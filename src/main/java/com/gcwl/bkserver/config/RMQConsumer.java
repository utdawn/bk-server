package com.gcwl.bkserver.config;

import com.gcwl.bkserver.entity.Orders;
import com.gcwl.bkserver.entity.Seckill;
import com.gcwl.bkserver.mapper.GoodsMapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RabbitListener(queues = "orderQueue")
public class RMQConsumer {

    @Autowired
    GoodsMapper goodsMapper;

    @RabbitHandler
    public void process(Orders orders) {
        Seckill seckill = goodsMapper.getSekillByGoodsCode(orders.getGoodsCode());
        orders.setPay(seckill.getSeckillPrice());
        //下订单
        goodsMapper.addOrders(orders);
        System.out.println("添加订单成功");
    }

}
