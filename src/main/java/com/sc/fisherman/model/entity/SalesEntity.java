package com.sc.fisherman.model.entity;

import com.sc.fisherman.model.entity.base.BaseEntity;
import com.sc.fisherman.model.enums.EnumSalesType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SALES_ENTITY")
public class SalesEntity extends BaseEntity {
    @NotBlank
    @Column(name = "TITLE", length = 100)
    private String title;

    @NotBlank
    @Column(name = "DESCRIPTION", columnDefinition = "TEXT")
    private String description;

    @NotNull
    @Column(name = "PRICE")
    private BigDecimal price;

    @NotNull
    @Column(name = "SALES_TYPE")
    private EnumSalesType salesType;

    @NotNull
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "imageUrlList")
    private List<String> imageUrlList;
}
