package com.gcwl.bkserver.controller;

import com.gcwl.bkserver.entity.Seckill;
import com.gcwl.bkserver.service.impl.GoodsServiceImpl;
import com.gcwl.bkserver.util.Result;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Api
@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsServiceImpl goodsServiceimpl;

    /**
     * 获取所有商品
     * @return
     */
    @PostMapping("/getGoodsList")
    @ResponseBody
    public Result getGoodsList() {
        return Result.build("0000","成功", goodsServiceimpl.getGoodsList());
    }

    /**
     * 获取所有订单
     * 角色：管理员
     * @return
     */
    @PostMapping("/getOrdersList")
    @RequiresRoles("r0001")
    @ResponseBody
    public Result getOrdersList() {
        return Result.build("0000","成功", goodsServiceimpl.getOrdersList());
    }

    /**
     * 获取某个商品信息
     * @param goodsCode
     * @return
     */
    @PostMapping("/getGoodsByGoodsCode")
    @ResponseBody
    public Result getGoodsByGoodsCode(String goodsCode) {
        return goodsServiceimpl.getGoodsByGoodsCode(goodsCode);
    }

    /**
     * 获取某个订单详情
     * @param id
     * @return
     */
    @PostMapping("/getOrdersById")
    @ResponseBody
    public Result getOrdersById(int id) {
        return Result.build("0000","成功", goodsServiceimpl.getOrdersById(id));
    }

    /**
     * 获取用户自己的所有订单
     * @param userName
     * @return
     */
    @PostMapping("/getOrdersByUserName")
    @ResponseBody
    public Result getOrdersByUserName(String userName) {
        return Result.build("0000","成功", goodsServiceimpl.getOrdersByUserName(userName));
    }

    /**
     * 秒杀
     * @param userName
     * @param goodsCode
     * @return
     */
    @PostMapping("/doSeckill")
    @ResponseBody
    public Result doSeckill(String userName, String goodsCode){
        return goodsServiceimpl.doSeckill(userName,goodsCode);
    }

    @PostMapping("/doSeckill2")
    @ResponseBody
    public Result doSeckill2(String userName, String goodsCode) {
        return goodsServiceimpl.doSeckill2(userName,goodsCode);
    }

    /**
     * 添加抢购商品名单
     * @param seckill
     * @return
     */
    @PostMapping("/addSeckill")
    @ResponseBody
    public Result addSeckill(Seckill seckill) {
        return goodsServiceimpl.addSeckill(seckill);
    }

    @GetMapping("/beginSecondKill")
    @ResponseBody
    public Result beginSecondKill(String goodsCode) {
        return goodsServiceimpl.beginSecondKill(goodsCode);
    }

    @GetMapping("/endSecondKill")
    @ResponseBody
    public Result endSecondKill(String goodsCode) {
        goodsServiceimpl.endSecondKill(goodsCode);
        return Result.success("结束秒杀");
    }

    @GetMapping("/endSecondKill2")
    @ResponseBody
    public Result endSecondKill2(String goodsCode){
        goodsServiceimpl.endSecondKill2(goodsCode);
        return Result.success("结束秒杀");
    }

}
