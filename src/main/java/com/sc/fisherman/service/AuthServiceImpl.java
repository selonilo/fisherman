package com.sc.fisherman.service;

import com.sc.fisherman.configuration.jwt.JwtService;
import com.sc.fisherman.exception.AlreadyExistException;
import com.sc.fisherman.exception.AnErrorOccurredException;
import com.sc.fisherman.exception.MailOrPasswordIncorrectException;
import com.sc.fisherman.exception.NotFoundException;
import com.sc.fisherman.model.data.PostHardyConstant;
import com.sc.fisherman.model.dto.NotificationModel;
import com.sc.fisherman.model.dto.ResponseMessageModel;
import com.sc.fisherman.model.dto.user.LoginModel;
import com.sc.fisherman.model.dto.user.PasswordRefreshModel;
import com.sc.fisherman.model.dto.user.TokenModel;
import com.sc.fisherman.model.dto.user.UserModel;
import com.sc.fisherman.model.entity.FollowEntity;
import com.sc.fisherman.model.entity.PostEntity;
import com.sc.fisherman.model.entity.UserEntity;
import com.sc.fisherman.model.mapper.UserMapper;
import com.sc.fisherman.repository.*;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

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

    public UserModel getById(Long id) {
        var optUser = userRepository.findById(id);
        if (optUser.isPresent()) {
            var userModel = UserMapper.mapTo(optUser.get());
            userModel.setFollowerCount(followRepository.countByFollowerUserId(id));
            userModel.setFollowCount(followRepository.countByFollowUserId(id));
            userModel.setPostCount(postRepository.countByUserId(id));
            userModel.setCommentCount(commentRepository.countByUserId(id));
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
        return tokenModel;
    }

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
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId.toString()));
        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadPath, fileName);

            Files.createDirectories(filePath.getParent());

            Files.write(filePath, file.getBytes());

            String imageUrl = "/post/uploads/" + fileName;
            user.setImageUrl(imageUrl);
            userRepository.save(user);

            return imageUrl;
        } catch (IOException e) {
            throw new AnErrorOccurredException(userId.toString());
        }
    }

    public void deleteUserImage(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(userId.toString()));

        if (user.getImageUrl() != null && !user.getImageUrl().isEmpty()) {
            try {
                Path filePath = Paths.get(uploadPath, user.getImageUrl().replace("/post/uploads/", ""));

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

    public void followUser(Long followUserId, Long followerUserId) {
        var optFollowUser = userRepository.findById(followUserId);
        var optFollowerUser = userRepository.findById(followerUserId);
        if (optFollowUser.isPresent() && optFollowerUser.isPresent()) {
            FollowEntity followEntity = new FollowEntity();
            followEntity.setFollowUserId(followUserId);
            followEntity.setFollowerUserId(followerUserId);
            followRepository.saveAndFlush(followEntity);
        } else {
            throw new NotFoundException(followUserId.toString().concat(followerUserId.toString()));
        }
    }

    public void unFollowUser(Long followUserId, Long followerUserId) {
        var optFollowUser = userRepository.findById(followUserId);
        var optFollowerUser = userRepository.findById(followerUserId);
        if (optFollowUser.isPresent() && optFollowerUser.isPresent()) {
            var optFollow = followRepository.findByFollowUserIdAndFollowerUserId(followUserId, followerUserId);
            optFollow.ifPresent(followEntity -> followRepository.delete(followEntity));
        } else {
            throw new NotFoundException(followUserId.toString().concat(followerUserId.toString()));
        }
    }

    public List<UserModel> getFollowListByUserId(Long userId) {
        var followList = followRepository.findByFollowUserId(userId);
        var userIdList = followList.stream().map(FollowEntity::getFollowerUserId).toList();
        return UserMapper.mapToList(userRepository.findByIdIn(userIdList));
    }

    public List<UserModel> getFollowerListByUserId(Long userId) {
        var followList = followRepository.findByFollowerUserId(userId);
        var userIdList = followList.stream().map(FollowEntity::getFollowUserId).toList();
        return UserMapper.mapToList(userRepository.findByIdIn(userIdList));
    }

    public List<NotificationModel> getNotification(Long userId) {
        List<NotificationModel> notificationModelList = new ArrayList<>();
        List<PostEntity> postList = postRepository.findAllByUserId(userId);
        var postIdList = postList.stream().map(PostEntity::getId).toList();
        var likeList = likeRepository.findAllByPostIdIn(postIdList);
        for (var like : likeList.stream().filter(x -> !x.getUserId().equals(userId)).toList()) {
            NotificationModel notificationModel = new NotificationModel();
            notificationModel.setIcon("pi pi-heart-fill");
            var optUser = userRepository.findById(like.getUserId());
            optUser.ifPresent(userEntity -> notificationModel.setUserModel(UserMapper.mapTo(userEntity)));
            var optPost = postRepository.findById(like.getPostId());
            optPost.ifPresent(postEntity -> notificationModel.setNotificationMessage(postEntity.getTitle().concat(" Başlıklı gönderini beğendi")));
            notificationModel.setNotificationDate(like.getCreatedDate());
            notificationModelList.add(notificationModel);
        }
        var commentList = commentRepository.findAllByPostIdIn(postIdList);
        for (var comment : commentList.stream().filter(x -> !x.getUserId().equals(userId)).toList()) {
            NotificationModel notificationModel = new NotificationModel();
            notificationModel.setIcon("pi pi-comment");
            var optUser = userRepository.findById(comment.getUserId());
            optUser.ifPresent(userEntity -> notificationModel.setUserModel(UserMapper.mapTo(userEntity)));
            var optPost = postRepository.findById(comment.getPostId());
            optPost.ifPresent(postEntity -> notificationModel.setNotificationMessage(postEntity.getTitle().concat(" Başlıklı gönderine yorum yaptı:".concat("\n").concat(comment.getComment()))));
            notificationModel.setNotificationDate(comment.getCreatedDate());
            notificationModelList.add(notificationModel);
        }
        return notificationModelList;
    }

}
