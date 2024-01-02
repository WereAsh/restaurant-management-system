package com.wereash.reggie.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wereash.reggie.entity.SetmealDish;
import com.wereash.reggie.mapper.SetMealDishMapper;
import com.wereash.reggie.service.SetmealDishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SetmealDishServiceImpl extends ServiceImpl<SetMealDishMapper, SetmealDish> implements SetmealDishService {
}
