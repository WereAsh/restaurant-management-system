package com.wereash.reggie.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wereash.reggie.common.CustomException;
import com.wereash.reggie.entity.Category;
import com.wereash.reggie.entity.Dish;
import com.wereash.reggie.entity.Setmeal;
import com.wereash.reggie.mapper.CategoryMapper;
import com.wereash.reggie.service.CategoryService;
import com.wereash.reggie.service.DishService;
import com.wereash.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImp extends ServiceImpl<CategoryMapper, Category> implements CategoryService{
    /*
    * 根据id删除分类，删除之前需要进行判断
    * */
    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper=new LambdaQueryWrapper<>();
        //添加查询条件，根据分类id进行查询
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count1=dishService.count(dishLambdaQueryWrapper);
        //查询当前分类是否关联了菜品，如果关联，就报出一个业务异常
        if (count1>0){
            throw new CustomException("当前分类下关联菜品，不能删除");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper=new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2=setmealService.count(setmealLambdaQueryWrapper);
        //查询当前分类是否关联了菜品，如果关联，就报出一个业务异常
        if (count2>0){
            throw new CustomException("当前分类下关联菜品，不能删除");
        }
        //正常删除分类
        super.removeById(id);
    }
}
