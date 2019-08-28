package com.gcwl.bkserver.service.impl;

import com.gcwl.bkserver.entity.Goods;
import com.gcwl.bkserver.entity.Orders;
import com.gcwl.bkserver.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public List<Goods> getGoodsList() {
        return goodsMapper.getGoodsList();
    }

    @Override
    public List<Orders> getOrdersList() {
        return goodsMapper.getOrdersList();
    }

    @Override
    public Goods getGoodsByGoodsCode(String goodsCode) {
        return goodsMapper.getGoodsByGoodsCode(goodsCode);
    }

    @Override
    public Orders getOrdersById(int id) {
        return goodsMapper.getOrdersById(id);
    }

    @Override
    public List<Orders> getOrdersByUserName(String userName) {
        return goodsMapper.getOrdersByUserName(userName);
    }
}
