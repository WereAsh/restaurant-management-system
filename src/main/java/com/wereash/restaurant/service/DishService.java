package com.wereash.restaurant.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wereash.restaurant.dto.DishDto;
import com.wereash.restaurant.entity.Dish;

import java.util.List;

public interface DishService extends IService<Dish> {
    //新增菜品，同时添加菜品的口味数据，需要操作两张表：dish、dish_flavor
    public void saveWithFlavor(DishDto dishDto);
    //根据id查询对应的菜品信息和口味信息
    public DishDto getByIdWithFlavor(Long id);

    public void updateWithFlavor(DishDto dishDto);

    public void deleteById(List<Long> ids);

    public void updateStatus2OneById(List<Long> ids);
    public void updateStatus2ZeroById(List<Long> ids);
}
