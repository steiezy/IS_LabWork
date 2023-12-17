package com.lab.airbnb.domain.vo;
import com.lab.airbnb.model.House;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class OrderVo {
    @NotNull
    @NotBlank
    private Double price;
    @NotNull
    @NotBlank
    private House house;
    @NotNull
    @NotBlank
    private String leastTime;

}
