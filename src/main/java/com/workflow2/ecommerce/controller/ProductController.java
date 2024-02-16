package com.workflow2.ecommerce.controller;

import com.workflow2.ecommerce.entity.Product;
import com.workflow2.ecommerce.dto.ProductDTO;
import com.workflow2.ecommerce.repository.CategoryDao;
import com.workflow2.ecommerce.repository.SubCategoryDao;
import com.workflow2.ecommerce.services.ProductServiceImpl;
import com.workflow2.ecommerce.util.ImageUtility;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

/**
 * This is the controller class which have all the endpoint for accessing, creating, updating and deleting the products
 * @author krishna_rawat
 * @version v0.0.1
 */
@RestController
@RequestMapping("api/product")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private SubCategoryDao subCategoryDao;

    /**
     * This method is the endpoint method for creating product
     * @param product It is a request part which contains product object and have all attributes of product
     * @param file It is a request part which takes multipart file
     * @return It returns a productDTO object which have all the attributes of product we created
     */
    @PostMapping(value = "/")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ProductDTO> saveProduct(
            @RequestPart("product") ProductDTO product,
            @RequestPart("image") MultipartFile file) {


        try {
            System.out.println("In try");
            Product product1 = Product.builder()
                    .id(UUID.randomUUID())
                    .name(product.getProductName())
                    .brand(product.getBrand())
                    .categoryName(product.getCategoryName())
                    .categoryId(categoryDao.getCategoryByName(product.getCategoryName()).getCategoryId())
                    .subCategoryName(product.getSubCategoryName())
                    .subCategoryId(subCategoryDao.getSubCategoryByName(product.getSubCategoryName()).getSubCategoryId())
                    .price(product.getPrice())
                    .description(product.getDescription())
                    .totalStock(product.getTotalStock())
                    .image(ImageUtility.compressImage(file.getBytes()))
                    .size(product.getSize())
                    .color(product.getColor())
                    .ratings(product.getRatings())
                    .discountPercent(product.getDiscountPercent())
                    .discountedPrice(product.getPrice()-(product.getPrice() * (product.getDiscountPercent() / 100)))
                    .build();

            return productService.saveProduct(product1);
        }catch (Exception e){
            System.out.println("In try catcgh");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * This method have endpoint that return particular product from given productId
     * @param productId It have the product Id of the product we wanted to to find
     * @return It return Product whose product Id is provided in the parameter
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") UUID productId){
        return productService.getProduct(productId);
    }

    /**
     * This method have endpoint for getting image of particular product by productId
     * @param productId It is the product Id of the product whose image we wanted to to find
     * @return It return Product Image whose product Id is provided in the parameter
     */
    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImageById(@PathVariable("id") UUID productId){
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(productService.getProduct(productId).getBody().getImage());
    }

    /**
     * This method have endpoint to return all the product present into the database
     * @return It return list of all the products
     */
    @GetMapping("/")
    public ResponseEntity<List<Product>> getAllProduct(){
        return productService.getAllProducts();
    }

    /**
     * This method have endpoint to update a product in the database whose id is provided in parameter
     * @param id It is the product Id who we wanted to update
     * @param product It is the product object with updated attributes
     * @param file It is the Multipart file which contains product image
     * @return It returns update product
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable("id") UUID id, @RequestPart("product") Product product, @RequestPart("image") MultipartFile file){
        ProductDTO prod = null;
        try{
            if(product.getImage()==null) {
                Product product1 = Product.builder()
                        .name(product.getName())
                        .brand(product.getBrand())
                        .categoryId(product.getCategoryId())
                        .subCategoryId(product.getSubCategoryId())
                        .price(product.getPrice())
                        .image(ImageUtility.compressImage(file.getBytes()))
                        .color(product.getColor())
                        .size(product.getSize())
                        .description(product.getDescription())
                        .totalStock(product.getTotalStock())
                        .ratings(product.getRatings())
                        .discountedPrice(product.getDiscountedPrice())
                        .discountPercent(product.getDiscountPercent())
                        .build();
                return productService.updateProduct(product1, id);
            } else {
                Product product1 = Product.builder()
                        .name(product.getName())
                        .brand(product.getBrand())
                        .categoryId(product.getCategoryId())
                        .subCategoryId(product.getSubCategoryId())
                        .price(product.getPrice())
                        .image(ImageUtility.compressImage(product.getImage()))
                        .color(product.getColor())
                        .size(product.getSize())
                        .description(product.getDescription())
                        .totalStock(product.getTotalStock())
                        .ratings(product.getRatings())
                        .discountedPrice(product.getDiscountedPrice())
                        .build();
                return productService.updateProduct(product1, id);
            }
        }catch (Exception ioe){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    /**
     * This method is the endpoint for deleting a product by its id
     * @param id It contains the product id which we wanted to delete
     * @return It returns the response entity with success message
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity deleteProductById(@PathVariable("id") final UUID id){
            return productService.deleteProduct(id);
    }

    /**
     * This method have endpoint to delete all the methods from the databse
     * @return It returns the response entity with success message
     */
    @DeleteMapping("/")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity deleteAllProduct(){
            return productService.deleteAllProducts();
    }

    /**
     * This method is the endpoint for finding products which belongs to given category
     * @param category It contains the category name whose products we wanted to find
     * @return It returns the list of all the products belong to given category
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductDTO>> getAllProductByCategory(@PathVariable("category") String category){
        return productService.getAllProductByCategory(category);
    }

    @GetMapping("/subCategory/{subCategoryId}")
    public ResponseEntity<List<ProductDTO>> getAllProductBySubCategory(@PathVariable("subCategoryId") UUID subCategoryId){
        return productService.getAllProductBySubCategory(subCategoryId);
    }




}
