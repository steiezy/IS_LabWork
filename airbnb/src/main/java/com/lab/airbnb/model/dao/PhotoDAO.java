package com.lab.airbnb.model.dao;

import com.lab.airbnb.model.Photo;
import com.lab.airbnb.model.User;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface PhotoDAO  extends ListCrudRepository<Photo,String> {
    List<Photo> findByHouse_HouseId(String houseId);

}
