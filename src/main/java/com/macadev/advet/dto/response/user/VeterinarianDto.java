package com.macadev.advet.dto.response.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class VeterinarianDto extends UserDto {
    private String specialization; // vet-specific field
}
