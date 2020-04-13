package com.damgor.foodapp.service;


import com.damgor.foodapp.model.Product;
import com.damgor.foodapp.model.ShortProduct;

import java.util.List;

public interface ProductService {
    List<ShortProduct> getSpecificProducts(String productName, int number, int offset);
    Product getGeneralProducts(String productName);
    Product getProductById(String productId);
}
