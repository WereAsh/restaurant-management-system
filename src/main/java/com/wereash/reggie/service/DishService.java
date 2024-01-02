package com.wereash.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wereash.reggie.dto.DishDto;
import com.wereash.reggie.entity.Dish;

public interface DishService extends IService<Dish> {
    //新增菜品，同时添加菜品的口味数据，需要操作两张表：dish、dish_flavor
    public void saveWithFlavor(DishDto dishDto);
    //根据id查询对应的菜品信息和口味信息
    public DishDto getByIdWithFlavor(Long id);

    public void updateWithFlavor(DishDto dishDto);
}
