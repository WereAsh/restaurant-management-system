package com.wereash.reggie.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wereash.reggie.dto.DishDto;
import com.wereash.reggie.entity.Dish;
import com.wereash.reggie.entity.DishFlavor;
import com.wereash.reggie.mapper.DishMapper;
import com.wereash.reggie.service.DishFlavorService;
import com.wereash.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;
    //新增菜品，并保存口味信息

    /*
    * 涉及到多张表的控制，加入@Transitional,在启动类上开始事务的支持
    * */
    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        //保存菜品数据到dish表中
        this.save(dishDto);

        Long dishId = dishDto.getId();

        List<DishFlavor> flavors= dishDto.getFlavors();
        flavors.forEach(f->f.setDishId(dishId));

/*        flavors=flavors.stream().map((item)->{
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());*/
        //保存菜品口味数据到菜品口味表：dish_flavor
        dishFlavorService.saveBatch(flavors);
    }

    @Override
    public DishDto getByIdWithFlavor(Long id) {
        //查询菜品基本信息
        Dish dish=this.getById(id);

        DishDto dishDto=new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
        //查询口味信息
        LambdaQueryWrapper<DishFlavor> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> flavors=dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavors);


        return dishDto;
    }

    @Transactional
    @Override
    public void updateWithFlavor(DishDto dishDto) {
        //更新菜品信息
        this.updateById(dishDto);
        /*
        * 先清理原来的口味信息 dish_flavor进行delete操作
        * 在添加当前提交的口味数据 dish_flavor进行insert操作
        * */

        LambdaQueryWrapper<DishFlavor> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dishDto.getId());

        dishFlavorService.remove(queryWrapper);

        List<DishFlavor> flavors=dishDto.getFlavors();

        flavors.forEach(f->f.setDishId(dishDto.getId()));
        dishFlavorService.saveBatch(flavors);
    }

    @Override
    public void deleteById(List<Long> ids) {
        removeByIds(ids);
    }

    @Override
    public void updateStatus2OneById(List<Long> ids) {
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Dish::getId, ids);
        List<Dish> list = this.list(queryWrapper);
        list.forEach(dish -> {
            dish.setStatus(1);
            updateById(dish);
        });;

    }

    @Override
    public void updateStatus2ZeroById(List<Long> ids) {
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Dish::getId, ids);
        List<Dish> list = this.list(queryWrapper);
        list.forEach(dish -> {
            dish.setStatus(0);
            updateById(dish);
        });
    }


}
