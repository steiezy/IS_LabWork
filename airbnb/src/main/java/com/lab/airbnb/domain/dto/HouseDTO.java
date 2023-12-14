package com.lab.airbnb.domain.dto;

import com.lab.airbnb.model.Photo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for {@link com.lab.airbnb.model.House}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class HouseDTO implements Serializable {
    private String houseId;
    private UserDTO user;
    private String title;
    private String description;
    private String location;
    private String owner;
    private String status;
    private Double longitude;
    private Double latitude;
    private String proportion;
    private String category;
    private List<Photo> photos = new ArrayList<>();
}