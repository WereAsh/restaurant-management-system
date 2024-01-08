package com.wereash.restaurant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wereash.restaurant.dto.SetmealDto;
import com.wereash.restaurant.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    public void saveWithDish(SetmealDto setmealDto);

    public void removeWithDish(List<Long> ids);
    public void updateStatus2One(List<Long> ids);

    public void updateStatus2Zero(List<Long> ids);
}
