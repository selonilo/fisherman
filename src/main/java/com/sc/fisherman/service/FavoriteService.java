package com.sc.fisherman.service;

import com.sc.fisherman.model.dto.favorite.FavoriteModel;
import com.sc.fisherman.model.dto.sales.SalesModel;
import com.sc.fisherman.model.enums.EnumContentType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FavoriteService {
    Boolean favorite(FavoriteModel favoriteModel);
    Boolean unFavorite(FavoriteModel favoriteModel);
}
