package com.workflow2.ecommerce.services;

import com.workflow2.ecommerce.dto.ProductDTO;
import com.workflow2.ecommerce.entity.Category;
import com.workflow2.ecommerce.entity.Product;
import com.workflow2.ecommerce.repository.CategoryDao;
import com.workflow2.ecommerce.repository.ProductDao;
import com.workflow2.ecommerce.util.ImageUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductRecommendationService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CategoryDao categoryDao;

    private ProductDTO convertToProductDTO(Product product) throws Exception {
        Category category = categoryDao.findById(product.getCategoryId()).get();
        return ProductDTO.builder().productId(product.getId()).productName(product.getName()).categoryName(category.getCategoryName())
                .brand(product.getBrand()).image(ImageUtility.decompressImage(product.getImage()))
                .price(product.getPrice()).ratings(product.getRatings()).discountedPrice(product.getDiscountedPrice())
                .totalStock(product.getTotalStock()).color(product.getColor()).size(product.getSize())
                .description(product.getDescription()).build();
    }

    public List<ProductDTO> recommendProduct(UUID productId) throws Exception {
        Product product = productDao.getReferenceById(productId);
        Category category = categoryDao.findById(product.getCategoryId()).get();
        List<Product> recommendedProducts = productDao.findRecommendedProducts(productId, category.getCategoryName(), product.getBrand());
        Set<Product> productSet = new HashSet<>();
        Random rdm = new Random();
        while(productSet.size()<Math.min(3, recommendedProducts.size())){
            int i = rdm.nextInt(recommendedProducts.size()-1);
            productSet.add(recommendedProducts.get(i));
        }
        List<ProductDTO> recommendedProductList = new ArrayList<>();
        for (Product product1:productSet){
            recommendedProductList.add(convertToProductDTO(product1));
        }
        return recommendedProductList;
    }
}
