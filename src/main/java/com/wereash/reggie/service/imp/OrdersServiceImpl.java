package com.wereash.reggie.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wereash.reggie.dto.DishDto;
import com.wereash.reggie.entity.Dish;
import com.wereash.reggie.entity.DishFlavor;
import com.wereash.reggie.entity.Orders;
import com.wereash.reggie.mapper.DishMapper;
import com.wereash.reggie.mapper.OrdersMapper;
import com.wereash.reggie.service.DishFlavorService;
import com.wereash.reggie.service.DishService;
import com.wereash.reggie.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
}
