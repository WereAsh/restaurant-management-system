package com.wereash.restaurant.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wereash.restaurant.common.CustomException;
import com.wereash.restaurant.dto.SetmealDto;
import com.wereash.restaurant.entity.Setmeal;
import com.wereash.restaurant.entity.SetmealDish;
import com.wereash.restaurant.mapper.SetmealMapper;
import com.wereash.restaurant.service.SetmealDishService;
import com.wereash.restaurant.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper,Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    /*
    * 新增套餐，同时需要保存套餐和菜品的关联关系
    * */
    @Override
    public void saveWithDish(SetmealDto setmealDto) {
        //保存套餐的基本信息，操作setmeal表，执行insert操作
        this.save(setmealDto);
        //保存套餐和菜品的关联信息，操作setmeal_dish表，执行inser操作
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();

        setmealDishes.forEach(s->s.setSetmealId(setmealDto.getId()));

        setmealDishService.saveBatch(setmealDishes);


    }

    /*
    * 删除套餐，并需要删除套餐的关联数据
    * */
    @Override
    @Transactional
    public void removeWithDish(List<Long> ids) {
        //查询套餐状态，确定是否可以删除
        LambdaQueryWrapper<Setmeal> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.in(Setmeal::getId,ids);
        queryWrapper.eq(Setmeal::getStatus,1);
        int count=this.count(queryWrapper);
        if(count>0){
            //如果不能删除，抛出异常
            throw new CustomException("套餐正在售卖中，不能删除！");
        }

        //如果可以删除，先删除setmeal表中的数据
        this.removeByIds(ids);
        //删除关系表中数据——setmeal_dish
        LambdaQueryWrapper<SetmealDish> queryWrapper1=new LambdaQueryWrapper<>();

        queryWrapper1.in(SetmealDish::getSetmealId,ids);

        setmealDishService.remove(queryWrapper1);

    }
    @Override
    public void updateStatus2One(List<Long> ids) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId,ids);
        List<Setmeal> list = list(queryWrapper);
        list.forEach(setmeal -> {
            setmeal.setStatus(1);
            this.updateById(setmeal);
        });

    }

    @Override
    public void updateStatus2Zero(List<Long> ids) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId,ids);
        List<Setmeal> list = list(queryWrapper);
        list.forEach(setmeal -> {
            setmeal.setStatus(0);
            this.updateById(setmeal);
        });
    }
}
