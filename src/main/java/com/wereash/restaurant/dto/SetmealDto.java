package com.wereash.restaurant.dto;

import com.wereash.restaurant.entity.Setmeal;
import com.wereash.restaurant.entity.SetmealDish;
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
