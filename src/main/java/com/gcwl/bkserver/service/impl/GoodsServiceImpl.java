package com.gcwl.bkserver.service.impl;

import com.gcwl.bkserver.entity.Goods;
import com.gcwl.bkserver.entity.Orders;
import com.gcwl.bkserver.entity.Seckill;
import com.gcwl.bkserver.mapper.GoodsMapper;
import com.gcwl.bkserver.service.GoodsService;
import com.gcwl.bkserver.service.RedisService;
import com.gcwl.bkserver.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public List<Goods> getGoodsList() {
        List<Goods> goodsList = goodsMapper.getGoodsList();
        for(Goods goods:goodsList){
            String goodsCode = goods.getGoodsCode();
            List<String> iconUrlList = goodsMapper.getGoodsIconUrlByGoodsCode(goodsCode);
            goods.setIconUrl(iconUrlList);
        }
        return goodsList;
    }

    @Override
    public List<Orders> getOrdersList() {
        return goodsMapper.getOrdersList();
    }

    @Override
    public Goods getGoodsByGoodsCode(String goodsCode) {
        Goods goods = goodsMapper.getGoodsByGoodsCode(goodsCode);
        if(null == goods)
            return null;
        List<String> iconUrlList = goodsMapper.getGoodsIconUrlByGoodsCode(goodsCode);
        goods.setIconUrl(iconUrlList);
        return goods;
    }

    @Override
    public List<Goods> getGoodsByGoodsName(String goodsName) {
        return goodsMapper.getGoodsByGoodsName(goodsName);
    }

    @Override
    public Orders getOrdersById(int id) {
        return goodsMapper.getOrdersById(id);
    }

    @Override
    public List<Orders> getOrdersByUserName(String userName) {
        return goodsMapper.getOrdersByUserName(userName);
    }

    @Override
    public List<Orders> getOrdersByUserNameAndGoodsCode(String userName, String goodsCode){
        return goodsMapper.getOrdersByUserNameAndGoodsCode(userName,goodsCode);
    }

    /**
     * 秒杀
     * @param userName
     * @param goodsCode
     * @return
     */
    @Override
    public synchronized Result doSeckill(String userName, String goodsCode){
        //判断库存量
        Seckill seckill  = goodsMapper.getSekillByGoodsCode(goodsCode);
//        int counts = seckill.getCounts();
        int counts = (Integer) redisService.get("counts");
        if(counts <= 0){
            return Result.error("手慢了，已被抢购空了!");
        }
        //取出该用户与该商品的有关订单信息，
        //包括非抢购时段购买信息和抢购时段购买信息
        List<Orders> ordersList = goodsMapper.getOrdersByUserNameAndGoodsCode(userName,goodsCode);
        //获取秒杀商品的抢购时间
        //用以判断用户是否已经抢购过该商品
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        for(Orders orders: ordersList){
            Date payTime = orders.getPayTime();
            System.out.println("------抢购时间："+payTime);
            System.out.println("开始时间："+startTime);
            System.out.println("结束时间："+endTime);
            if(payTime.compareTo(startTime) >= 0 && payTime.compareTo(endTime) <= 0){
                return Result.error("已抢购过该商品！");
            }
        }
        //减库存 下订单
        counts = counts - 1;
        seckill.setCounts(counts);
//        goodsMapper.updateSeckillByGoodsCode(seckill);
        redisService.set("counts", counts);
        Orders orders = new Orders();
        orders.setGoodsCode(goodsCode);
        orders.setUserName(userName);
        orders.setCounts(1);
        orders.setPayTime(new Date());
        orders.setPay(seckill.getSeckillPrice());
        goodsMapper.addOrders(orders);
        return Result.success("恭喜你，抢购成功！");
    }

    @Override
    public Result addSeckill(Seckill seckill) {
        Seckill sec = goodsMapper.getSekillByGoodsCode(seckill.getGoodsCode());
        if(null != sec)
            return Result.error("添加失败，抢购商品已存在");
        goodsMapper.addSeckill(seckill);
        return Result.success("添加成功");
    }

    @Override
    public int getCounts(String goodsCode) {
        Seckill seckill  = goodsMapper.getSekillByGoodsCode(goodsCode);
        int counts = seckill.getCounts();
        return counts;
    }

    @Override
    public Result endSecondKill(String goodsCode, int counts) {
        goodsMapper.reduceSeckillByGoodsCode(goodsCode, counts);
        return Result.success("");
    }


}
