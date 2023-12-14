package com.lab.airbnb.model.dao;

import com.lab.airbnb.model.House;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface HouseDAO extends ListCrudRepository<House, String> {


    List<House> findByUser_UserId(String userId);

    House findByHouseId(String houseId);

}
