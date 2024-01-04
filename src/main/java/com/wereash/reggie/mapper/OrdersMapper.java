package com.wereash.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wereash.reggie.entity.Orders;
import com.wereash.reggie.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
}
