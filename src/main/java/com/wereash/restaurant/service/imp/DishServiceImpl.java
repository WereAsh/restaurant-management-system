package com.wereash.restaurant.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wereash.restaurant.common.CustomException;
import com.wereash.restaurant.dto.DishDto;
import com.wereash.restaurant.entity.Dish;
import com.wereash.restaurant.entity.DishFlavor;
import com.wereash.restaurant.entity.SetmealDish;
import com.wereash.restaurant.mapper.DishMapper;
import com.wereash.restaurant.service.DishFlavorService;
import com.wereash.restaurant.service.DishService;
import com.wereash.restaurant.service.SetmealDishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private SetmealDishService setmealDishService;

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
        LambdaQueryWrapper<Dish> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.in(Dish::getId,ids);
        queryWrapper.eq(Dish::getStatus,1);
        int count=this.count(queryWrapper);
        if(count>0){
            throw new CustomException("菜品正在售卖中，不能删除！");
        }
        this.removeByIds(ids);
        /*
        * 删除菜品以及对应的套餐
        * */
        LambdaQueryWrapper<SetmealDish> queryWrapper1=new LambdaQueryWrapper<>();
        queryWrapper1.in(SetmealDish::getDishId,ids);
        setmealDishService.remove(queryWrapper1);
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
