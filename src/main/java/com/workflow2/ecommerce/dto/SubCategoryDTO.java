package com.workflow2.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
/**
 * @author mayur_jadhav
 * @version v0.0.1
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubCategoryDTO {
    private String subCategoryName;
    private byte[] subCategoryImage;
    private UUID categoryId;
}
