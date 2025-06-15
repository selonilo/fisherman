package com.sc.fisherman.model.converter;

import com.sc.fisherman.model.enums.EnumPostType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EnumPostConverter implements AttributeConverter<EnumPostType, Long> {

    @Override
    public Long convertToDatabaseColumn(EnumPostType postType) {
        if (postType == null) {
            return null;
        }
        return postType.getValue();
    }

    @Override
    public EnumPostType convertToEntityAttribute(Long value) {
        if (value == null) {
            return null;
        }
        return EnumPostType.fromValue(value);
    }
}
