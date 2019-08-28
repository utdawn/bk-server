package com.gcwl.bkserver.mapper;

import com.gcwl.bkserver.entity.Goods;
import com.gcwl.bkserver.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.core.annotation.Order;

import java.util.List;

@Mapper
public interface GoodsMapper {
    @Select({"select * from goods"})
    public List<Goods> getGoodsList();

    @Select({"select * from orders"})
    public List<Orders> getOrdersList();

    @Select({"select * from goods where goodsCode=#{goodsCode}"})
    public Goods getGoodsByGoodsCode(String goodsCode);

    @Select({"select * from orders where oid=#{id}"})
    public Orders getOrdersById(int id);

    @Select({"select * from orders o " +
            "left join user u on o.userName=u.userName where o.userName=#{userName}"})
    public List<Orders> getOrdersByUserName(String userName);
}
