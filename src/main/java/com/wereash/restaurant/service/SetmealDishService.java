package com.wereash.restaurant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wereash.restaurant.dto.SetmealDto;
import com.wereash.restaurant.entity.SetmealDish;

public interface SetmealDishService extends IService<SetmealDish> {
    public SetmealDto getSetmealDto(Long id);
}
