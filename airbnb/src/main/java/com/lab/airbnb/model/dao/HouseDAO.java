package com.lab.airbnb.model.dao;

import com.lab.airbnb.model.House;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface HouseDAO extends ListCrudRepository<House, String> {


    List<House> findByUser_UserId(String userId);

    Optional<House> findByHouseId(String houseId);

}
