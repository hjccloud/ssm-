package com.he.servicce.impl;

import com.github.pagehelper.PageHelper;
import com.he.dao.OrdersDao;
import com.he.domain.Orders;
import com.he.servicce.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("orderService")
public class OrdersServiceImpl  implements OrdersService{

    @Autowired
    private OrdersDao ordersDao;


    public Orders findById(Integer id) throws Exception {
        return ordersDao.findById(id);
    }

    public List<Orders> findAll(int page,int size) throws Exception {
        //参数pageNum是页码值  参数pageSize代表每页显示条数，必须写在查询方法之前
        PageHelper.startPage(page,size);
        return ordersDao.findAll();
    }
}
