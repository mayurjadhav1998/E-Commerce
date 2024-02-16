package com.workflow2.ecommerce.services;


import com.workflow2.ecommerce.entity.Category;
import com.workflow2.ecommerce.entity.Product;
import com.workflow2.ecommerce.dto.ProductDTO;

import com.workflow2.ecommerce.entity.SubCategory;
import com.workflow2.ecommerce.repository.CategoryDao;
import com.workflow2.ecommerce.repository.ProductDao;

import com.workflow2.ecommerce.repository.SubCategoryDao;
import com.workflow2.ecommerce.util.ImageUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * This class implements ProductService interface which have all the methods to perform operations on Product
 * @author krishna_rawat & nikhitha_sripada
 * @version v0.0.1
 */
@Service
public class ProductServiceImpl{

    @Autowired
    private ProductDao productDao;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private SubCategoryDao subCategoryDao;

    public Category getCategory(Product product){
        return categoryDao.findById(product.getCategoryId()).get();
    }

    public SubCategory getSubCategory(Product product){
        return subCategoryDao.findById(product.getSubCategoryId()).get();
    }

    /**
     * This method save's product to the database
     * @param product it takes Product object as parameter which have all the attribute related to product
     * @return it returns object of entity with ProductDTO inside body and respective response code
     */
    public ResponseEntity<ProductDTO> saveProduct(Product product) {
        try {
            System.out.println("In save try");
           product = productDao.save(product);

            if(product==null){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(ProductDTO.builder()
                    .productId(product.getId())
                    .productName(product.getName())
                    .brand(product.getBrand())
                    .categoryName(product.getCategoryName())
                    .subCategoryName(product.getSubCategoryName())
                    .categoryId(product.getCategoryId())
                    .subCategoryId(product.getSubCategoryId())
                    .color(product.getColor())
                    .size(product.getSize())
                    .price(product.getPrice())
                    .description(product.getDescription())
                    .totalStock(product.getTotalStock())
                    .image(ImageUtility.decompressImage(product.getImage()))
                    .ratings(product.getRatings())
                    .discountPercent(product.getDiscountPercent())
                    .discountedPrice(product.getDiscountedPrice())
                    .build());
        }catch (Exception e){
            System.out.println("In save catcg");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * This method fetch data from database and sent back to controller class
     * @param productId It takes productId as argument which is type of UUID
     * @return returns product whose id is give as parameter otherwise return not found status
     */
    public ResponseEntity<ProductDTO> getProduct(UUID productId) {
        try {
            final Optional<Product> prod = Optional.of(productDao.getReferenceById(productId));
            if (prod==null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.status(HttpStatus.OK).body(ProductDTO.builder()
                    .productId(prod.get().getId())
                    .productName(prod.get().getName())
                    .categoryName(prod.get().getCategoryName())
                    .subCategoryName(prod.get().getSubCategoryName())
                    .categoryId(prod.get().getCategoryId())
                    .subCategoryId(prod.get().getSubCategoryId())
                    .brand(prod.get().getBrand())
                    .image(ImageUtility.decompressImage(prod.get().getImage()))
                    .price(prod.get().getPrice())
                    .size(prod.get().getSize())
                    .color(prod.get().getColor())
                    .description(prod.get().getDescription())
                    .totalStock(prod.get().getTotalStock())
                    .ratings(prod.get().getRatings())
                    .discountedPrice(prod.get().getDiscountedPrice())
                    .discountPercent(prod.get().getDiscountPercent())
                    .build());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
    }

    /**
     * This method return all the product present in database
     * @return returns List of products inside the body of response entity class
     */
    public ResponseEntity<List<Product>> getAllProducts() {
        try {
            List<Product> prods = productDao.findAll();
            if (prods == null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
            List<Product> decompressProds = new ArrayList<>();
            for (Product p : prods) {
                Product prod = Product.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .categoryId(p.getCategoryId())
                        .subCategoryId(p.getSubCategoryId())
                        .categoryName(p.getCategoryName())
                        .subCategoryName(p.getSubCategoryName())
                        .brand(p.getBrand())
                        .image(ImageUtility.decompressImage(p.getImage()))
                        .size(p.getSize())
                        .color(p.getColor())
                        .price(p.getPrice())
                        .description(p.getDescription())
                        .totalStock(p.getTotalStock())
                        .ratings(p.getRatings())
                        .discountedPrice(p.getDiscountedPrice())
                        .discountPercent(p.getDiscountPercent())
                        .build();
                decompressProds.add(prod);
            }
            return ResponseEntity.status(HttpStatus.OK).body(decompressProds);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * This method update the product whose id is given in the parameter
     * @param product This contains updated product value
     * @param productId This is the Id of the product we are supposed to update
     * @return return updated product within body of response entity class
     */
    public ResponseEntity<ProductDTO> updateProduct(Product product, UUID productId) {
        try {
            final Optional<Product> prod = Optional.of(productDao.getReferenceById(productId));
            if (prod != null) {
                product.setId(prod.get().getId());
                product = productDao.save(product);
                product.setImage(ImageUtility.decompressImage(prod.get().getImage()));
                return ResponseEntity.status(HttpStatus.OK)
                        .body(ProductDTO.builder()
                        .productId(product.getId())
                        .productName(product.getName())
                        .brand(product.getBrand())
                        .categoryName(product.getCategoryName())
                                .subCategoryName(product.getSubCategoryName())
                                .categoryId(prod.get().getCategoryId())
                                .subCategoryId(prod.get().getSubCategoryId())
                        .color(product.getColor())
                        .size(product.getSize())
                        .price(product.getPrice())
                        .image(product.getImage())
                        .description(product.getDescription())
                        .totalStock(product.getTotalStock())
                        .ratings(product.getRatings())
                        .discountPercent(product.getDiscountPercent())
                        .discountedPrice(product.getDiscountedPrice())
                        .build());
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * This method has ability to delete a particular product from database whose Id is given inside the parameter
     * @param productId This is the productId of the product
     * @return it return String inside the body of response entity class with appropriate status code
     */
    public ResponseEntity deleteProduct(UUID productId) {
        try {
            productDao.deleteById(productId);
            return ResponseEntity.status(HttpStatus.OK).body("Deleted Successfully");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
        }
    }

    /**
     * This method can delete all the product from the database hence this operation is only allow for admin User
     * @return it return String inside the body of response entity class with appropriate status code
     */
    public ResponseEntity deleteAllProducts() {
        try {
            productDao.deleteAll();
            return ResponseEntity.status(HttpStatus.OK).body("All products Deleted Successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to delete there is some error");
        }
    }

    /**
     * This method has ability to find product based on given string value
     * @param searchText This is a String which contains information about product which need to be searched
     * @return It returns list of product which fully or partially matches with the given string
     */
    public ResponseEntity<List<ProductDTO>> getAllSearchedProduct(String searchText) {
        try {
            List<Product> prods = productDao.findBySearchText(searchText);
            if (prods == null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
            List<ProductDTO> decompressProds = new ArrayList<>();
            for (Product p : prods) {
                p.setImage(ImageUtility.decompressImage(p.getImage()));
                ProductDTO prod = ProductDTO.builder()
                        .productId(p.getId())
                        .productName(p.getName())
                        .categoryName(getCategory(p).getCategoryName())
                        .subCategoryName(getSubCategory(p).getSubCategoryName())
                        .brand(p.getBrand())
                        .image(p.getImage())
                        .size(p.getSize())
                        .color(p.getColor())
                        .price(p.getPrice())
                        .description(p.getDescription())
                        .totalStock(p.getTotalStock())
                        .ratings(p.getRatings())
                        .discountedPrice(p.getDiscountedPrice())
                        .discountPercent(p.getDiscountPercent())
                        .build();
                decompressProds.add(prod);
            }
            return ResponseEntity.status(HttpStatus.OK).body(decompressProds);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * This method is used to find all the product which belongs to a certain category
     * @param category It's a String value which contains category name
     * @return It return's list of products which belongs to the category provided in parameter
     */
    public ResponseEntity<List<ProductDTO>> getAllProductByCategory(String category) {
        try {
            List<Product> prods = productDao.findByCategory(category);
            if (prods == null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
            List<ProductDTO> decompressProds = new ArrayList<>();
            for (Product p : prods) {
                p.setImage(ImageUtility.decompressImage(p.getImage()));
                ProductDTO prod = ProductDTO.builder()
                        .productId(p.getId())
                        .productName(p.getName())
                        .categoryName(p.getCategoryName())
                        .subCategoryName(p.getSubCategoryName())
                        .categoryId(p.getCategoryId())
                        .subCategoryId(p.getSubCategoryId())
                        .brand(p.getBrand())
                        .image(p.getImage())
                        .size(p.getSize())
                        .color(p.getColor())
                        .price(p.getPrice())
                        .description(p.getDescription())
                        .totalStock(p.getTotalStock())
                        .ratings(p.getRatings())
                        .discountPercent(p.getDiscountPercent())
                        .discountedPrice(p.getDiscountedPrice())
                        .build();
                decompressProds.add(prod);
            }
            return ResponseEntity.status(HttpStatus.OK).body(decompressProds);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    public ResponseEntity<List<ProductDTO>> getAllProductBySubCategory(UUID subCategoryId) {
        try {
            List<Product> prods = productDao.findBySubCategoryId(subCategoryId);
            if (prods == null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
            List<ProductDTO> decompressProds = new ArrayList<>();
            for (Product p : prods) {
                p.setImage(ImageUtility.decompressImage(p.getImage()));
                ProductDTO prod = ProductDTO.builder()
                        .productId(p.getId())
                        .productName(p.getName())
                        .categoryName(p.getCategoryName())
                        .subCategoryName(p.getSubCategoryName())
                        .categoryId(p.getCategoryId())
                        .subCategoryId(p.getSubCategoryId())
                        .brand(p.getBrand())
                        .image(p.getImage())
                        .size(p.getSize())
                        .color(p.getColor())
                        .price(p.getPrice())
                        .description(p.getDescription())
                        .totalStock(p.getTotalStock())
                        .ratings(p.getRatings())
                        .discountPercent(p.getDiscountPercent())
                        .discountedPrice(p.getDiscountedPrice())
                        .build();
                decompressProds.add(prod);
            }
            return ResponseEntity.status(HttpStatus.OK).body(decompressProds);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

