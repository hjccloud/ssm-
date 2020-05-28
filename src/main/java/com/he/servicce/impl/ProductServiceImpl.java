package com.he.servicce.impl;

import com.github.pagehelper.PageHelper;
import com.he.dao.ProductDao;
import com.he.domain.Product;
import com.he.servicce.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productService")

public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;


    public List<Product> findAll(int page,int size) throws Exception {
        PageHelper.startPage(page,size);
        return productDao.findAll();
    }

    @Override
    public void save(Product product) throws Exception{
        productDao.save(product);
    }


}
