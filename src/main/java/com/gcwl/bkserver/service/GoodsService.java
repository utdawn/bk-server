package com.gcwl.bkserver.service;

import com.gcwl.bkserver.entity.Goods;
import com.gcwl.bkserver.entity.Orders;
import com.gcwl.bkserver.entity.Seckill;
import com.gcwl.bkserver.util.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GoodsService {
    public List<Goods> getGoodsList();

    public List<Orders> getOrdersList();

    public Result getGoodsByGoodsCode(String goodsCode);

    public List<Goods> getGoodsByGoodsName(String goodsName);

    public Orders getOrdersById(int id);

    public List<Orders> getOrdersByUserName(String userName);

    public List<Orders> getOrdersByUserNameAndGoodsCode(String userName, String goodsCode);

    public Result doSeckill(String userName, String goodsCode);

    public Result addSeckill(Seckill seckill);

//    public int getCounts(String goodsCode);

    public Result beginSecondKill(String goodsCode);

    public Result endSecondKill(String goodsCode);

}
