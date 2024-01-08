package com.wereash.restaurant.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wereash.restaurant.dto.SetmealDto;
import com.wereash.restaurant.entity.Setmeal;
import com.wereash.restaurant.entity.SetmealDish;
import com.wereash.restaurant.mapper.SetMealDishMapper;
import com.wereash.restaurant.service.SetmealDishService;
import com.wereash.restaurant.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SetmealDishServiceImpl extends ServiceImpl<SetMealDishMapper, SetmealDish> implements SetmealDishService {

    @Autowired
    private SetmealService setmealService;


    @Override
    public SetmealDto getSetmealDto(Long id) {
        Setmeal setmeal = setmealService.getById(id);
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,id);
        List<SetmealDish> list = this.list(queryWrapper);
        SetmealDto setmealDto=new SetmealDto();
        BeanUtils.copyProperties(setmeal,setmealDto);
        setmealDto.setSetmealDishes(list);
        return setmealDto;
    }
}
