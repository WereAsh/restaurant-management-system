package com.wereash.restaurant.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wereash.restaurant.common.R;
import com.wereash.restaurant.entity.Orders;
import com.wereash.restaurant.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: WereAsh
 * @Date:2024-01-03 20:40
 **/

@Slf4j
@RestController
@RequestMapping("order")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String number){
        log.info("page={},pageSize={},name={}",page,pageSize,number);
        //构造分页构造器
        Page pageInfo=new Page(page,pageSize);

        LambdaQueryWrapper<Orders> queryWrapper=new LambdaQueryWrapper<>();

        queryWrapper.like(StringUtils.isNotEmpty(number),Orders::getNumber,number);
        queryWrapper.orderByDesc(Orders::getOrderTime);
        ordersService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }
}
