package com.gcwl.bkserver.service.impl;

import com.gcwl.bkserver.entity.Goods;
import com.gcwl.bkserver.entity.Orders;
import com.gcwl.bkserver.entity.Seckill;
import com.gcwl.bkserver.mapper.GoodsMapper;
import com.gcwl.bkserver.service.GoodsService;
import com.gcwl.bkserver.service.JedisCache;
import com.gcwl.bkserver.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class GoodsServiceImpl implements GoodsService {

    private static final String KEY_GOODS = "goods";
    private static final String KEY_SECKILL = "seckill";
    private static final String KEY_ORDERS = "orders";

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private JedisCache jedisCache;

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
    public Result getGoodsByGoodsCode(String goodsCode) {
        Goods goods = null ;
        if(false == jedisCache.hexists(goodsCode,"counts")){
            //无缓存，表示不在秒杀状态中，则从数据库中取
            goods = goodsMapper.getGoodsByGoodsCode(goodsCode);
            if(null == goods){
                return Result.error("暂无该商品");
            }
        }else{
            Seckill seckill =  goodsMapper.getSekillByGoodsCode(goodsCode);
            //从redis中取出当前余量
            String countsStr = jedisCache.hget(goodsCode, "counts");
            int counts = Integer.parseInt(countsStr);
            goods.setCounts(counts);
        }
        List<String> iconUrlList = goodsMapper.getGoodsIconUrlByGoodsCode(goodsCode);
        goods.setIconUrl(iconUrlList);
        return Result.build("9999","成功", goods);
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
        if(false == jedisCache.hexists(goodsCode, "counts")){
            return Result.error("尚未开启抢购");
        }
        System.out.println("********* 开始秒杀 ********");
        //取出该商品库存量
        String countsStr  = jedisCache.hget(goodsCode, "counts");
        int counts = Integer.parseInt(countsStr);
        if(counts <= 0){
            return Result.error("手慢了，已被抢购空了!");
        }
        //判断该用户是否已抢购过该商品，
        if(jedisCache.hexists(goodsCode,userName)){
            return Result.error("已抢购过该商品！");
        }
        //减库存
        counts = counts - 1;
        jedisCache.hset(goodsCode,"counts", counts+"");
        //获取当前时间，存订单
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String dateStr = formatter.format(currentTime);
        jedisCache.hset(goodsCode,userName,dateStr);
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
    public Result beginSecondKill(String goodsCode){
        if(jedisCache.hexists(goodsCode, "counts")){
            return Result.error("无法重复开启秒杀");
        }
        Seckill seckill = goodsMapper.getSekillByGoodsCode(goodsCode);
        int counts = seckill.getCounts();
        jedisCache.hset(goodsCode,"counts", counts+"");
        return Result.success("开启秒杀");
    }

    @Override
    public Result endSecondKill(String goodsCode) {
        if(false == jedisCache.hexists(goodsCode, "counts")){
            return Result.error("无法结束秒杀，请先开启该商品秒杀");
        }
        Map<String,String> userNameMap = jedisCache.hgetAll(goodsCode);
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        Seckill seckill =  goodsMapper.getSekillByGoodsCode(goodsCode);
        double price = seckill.getSeckillPrice();
        //将所有用户和该商品的抢购订单加入数据库中
        for (Map.Entry<String, String> entry : userNameMap.entrySet()) {
            if(entry.getKey().equals("counts"))
                continue;
            Orders orders = new Orders();
            orders.setUserName(entry.getKey());
            orders.setGoodsCode(goodsCode);
            orders.setCounts(1);
            orders.setPay(price);
            try {
                orders.setPayTime(format.parse(entry.getValue()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            goodsMapper.addOrders(orders);
        }
        int counts = Integer.parseInt(jedisCache.hget(goodsCode, "counts"));
        //更新数据库中该抢购商品的数量
        goodsMapper.reduceSeckillByGoodsCode(goodsCode, counts);
        //移除相应的key
        jedisCache.delKey(goodsCode);
        return Result.success("结束秒杀");
    }


}
