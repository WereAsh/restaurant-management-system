package com.wereash.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wereash.reggie.dto.SetmealDto;
import com.wereash.reggie.entity.SetmealDish;

import java.util.List;

public interface SetmealDishService extends IService<SetmealDish> {
    public SetmealDto getSetmealDto(Long id);
}
