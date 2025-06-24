package com.sc.fisherman.service;

import com.sc.fisherman.model.dto.community.CommunityModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CommunityService {
    CommunityModel save(CommunityModel model);
    CommunityModel update(CommunityModel model);
    void delete(Long id);
    CommunityModel getById(Long id);
    List<CommunityModel> getList(Long userId);
    List<CommunityModel> getListByFollowed(Long userId);
    Boolean followCommunity(Long communityId, Long userId);
    Boolean unFollowCommunity(Long communityId, Long userId);
    String uploadImage(Long communityId, MultipartFile file);
    void deleteImage(Long postId);
}
