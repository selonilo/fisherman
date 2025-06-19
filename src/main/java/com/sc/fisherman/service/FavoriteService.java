package com.sc.fisherman.service;

import com.sc.fisherman.model.dto.favorite.FavoriteModel;

public interface FavoriteService {
    Boolean favorite(FavoriteModel favoriteModel);
    Boolean unFavorite(FavoriteModel favoriteModel);
}
