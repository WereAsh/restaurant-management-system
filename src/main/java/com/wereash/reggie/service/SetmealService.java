package com.wereash.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wereash.reggie.dto.SetmealDto;
import com.wereash.reggie.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    public void saveWithDish(SetmealDto setmealDto);

    public void removeWithDish(List<Long> ids);
    public void updateStatus2One(List<Long> ids);

    public void updateStatus2Zero(List<Long> ids);
}
