package com.sc.fisherman.model.dto.sales;

import com.sc.fisherman.model.dto.base.BaseModel;
import com.sc.fisherman.model.dto.user.UserModel;
import com.sc.fisherman.model.enums.EnumSalesType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesModel extends BaseModel {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotNull
    private BigDecimal price;
    @NotNull
    private EnumSalesType salesType;
    @NotNull
    private Long userId;
    private List<String> imageUrlList;
    private List<MultipartFile> fileList;
    private Long favoriteCount;
    private UserModel userModel;
}
