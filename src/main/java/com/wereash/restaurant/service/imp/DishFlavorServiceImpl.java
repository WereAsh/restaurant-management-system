package com.wereash.restaurant.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wereash.restaurant.entity.DishFlavor;
import com.wereash.restaurant.mapper.DishFavorMapper;
import com.wereash.restaurant.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFavorMapper, DishFlavor> implements DishFlavorService {
}
