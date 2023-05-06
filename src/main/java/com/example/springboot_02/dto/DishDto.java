package com.example.springboot_02.dto;

import com.example.springboot_02.pojo.Dish;
import com.example.springboot_02.pojo.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
