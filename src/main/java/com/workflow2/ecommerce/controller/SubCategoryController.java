package com.workflow2.ecommerce.controller;

import com.workflow2.ecommerce.dto.SubCategoryDTO;
import com.workflow2.ecommerce.entity.SubCategory;
import com.workflow2.ecommerce.services.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
/**
 * @author mayur_jadhav
 * @version v0.0.1
 */
@RequestMapping("/api")
@RestController
public class SubCategoryController {
    @Autowired
    SubCategoryService subCategoryService;
    @PostMapping("/subCategory/{categoryId}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<String> saveSubCategory(@RequestPart("subCategory")SubCategoryDTO subCategory,
                                                  @RequestPart("image")MultipartFile file,@PathVariable UUID categoryId){
        try{

           ResponseEntity<String> result = subCategoryService.saveSubCatergory(subCategory,file,categoryId);

           return result;

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @GetMapping("subCategory/{categoryId}")
    public ResponseEntity<List<SubCategory>> getAllSubCategoryByCategory(@PathVariable UUID categoryId) throws Exception {
        return ResponseEntity.ok().body(subCategoryService.getAllSubCategoryByCategory(categoryId));
    }



}
