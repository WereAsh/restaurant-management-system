package com.wereash.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wereash.reggie.entity.Category;

import java.util.List;

public interface CategoryService extends IService<Category> {
    public void remove(Long id);

}
