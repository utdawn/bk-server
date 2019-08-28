package com.gcwl.bkserver.service.impl;

import com.gcwl.bkserver.entity.Goods;
import com.gcwl.bkserver.entity.Orders;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GoodsService {
    public List<Goods> getGoodsList();

    public List<Orders> getOrdersList();

    public Goods getGoodsByGoodsCode(String goodsCode);

    public Orders getOrdersById(int id);

    public List<Orders> getOrdersByUserName(String userName);
}
