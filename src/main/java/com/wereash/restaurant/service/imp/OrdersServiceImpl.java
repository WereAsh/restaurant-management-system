package com.wereash.restaurant.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wereash.restaurant.entity.Orders;
import com.wereash.restaurant.mapper.OrdersMapper;
import com.wereash.restaurant.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
}
