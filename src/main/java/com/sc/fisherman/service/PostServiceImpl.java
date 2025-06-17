package com.sc.fisherman.service;

import com.sc.fisherman.exception.AnErrorOccurredException;
import com.sc.fisherman.exception.NotFoundException;
import com.sc.fisherman.model.dto.TotalStatsModel;
import com.sc.fisherman.model.dto.post.CommentModel;
import com.sc.fisherman.model.dto.post.PostModel;
import com.sc.fisherman.model.dto.post.PostQueryModel;
import com.sc.fisherman.model.entity.CommentEntity;
import com.sc.fisherman.model.entity.LikeEntity;
import com.sc.fisherman.model.entity.PostEntity;
import com.sc.fisherman.model.entity.ViewEntity;
import com.sc.fisherman.model.mapper.PostMapper;
import com.sc.fisherman.model.mapper.UserMapper;
import com.sc.fisherman.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Value("${upload.path}")
    private String uploadPath;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ViewRepository viewRepository;
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private CommentRepository commentRepository;

    public PostModel save(PostModel postModel) {
        var savedModel = PostMapper.mapTo(postRepository.saveAndFlush(PostMapper.mapTo(postModel)));
        if (postModel.getFile() != null) {
            uploadImage(savedModel.getId(), postModel.getFile());
        }
        return savedModel;
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    public PostModel getById(Long id) {
        var optPost = postRepository.findById(id);
        if (optPost.isPresent()) {
            var post = optPost.get();
            return PostMapper.mapTo(post);
        } else {
            throw new NotFoundException(id.toString());
        }
    }

    public void likePost(Long postId, Long userId) {
        var optPost = postRepository.findById(postId);
        var optUser = userRepository.findById(userId);
        if (optPost.isPresent() && optUser.isPresent()) {
            LikeEntity likeEntity = new LikeEntity();
            likeEntity.setPostId(postId);
            likeEntity.setUserId(userId);
            likeRepository.saveAndFlush(likeEntity);
        } else {
            throw new NotFoundException(postId.toString().concat(userId.toString()));
        }
    }

    public void unLikePost(Long postId, Long userId) {
        var optPost = postRepository.findById(postId);
        var optUser = userRepository.findById(userId);
        if (optPost.isPresent() && optUser.isPresent()) {
            var optLike = likeRepository.findByPostIdAndUserId(postId, userId);
            optLike.ifPresent(likeEntity -> likeRepository.delete(likeEntity));
        } else {
            throw new NotFoundException(postId.toString().concat(userId.toString()));
        }
    }

    public List<PostModel> getList(Long userId) {
        List<PostEntity> postList = postRepository.findAll();
        List<PostModel> postModelList = PostMapper.mapToList(postList);
        for (var post : postModelList) {
            var optView = viewRepository.findByPostIdAndUserId(post.getId(), userId);
            if (optView.isEmpty()) {
                ViewEntity viewEntity = new ViewEntity();
                viewEntity.setPostId(post.getId());
                viewEntity.setUserId(userId);
                viewRepository.saveAndFlush(viewEntity);
            }
            post.setIsLiked(likeRepository.findByPostIdAndUserId(post.getId(), userId).isPresent());
            post.setIsFollowed(followRepository.findByFollowUserIdAndFollowerUserId(post.getUserId(), userId).isPresent());
            post.setLikeNumber(likeRepository.countByPostId(post.getId()));
            post.setViewNumber(viewRepository.countByPostId(post.getId()));
            var optUser = userRepository.findById(post.getUserId());
            optUser.ifPresent(user -> {
                post.setUserImageUrl(user.getImageUrl());
                post.setName(user.getName());
            });
            var commentList = commentRepository.findAllByPostId(post.getId());
            List<CommentModel> commentModelList = new ArrayList<>();
            for (var comment : commentList) {
                CommentModel commentModel = new CommentModel();
                commentModel.setId(comment.getId());
                commentModel.setPostId(comment.getPostId());
                commentModel.setUserId(comment.getUserId());
                commentModel.setComment(comment.getComment());
                commentModel.setCreatedDate(comment.getCreatedDate());
                var optCommentUser = userRepository.findById(comment.getUserId());
                optCommentUser.ifPresent(userEntity -> commentModel.setUserModel(UserMapper.mapTo(userEntity)));
                commentModelList.add(commentModel);
            }
            post.setCommentModelList(commentModelList);
        }
        postModelList = new ArrayList<>(postModelList);
        postModelList.sort(Comparator.comparing(PostModel::getUserId));
        return postModelList;
    }

    public List<PostModel> getListByUserId(Long userId) {
        List<PostEntity> postList = postRepository.findAllByUserId(userId);
        List<PostModel> postModelList = PostMapper.mapToList(postList);
        for (var post : postModelList) {
            var optView = viewRepository.findByPostIdAndUserId(post.getId(), userId);
            if (optView.isEmpty()) {
                ViewEntity viewEntity = new ViewEntity();
                viewEntity.setPostId(post.getId());
                viewEntity.setUserId(userId);
                viewRepository.saveAndFlush(viewEntity);
            }
            post.setIsLiked(likeRepository.findByPostIdAndUserId(post.getId(), userId).isPresent());
            post.setLikeNumber(likeRepository.countByPostId(post.getId()));
            post.setViewNumber(viewRepository.countByPostId(post.getId()));
            var optUser = userRepository.findById(post.getUserId());
            optUser.ifPresent(user -> {
                post.setUserImageUrl(user.getImageUrl());
                post.setName(user.getName());
            });
            var commentList = commentRepository.findAllByPostId(post.getId());
            List<CommentModel> commentModelList = new ArrayList<>();
            for (var comment : commentList) {
                CommentModel commentModel = new CommentModel();
                commentModel.setId(comment.getId());
                commentModel.setPostId(comment.getPostId());
                commentModel.setUserId(comment.getUserId());
                commentModel.setComment(comment.getComment());
                commentModel.setCreatedDate(comment.getCreatedDate());
                var optCommentUser = userRepository.findById(comment.getUserId());
                optCommentUser.ifPresent(userEntity -> commentModel.setUserModel(UserMapper.mapTo(userEntity)));
                commentModelList.add(commentModel);
            }
            post.setCommentModelList(commentModelList);
        }
        postModelList = new ArrayList<>(postModelList);
        postModelList.sort(Comparator.comparing(PostModel::getUserId));
        return postModelList;
    }

    public List<PostModel> getListByUserIdAndLoginUserId(Long userId, Long loginUserId) {
        List<PostEntity> postList = postRepository.findAllByUserId(userId);
        List<PostModel> postModelList = PostMapper.mapToList(postList);
        for (var post : postModelList) {
            var optView = viewRepository.findByPostIdAndUserId(post.getId(), loginUserId);
            if (optView.isEmpty()) {
                ViewEntity viewEntity = new ViewEntity();
                viewEntity.setPostId(post.getId());
                viewEntity.setUserId(loginUserId);
                viewRepository.saveAndFlush(viewEntity);
            }
            post.setIsLiked(likeRepository.findByPostIdAndUserId(post.getId(), loginUserId).isPresent());
            post.setLikeNumber(likeRepository.countByPostId(post.getId()));
            post.setViewNumber(viewRepository.countByPostId(post.getId()));
            var optUser = userRepository.findById(post.getUserId());
            optUser.ifPresent(user -> {
                post.setUserImageUrl(user.getImageUrl());
                post.setName(user.getName());
            });
            var commentList = commentRepository.findAllByPostId(post.getId());
            List<CommentModel> commentModelList = new ArrayList<>();
            for (var comment : commentList) {
                CommentModel commentModel = new CommentModel();
                commentModel.setId(comment.getId());
                commentModel.setPostId(comment.getPostId());
                commentModel.setUserId(comment.getUserId());
                commentModel.setComment(comment.getComment());
                commentModel.setCreatedDate(comment.getCreatedDate());
                var optCommentUser = userRepository.findById(comment.getUserId());
                optCommentUser.ifPresent(userEntity -> commentModel.setUserModel(UserMapper.mapTo(userEntity)));
                commentModelList.add(commentModel);
            }
            post.setCommentModelList(commentModelList);
        }
        postModelList = new ArrayList<>(postModelList);
        postModelList.sort(Comparator.comparing(PostModel::getUserId));
        return postModelList;
    }

    public Page<PostModel> findPostWithPagination(Pageable pageable, PostQueryModel queryModel) {
        Page<PostEntity> post = postRepository.findAllPost(queryModel.getTitle(), queryModel.getFishType(), pageable);
        return post.map(PostMapper::mapTo);
    }

    public Boolean isLiked(Long postId, Long userId) {
        return likeRepository.findByPostIdAndUserId(postId, userId).isPresent();
    }

    public String uploadImage(Long postId, MultipartFile file) {
        var post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException(postId.toString()));
        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadPath, fileName);

            Files.createDirectories(filePath.getParent());

            Files.write(filePath, file.getBytes());

            String imageUrl = "/fisherman/uploads/" + fileName;
            post.setImageUrl(imageUrl);
            postRepository.save(post);

            return imageUrl;
        } catch (IOException e) {
            throw new AnErrorOccurredException(postId.toString());
        }
    }

    public void deleteImage(Long postId) {
        var post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(postId.toString()));

        if (post.getImageUrl() != null && !post.getImageUrl().isEmpty()) {
            try {
                Path filePath = Paths.get(uploadPath, post.getImageUrl().replace("/fisherman/uploads/", ""));

                Files.deleteIfExists(filePath);

                post.setImageUrl(null);
                postRepository.save(post);
            } catch (IOException e) {
                throw new AnErrorOccurredException(postId.toString());
            }
        } else {
            throw new NotFoundException("Silinecek resim".concat(postId.toString()));
        }
    }

    public TotalStatsModel getTotalStats() {
        TotalStatsModel totalStatsModel = new TotalStatsModel();
        totalStatsModel.setTotalPostCount(postRepository.count());
        totalStatsModel.setTotalLikeCount(likeRepository.count());
        totalStatsModel.setTotalUserCount(userRepository.count());
        totalStatsModel.setTotalCommentCount(commentRepository.count());
        return totalStatsModel;
    }

    public void commentPost(CommentModel commentModel) {
        var optPost = postRepository.findById(commentModel.getPostId());
        var optUser = userRepository.findById(commentModel.getUserId());
        if (optPost.isPresent() && optUser.isPresent()) {
            CommentEntity commentEntity = new CommentEntity();
            commentEntity.setUserId(commentModel.getUserId());
            commentEntity.setPostId(commentModel.getPostId());
            commentEntity.setComment(commentModel.getComment());
            commentRepository.saveAndFlush(commentEntity);
        } else {
            throw new NotFoundException(commentModel.getPostId().toString().concat(commentModel.getUserId().toString()));
        }
    }

    public void deleteComment(Long commentId) {
        var optComment = commentRepository.findById(commentId);
        if (optComment.isPresent()) {
            optComment.ifPresent(commentEntity -> commentRepository.delete(commentEntity));
        } else {
            throw new NotFoundException(commentId.toString());
        }
    }

    public List<PostModel> getListByLocationId(Long locationId, Long userId) {
        List<PostEntity> postList = postRepository.findAllByLocationId(locationId);
        List<PostModel> postModelList = PostMapper.mapToList(postList);
        for (var post : postModelList) {
            var optView = viewRepository.findByPostIdAndUserId(post.getId(), userId);
            if (optView.isEmpty()) {
                ViewEntity viewEntity = new ViewEntity();
                viewEntity.setPostId(post.getId());
                viewEntity.setUserId(userId);
                viewRepository.saveAndFlush(viewEntity);
            }
            post.setIsLiked(likeRepository.findByPostIdAndUserId(post.getId(), userId).isPresent());
            post.setIsFollowed(followRepository.findByFollowUserIdAndFollowerUserId(post.getUserId(), userId).isPresent());
            post.setLikeNumber(likeRepository.countByPostId(post.getId()));
            post.setViewNumber(viewRepository.countByPostId(post.getId()));
            var optUser = userRepository.findById(post.getUserId());
            optUser.ifPresent(user -> {
                post.setUserImageUrl(user.getImageUrl());
                post.setName(user.getName());
            });
            var commentList = commentRepository.findAllByPostId(post.getId());
            List<CommentModel> commentModelList = new ArrayList<>();
            for (var comment : commentList) {
                CommentModel commentModel = new CommentModel();
                commentModel.setId(comment.getId());
                commentModel.setPostId(comment.getPostId());
                commentModel.setUserId(comment.getUserId());
                commentModel.setComment(comment.getComment());
                commentModel.setCreatedDate(comment.getCreatedDate());
                var optCommentUser = userRepository.findById(comment.getUserId());
                optCommentUser.ifPresent(userEntity -> commentModel.setUserModel(UserMapper.mapTo(userEntity)));
                commentModelList.add(commentModel);
            }
            post.setCommentModelList(commentModelList);
        }
        postModelList = new ArrayList<>(postModelList);
        postModelList.sort(Comparator.comparing(PostModel::getUserId));
        return postModelList;
    }
}
