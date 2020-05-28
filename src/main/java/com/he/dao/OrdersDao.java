package com.he.dao;

import com.he.domain.Member;
import com.he.domain.Orders;
import com.he.domain.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface OrdersDao {

    //使用注解方式实现一多一的多表查询
    //查询所有订单的信息包括product
    @Select("select * from orders")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "orderNum",property = "orderNum"),
            @Result(column = "orderTime",property = "orderTime"),
            @Result(column = "orderStatus",property = "orderStatus"),
            @Result(column = "payType",property = "payType"),
            @Result(column = "orderDesc",property = "orderDesc"),
            @Result(column = "productId",property = "product",one = @One(select = "com.he.dao.ProductDao.findById"))
    })
    List<Orders> findAll() throws Exception;

    //多表操作
    @Select("select * from orders where id = #{id}")
    @Results({
            @Result(id = true,column = "id",property = "id"),
            @Result(column = "orderNum",property = "orderNum"),
            @Result(column = "orderTime",property = "orderTime"),
            @Result(column = "orderStatus",property = "orderStatus"),
            @Result(column = "payType",property = "payType"),
            @Result(column = "orderDesc",property = "orderDesc"),
            @Result(column = "productId",property = "product",javaType =Product.class,one = @One(select = "com.he.dao.ProductDao.findById")),
            @Result(property = "member",column = "memberId",javaType = Member.class,one = @One(select = "com.he.dao.MemberDao.findById")),
            @Result(property = "travellers",column = "id",javaType =java.util. List.class,many = @Many(select = "com.he.dao.TravellerDao.findById"))
    })
    Orders findById(Integer ordersId) throws Exception;
}
