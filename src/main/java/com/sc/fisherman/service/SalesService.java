package com.sc.fisherman.service;

import com.sc.fisherman.model.dto.sales.SalesModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SalesService {
    SalesModel save(SalesModel model);
    SalesModel update(SalesModel model);
    void delete(Long id);
    SalesModel getById(Long id);
    List<SalesModel> getList();
    void uploadImage(Long salesId, List<MultipartFile> fileList);
    void deleteImage(Long postId);
}
