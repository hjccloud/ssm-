package com.he.servicce;

import com.he.domain.Orders;

import java.util.List;

public interface OrdersService {

    Orders findById(Integer id) throws Exception;

    List<Orders> findAll(int page,int size) throws Exception;
}
