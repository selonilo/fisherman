package com.sc.fisherman.service;

import com.sc.fisherman.exception.NotFoundException;
import com.sc.fisherman.model.dto.favorite.FavoriteModel;
import com.sc.fisherman.model.mapper.FavoriteMapper;
import com.sc.fisherman.repository.FavoriteRepository;
import com.sc.fisherman.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FavoriteServiceImpl implements FavoriteService {
    @Autowired
    private FavoriteRepository repository;
    @Autowired
    private UserRepository userRepository;

    public Boolean favorite(FavoriteModel favoriteModel) {
        var optUser = userRepository.findById(favoriteModel.getUserId());
        if (optUser.isPresent()) {
            repository.saveAndFlush(FavoriteMapper.mapTo(favoriteModel));
            return true;
        } else {
            throw new NotFoundException(favoriteModel.getUserId().toString());
        }
    }

    public Boolean unFavorite(FavoriteModel favoriteModel) {
        var optUser = userRepository.findById(favoriteModel.getUserId());
        if (optUser.isPresent()) {
            var optFavorite = repository.findByContentTypeAndUserIdAndContentId(favoriteModel.getContentType(), favoriteModel.getUserId(), favoriteModel.getContentId());
            optFavorite.ifPresent(favorite -> repository.delete(favorite));
            return false;
        } else {
            throw new NotFoundException(favoriteModel.getUserId().toString());
        }
    }
}
