package com.wereash.reggie.dto;

import com.wereash.reggie.entity.Setmeal;
import com.wereash.reggie.entity.SetmealDish;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;

    private BigDecimal price;

    private String name;
}
