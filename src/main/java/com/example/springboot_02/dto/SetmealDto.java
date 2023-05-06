package com.example.springboot_02.dto;

import com.example.springboot_02.pojo.Setmeal;
import com.example.springboot_02.pojo.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
