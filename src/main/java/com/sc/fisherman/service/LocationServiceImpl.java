package com.sc.fisherman.service;

import com.sc.fisherman.exception.NotFoundException;
import com.sc.fisherman.model.dto.location.LocationModel;
import com.sc.fisherman.model.entity.*;
import com.sc.fisherman.model.mapper.LocationMapper;
import com.sc.fisherman.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {
    @Autowired
    private LocationRepository repository;

    public LocationModel save(LocationModel model) {
        return LocationMapper.mapTo(repository.saveAndFlush(LocationMapper.mapTo(model)));
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
            return LocationMapper.mapTo(entity);
        } else {
            throw new NotFoundException(id.toString());
        }
    }

    public List<LocationModel> getList() {
        List<LocationEntity> entityList = repository.findAll();
        return LocationMapper.mapToList(entityList);
    }
}
