package com.gcwl.bkserver.mapper;

import com.gcwl.bkserver.entity.Goods;
import com.gcwl.bkserver.entity.Orders;
import com.gcwl.bkserver.entity.Seckill;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface GoodsMapper {

    /* ----- Goods ----- */
    @Select({"select * from goods"})
    public List<Goods> getGoodsList();

    @Select({"select * from goods where goodsCode=#{goodsCode}"})
    public Goods getGoodsByGoodsCode(String goodsCode);

    @Select({"select * from goods where goodsName like CONCAT('%',#{goodsName},'%')"})
    public List<Goods> getGoodsByGoodsName(String goodsName);

    @Select({"select iconUrl \n" +
            "from goods_icon_url \n" +
            "where goodsCode=#{goodsCode}"})
    public List<String> getGoodsIconUrlByGoodsCode(String goodsCode);

    @Insert({"insert into goods (goodsCode,goodsName,counts,discount,price,description) " +
            "values (#{goodsCode},#{goodsName},#{counts},#{discount},#{price},#{description})"})
    public int addGoods(Goods goods);



    /* ----- orders ----- */
    @Select({"select * from orders"})
    public List<Orders> getOrdersList();

    @Select({"select * from orders where oid=#{id}"})
    public Orders getOrdersById(int id);

    @Select({"select * from orders o " +
            "where o.userName=#{userName}"})
    public List<Orders> getOrdersByUserName(String userName);

    @Select({"select * from orders o " +
            "where o.userName=#{userName} and o.goodsCode=#{goodsCode}"})
    public List<Orders> getOrdersByUserNameAndGoodsCode(String userName, String goodsCode);

    @Insert({"insert into orders (userName,goodsCode,counts,payTime,pay)" +
            " values (#{userName},#{goodsCode},#{counts},#{payTime},#{pay})"})
    public int addOrders(Orders orders);



    /* ----- seckill ----- */
    @Select({"select * from seckill where goodsCode=#{goodsCode}"})
    public Seckill getSekillByGoodsCode(String goodsCode);

    @Insert({"insert into seckill (goodsCode,seckillPrice,counts,startTime,endTime)" +
            " values (#{goodsCode},#{seckillPrice},#{counts},#{startTime},#{endTime})"})
    public int addSeckill(Seckill seckill);

    @Update({"update seckill set " +
            "goodsCode=#{goodsCode},seckillPrice=#{seckillPrice},counts=#{counts}," +
            "startTime=#{startTime},endTime=#{endTime}" +
            "where goodsCode=#{goodsCode}"})
    public int updateSeckillByGoodsCode(Seckill seckill);

    @Update({"update seckill " +
            "set counts = #{counts} " +
            "where goodsCode = #{goodsCode} and counts > 0"})
    public int reduceSeckillByGoodsCode(String goodsCode, int counts);
}
