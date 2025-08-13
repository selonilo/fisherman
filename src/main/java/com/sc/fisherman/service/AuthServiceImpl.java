package com.sc.fisherman.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sc.fisherman.configuration.jwt.JwtService;
import com.sc.fisherman.exception.AlreadyExistException;
import com.sc.fisherman.exception.AnErrorOccurredException;
import com.sc.fisherman.exception.MailOrPasswordIncorrectException;
import com.sc.fisherman.exception.NotFoundException;
import com.sc.fisherman.model.data.PostHardyConstant;
import com.sc.fisherman.model.dto.NotificationCountModel;
import com.sc.fisherman.model.dto.NotificationModel;
import com.sc.fisherman.model.dto.ResponseMessageModel;
import com.sc.fisherman.model.dto.user.*;
import com.sc.fisherman.model.entity.*;
import com.sc.fisherman.model.enums.EnumContentType;
import com.sc.fisherman.model.mapper.NotificationMapper;
import com.sc.fisherman.model.mapper.UserMapper;
import com.sc.fisherman.repository.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AuthServiceImpl implements AuthService {
    @Value("${upload.path}")
    private String uploadPath;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789.";
    private static final SecureRandom RANDOM = new SecureRandom();
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    SpringTemplateEngine templateEngine;
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private PostService postService;

    private final JPAQueryFactory queryFactory;

    private final QUserEntity query = QUserEntity.userEntity;

    public AuthServiceImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public UserModel getById(Long id) {
        var optUser = userRepository.findById(id);
        if (optUser.isPresent()) {
            var userModel = UserMapper.mapTo(optUser.get());
            userModel.setFollowerCount(followRepository.countByContentTypeAndContentId(EnumContentType.USER, id));
            userModel.setFollowCount(followRepository.countByContentTypeAndUserId(EnumContentType.USER, id));
            userModel.setPostCount(postRepository.countByUserId(id));
            userModel.setCommentCount(commentRepository.countByUserId(id));
            return userModel;
        } else {
            throw new NotFoundException(id.toString());
        }
    }

    public UserModel getByIdAndLoginUserId(Long id, Long loginUserId) {
        var optUser = userRepository.findById(id);
        if (optUser.isPresent()) {
            var userModel = UserMapper.mapTo(optUser.get());
            userModel.setFollowerCount(followRepository.countByContentTypeAndContentId(EnumContentType.USER, id));
            userModel.setFollowCount(followRepository.countByContentTypeAndUserId(EnumContentType.USER, id));
            userModel.setPostCount(postRepository.countByUserId(id));
            userModel.setCommentCount(commentRepository.countByUserId(id));
            userModel.setIsFollowed(followRepository.findByContentTypeAndUserIdAndContentId(EnumContentType.USER, loginUserId, id).isPresent());
            return userModel;
        } else {
            throw new NotFoundException(id.toString());
        }
    }

    public UserModel register(UserModel userModel) {
        var optUser = userRepository.findByMail(userModel.getMail());
        if (optUser.isPresent()) {
            throw new AlreadyExistException(userModel.getMail());
        }
        UserEntity entity = UserMapper.mapTo(userModel);
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return UserMapper.mapTo(userRepository.save(entity));
    }

    public UserModel updateUser(UserModel userModel) {
        var optUser = userRepository.findById(userModel.getId());
        if (optUser.isPresent()) {
            var user = UserMapper.mapTo(userModel);
            user.setPassword(passwordEncoder.encode(userModel.getPassword()));
            return UserMapper.mapTo(userRepository.saveAndFlush(user));
        } else {
            throw new NotFoundException(userModel.getMail());
        }
    }

    public TokenModel login(LoginModel loginModel) {
        UserEntity user = userRepository.findByMail(loginModel.getMail())
                .orElseThrow(() -> new NotFoundException(loginModel.getMail()));

        if (!passwordEncoder.matches(loginModel.getPassword(), user.getPassword())) {
            throw new MailOrPasswordIncorrectException();
        }
        TokenModel tokenModel = new TokenModel();
        tokenModel.setToken(jwtService.generateToken(user));
        tokenModel.setUserId(user.getId());
        tokenModel.setName(user.getName());
        tokenModel.setImageUrl(user.getImageUrl());
        return tokenModel;
    }

    @Transactional
    public ResponseMessageModel refreshPassword(PasswordRefreshModel passwordRefreshModel) {
        var optUserEntity = userRepository.findByMail(passwordRefreshModel.getMail());
        if (optUserEntity.isPresent()) {
            var userEntity = optUserEntity.get();
            var password = generateRandomPassword(8);
            userEntity.setPassword(passwordEncoder.encode(password));
            userRepository.saveAndFlush(userEntity);
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message);
                Context context = new Context();
                context.setVariable(PostHardyConstant.PASSWORD, password);
                context.setVariable(PostHardyConstant.USERNAME, userEntity.getName().concat(" ").concat(userEntity.getSurname()));
                String text = templateEngine.process(PostHardyConstant.MAIL_TEMPLATE, context);
                helper.setFrom(from);
                helper.setTo(userEntity.getMail());
                helper.setSubject(PostHardyConstant.MAIL_SUBJECT);
                helper.setText(text, true);
                mailSender.send(message);
            } catch (MessagingException e) {
                throw new AnErrorOccurredException(e.getMessage());
            }
            ResponseMessageModel responseMessageModel = new ResponseMessageModel();
            responseMessageModel.setMessage(passwordRefreshModel.getMail() + PostHardyConstant.MAIL_SUCCESS);
            return responseMessageModel;
        } else {
            throw new NotFoundException(passwordRefreshModel.getMail());
        }
    }

    public static String generateRandomPassword(int length) {
        if (length < 1) {
            throw new IllegalArgumentException("Password length must be at least 1");
        }

        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(randomIndex));
        }

        return password.toString();
    }

    public String uploadUserImage(Long userId, MultipartFile file) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(userId.toString()));

        try {
            // 📌 Tüm dosyaları JPG olarak kaydediyoruz
            String fileName = System.currentTimeMillis() + ".jpg";
            Path filePath = Paths.get(uploadPath, fileName);
            Files.createDirectories(filePath.getParent());

            // 1️⃣ MultipartFile'i BufferedImage olarak oku
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image == null) {
                throw new RuntimeException("Görsel okunamadı.");
            }

            // 2️⃣ PNG ise arka planı beyaza çevir (JPG transparan desteklemez)
            if ("png".equalsIgnoreCase(getFileExtension(file.getOriginalFilename()))) {
                BufferedImage newImage = new BufferedImage(
                        image.getWidth(), image.getHeight(),
                        BufferedImage.TYPE_INT_RGB
                );
                Graphics2D g2d = newImage.createGraphics();
                g2d.setColor(Color.WHITE); // Beyaz arka plan
                g2d.fillRect(0, 0, newImage.getWidth(), newImage.getHeight());
                g2d.drawImage(image, 0, 0, null);
                g2d.dispose();
                image = newImage;
            }

            int targetWidth = 1080;
            if (image.getWidth() > targetWidth) {
                int targetHeight = (int) (((double) targetWidth / image.getWidth()) * image.getHeight());
                BufferedImage resized = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2dResize = resized.createGraphics();
                g2dResize.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2dResize.drawImage(image, 0, 0, targetWidth, targetHeight, null);
                g2dResize.dispose();
                image = resized;
            }

            // 4️⃣ JPG olarak daha düşük kaliteyle kaydet (%30 kalite)
            try (OutputStream os = Files.newOutputStream(filePath)) {
                ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
                ImageOutputStream ios = ImageIO.createImageOutputStream(os);
                writer.setOutput(ios);

                ImageWriteParam param = writer.getDefaultWriteParam();
                if (param.canWriteCompressed()) {
                    param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                    param.setCompressionQuality(0.3f); // %30 kalite
                }

                writer.write(null, new IIOImage(image, null, null), param);
                ios.close();
                writer.dispose();
            }

            // 4️⃣ Veritabanına URL kaydet
            String imageUrl = "/fisherman/uploads/" + fileName;
            user.setImageUrl(imageUrl);
            userRepository.save(user);

            return imageUrl;

        } catch (IOException e) {
            throw new AnErrorOccurredException(userId.toString());
        }
    }

    // 📌 Yardımcı metod: uzantıyı alır
    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) return "";
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }


    public void deleteUserImage(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(userId.toString()));

        if (user.getImageUrl() != null && !user.getImageUrl().isEmpty()) {
            try {
                Path filePath = Paths.get(uploadPath, user.getImageUrl().replace("/fisherman/uploads/", ""));

                Files.deleteIfExists(filePath);

                user.setImageUrl(null);
                userRepository.save(user);
            } catch (IOException e) {
                throw new AnErrorOccurredException(userId.toString());
            }
        } else {
            throw new NotFoundException("Silinecek resim".concat(userId.toString()));
        }
    }

    public String getImage(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId.toString()));

        if (user.getImageUrl() == null) {
            throw new NotFoundException(userId.toString().concat("Resim"));
        }
        return user.getImageUrl();
    }

    public List<UserModel> getFollowListByUserId(Long userId) {
        var followList = followRepository.findAllByContentTypeAndUserId(EnumContentType.USER, userId);
        var userIdList = followList.stream().map(FollowEntity::getContentId).toList();
        return UserMapper.mapToList(userRepository.findByIdIn(userIdList));
    }

    public List<UserModel> getFollowerListByUserId(Long userId) {
        var followList = followRepository.findAllByContentTypeAndContentId(EnumContentType.USER, userId);
        var userIdList = followList.stream().map(FollowEntity::getUserId).toList();
        return UserMapper.mapToList(userRepository.findByIdIn(userIdList));
    }


    public List<NotificationModel> getNotification(Long userId) {
        var notificationList = notificationRepository.findAllByReceiverUserIdAndIsRead(userId, false);
        var userIdList = notificationList.stream().map(NotificationEntity::getSenderUserId).distinct().toList();
        List<UserEntity> userList = new ArrayList<>();
        if (!userIdList.isEmpty()) {
            userList = userRepository.findByIdIn(userIdList);
        }
        var notificationModelList = NotificationMapper.mapToList(notificationList);
        for (var notif : notificationList) {
            notif.setRead(true);
            notificationRepository.save(notif);
        }
        for (NotificationModel notificationModel : notificationModelList) {
            notificationModel.setUserImageUrl(Objects.requireNonNull(userList.stream().filter(user ->
                    user.getId().equals(notificationModel.getSenderUserId())).findFirst().orElse(null)).getImageUrl());
        }
        return notificationModelList;
    }

    public NotificationCountModel getNotificationCount(Long userId) {
        var notificationList = notificationRepository.countByReceiverUserIdAndIsRead(userId, false);
        NotificationCountModel notificationCountModel = new NotificationCountModel();
        notificationCountModel.setNotificationCount(notificationList);
        return notificationCountModel;
    }

    public List<UserModel> findWithName(UserQueryModel queryModel) {
        var jpaQuery = queryFactory.selectFrom(query);
        if (queryModel.getName() != null) {
            jpaQuery.where(query.name.toLowerCase().contains(queryModel.getName().toLowerCase()));
        }
        var entityList = jpaQuery.fetch();
        return UserMapper.mapToList(entityList);
    }

}
