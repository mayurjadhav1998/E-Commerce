package com.workflow2.ecommerce.services;

import com.workflow2.ecommerce.dto.SubCategoryDTO;
import com.workflow2.ecommerce.entity.SubCategory;
import com.workflow2.ecommerce.repository.CategoryDao;
import com.workflow2.ecommerce.repository.SubCategoryDao;
import com.workflow2.ecommerce.util.ImageUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
/**
 * @author mayur_jadhav
 * @version v0.0.1
 */
@Service
public class SubCategoryService {
    @Autowired
    CategoryDao categoryDao;
    @Autowired
    SubCategoryDao subCategoryDao;
    public ResponseEntity<String> saveSubCatergory(SubCategoryDTO subCategoryDTO, MultipartFile file, UUID categoryId) throws Exception {
        SubCategory subCategory = new SubCategory();
        subCategory.setCategoryId(categoryId);
        subCategory.setSubCategoryImage(ImageUtility.compressImage(file.getBytes()));
        subCategory.setSubCategoryName(subCategoryDTO.getSubCategoryName());
        subCategory.setCategoryName(categoryDao.findById(categoryId).get().getCategoryName());
        subCategoryDao.save(subCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body("Sub Category Created");
    }

    public List<SubCategory> getAllSubCategoryByCategory(UUID categoryId) throws Exception {
        List<SubCategory> subCategory = subCategoryDao.findByCategoryId(categoryId);
        for(SubCategory sub : subCategory){
            sub.setSubCategoryImage(ImageUtility.decompressImage(sub.getSubCategoryImage()));
        }

        return subCategory;
    }
}
