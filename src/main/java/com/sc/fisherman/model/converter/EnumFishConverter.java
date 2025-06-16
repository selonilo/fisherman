package com.sc.fisherman.model.converter;

import com.sc.fisherman.model.enums.EnumFishType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EnumFishConverter implements AttributeConverter<EnumFishType, Long> {

    @Override
    public Long convertToDatabaseColumn(EnumFishType postType) {
        if (postType == null) {
            return null;
        }
        return postType.getValue();
    }

    @Override
    public EnumFishType convertToEntityAttribute(Long value) {
        if (value == null) {
            return null;
        }
        return EnumFishType.fromValue(value);
    }
}
