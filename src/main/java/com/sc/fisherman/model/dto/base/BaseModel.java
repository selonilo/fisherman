package com.sc.fisherman.model.dto.base;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BaseModel {
    private Long id;
    private LocalDate createdDate;
    private LocalDate updatedDate;
    private String createdBy;
    private String updatedBy;
}
