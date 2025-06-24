package com.sc.fisherman.service;

import com.sc.fisherman.model.dto.follow.FollowModel;
import com.sc.fisherman.model.mapper.FollowMapper;
import com.sc.fisherman.repository.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    private FollowRepository repository;

    public Boolean follow(FollowModel model) {
        repository.saveAndFlush(FollowMapper.mapTo(model));
        return true;
    }

    public Boolean unFollow(FollowModel model) {
        var optFavorite = repository.findByContentTypeAndUserIdAndContentId(model.getContentType(), model.getUserId(), model.getContentId());
        optFavorite.ifPresent(favorite -> repository.delete(favorite));
        return false;
    }
}
