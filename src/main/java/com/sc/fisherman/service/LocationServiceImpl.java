package com.sc.fisherman.service;

import com.sc.fisherman.exception.NotFoundException;
import com.sc.fisherman.model.dto.location.LocationModel;
import com.sc.fisherman.model.entity.*;
import com.sc.fisherman.model.mapper.LocationMapper;
import com.sc.fisherman.model.mapper.UserMapper;
import com.sc.fisherman.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {
    @Autowired
    private LocationRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApproveRepository approveRepository;

    public LocationModel save(LocationModel model) {
        var savedModel = LocationMapper.mapTo(repository.saveAndFlush(LocationMapper.mapTo(model)));
        var optUser = userRepository.findById(savedModel.getUserId());
        optUser.ifPresent(x -> savedModel.setUserModel(UserMapper.mapTo(x)));
        return savedModel;
    }

    public LocationModel update(LocationModel model) {
        var optEntity = repository.findById(model.getId());
        if (optEntity.isPresent()) {
            var entity = LocationMapper.mapTo(model);
            return LocationMapper.mapTo(repository.saveAndFlush(entity));
        } else {
            throw new NotFoundException(model.getCoordinate());
        }
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public LocationModel getById(Long id) {
        var optEntity = repository.findById(id);
        if (optEntity.isPresent()) {
            var entity = optEntity.get();
            var model = LocationMapper.mapTo(entity);
            var optUser = userRepository.findById(entity.getUserId());
            optUser.ifPresent(x -> model.setUserModel(UserMapper.mapTo(x)));
            return model;
        } else {
            throw new NotFoundException(id.toString());
        }
    }

    public List<LocationModel> getList() {
        List<LocationEntity> entityList = repository.findAll();
        var modelList = LocationMapper.mapToList(entityList);
        for (var model : modelList) {
            var optUser = userRepository.findById(model.getUserId());
            optUser.ifPresent(x -> model.setUserModel(UserMapper.mapTo(x)));
            model.setApproveCount(approveRepository.countByLocationId(model.getId()));
        }
        return modelList;
    }

    public List<LocationModel> getList(Long userId) {
        List<LocationEntity> entityList = repository.findAll();
        var modelList = LocationMapper.mapToList(entityList);
        for (var model : modelList) {
            var optUser = userRepository.findById(model.getUserId());
            optUser.ifPresent(x -> model.setUserModel(UserMapper.mapTo(x)));
            model.setApproveCount(approveRepository.countByLocationId(model.getId()));
            model.setIsApproved(approveRepository.findByLocationIdAndUserId(model.getId(), userId).isPresent());
        }
        return modelList;
    }

    public Boolean approveLocation(Long locationId, Long userId) {
        var optEntity = repository.findById(locationId);
        var optUser = userRepository.findById(userId);
        if (optEntity.isPresent() && optUser.isPresent()) {
            ApproveEntity approveEntity = new ApproveEntity();
            approveEntity.setLocationId(locationId);
            approveEntity.setUserId(userId);
            approveRepository.saveAndFlush(approveEntity);
            return true;
        } else {
            throw new NotFoundException(locationId.toString().concat(userId.toString()));
        }
    }

    public Boolean unApproveLocation(Long locationId, Long userId) {
        var optEntity = repository.findById(locationId);
        var optUser = userRepository.findById(userId);
        if (optEntity.isPresent() && optUser.isPresent()) {
            var optApprove = approveRepository.findByLocationIdAndUserId(locationId, userId);
            optApprove.ifPresent(approve -> approveRepository.delete(approve));
            return false;
        } else {
            throw new NotFoundException(locationId.toString().concat(userId.toString()));
        }
    }
}
