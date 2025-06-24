package com.sc.fisherman.service;

import com.sc.fisherman.model.dto.follow.FollowModel;

public interface FollowService {
    Boolean follow(FollowModel model);
    Boolean unFollow(FollowModel model);
}
