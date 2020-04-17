package com.damgor.foodapp.service;


import com.damgor.foodapp.model.Product;
import com.damgor.foodapp.model.ShortProduct;

import java.util.List;

public interface ProductService {
    List<ShortProduct> getSpecificProducts(String productName, int number, int offset, long userId);
    Product getGeneralProducts(String productName, long userId);
    Product getProductById(String productId, long userId);
}
