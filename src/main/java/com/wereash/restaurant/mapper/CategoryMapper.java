package com.wereash.restaurant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wereash.restaurant.entity.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
