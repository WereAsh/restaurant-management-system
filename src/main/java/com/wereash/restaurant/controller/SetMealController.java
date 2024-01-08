package com.wereash.restaurant.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wereash.restaurant.common.R;
import com.wereash.restaurant.dto.SetmealDto;
import com.wereash.restaurant.entity.Category;
import com.wereash.restaurant.entity.Setmeal;
import com.wereash.restaurant.service.CategoryService;
import com.wereash.restaurant.service.SetmealDishService;
import com.wereash.restaurant.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetMealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{id}")
    public R<SetmealDto> getSetmealByDish(@PathVariable Long id){
        log.info("setmeal id= {}",id);
        SetmealDto setmealDto = setmealDishService.getSetmealDto(id);



        return R.success(setmealDto);
    }

    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        log.info("新增菜品信息：{}",setmealDto);

        setmealService.saveWithDish(setmealDto);

        return R.success("新增套餐成功！");
    }

    @PutMapping
    public R<String> savaModify(@RequestBody SetmealDto setmealDto){
        setmealService.updateById(setmealDto);
        return R.success("修改套餐成功！");
    }
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){

        Page<Setmeal> pageInfo=new Page<>(page,pageSize);

        Page<SetmealDto> dtoPage=new Page<>();

        LambdaQueryWrapper<Setmeal> queryWrapper=new LambdaQueryWrapper<>();

        queryWrapper.like(name!=null,Setmeal::getName,name);

        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        setmealService.page(pageInfo,queryWrapper);

        BeanUtils.copyProperties(pageInfo,dtoPage,"records");

        List<Setmeal> records=pageInfo.getRecords();

        List<SetmealDto> list = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            //对象拷贝
            BeanUtils.copyProperties(item,setmealDto);

            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());

        dtoPage.setRecords(list);
        return R.success(dtoPage);
    }

    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        log.info("ids{}",ids);
        setmealService.removeWithDish(ids);
        return R.success("删除成功！");
    }
    @PostMapping("/status/1")
    public R<String> setStatus21(@RequestParam List<Long> ids){
        log.info("{}",ids);
        setmealService.updateStatus2One(ids);
        return R.success("修改分类状态成功");
    }
    @PostMapping("/status/0")
    public R<String> setStatus20(@RequestParam List<Long> ids){
        log.info("{}",ids);
        setmealService.updateStatus2Zero(ids);
        return R.success("修改分类状态成功");
    }
}
