package com.gcwl.bkserver.controller;

import com.gcwl.bkserver.entity.Goods;
import com.gcwl.bkserver.entity.Orders;
import com.gcwl.bkserver.service.impl.GoodsServiceImpl;
import com.gcwl.bkserver.util.Result;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Api
@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsServiceImpl goodsServiceimpl;

    /**
     * 获取所有商品
     * 角色：管理员
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
        return Result.build("0000","成功", goodsServiceimpl.getGoodsByGoodsCode(goodsCode));
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

    @PostMapping("/doSeckill")
    @ResponseBody
    public Result doSeckill(String userName, String goodsCode){
        return goodsServiceimpl.doSeckill(userName,goodsCode);
    }
}
