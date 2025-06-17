package com.sc.fisherman.service;

import com.sc.fisherman.model.dto.location.LocationModel;

import java.util.List;

public interface LocationService {
    LocationModel save(LocationModel model);
    LocationModel update(LocationModel model);
    void delete(Long id);
    LocationModel getById(Long id);
    List<LocationModel> getList();
    List<LocationModel> getList(Long userId);
    Boolean approveLocation(Long locationId, Long userId);
    Boolean unApproveLocation(Long locationId, Long userId);
}
