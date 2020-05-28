package com.he.servicce;

import com.he.domain.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll(int page,int size) throws Exception;

    void save(Product product) throws Exception;
}
