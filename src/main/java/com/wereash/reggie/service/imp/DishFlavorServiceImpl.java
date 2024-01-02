package com.wereash.reggie.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wereash.reggie.entity.DishFlavor;
import com.wereash.reggie.mapper.DishFavorMapper;
import com.wereash.reggie.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFavorMapper, DishFlavor> implements DishFlavorService {
}
