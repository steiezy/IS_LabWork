package com.lab.airbnb.service;

import com.lab.airbnb.domain.dto.HouseDTO;
import com.lab.airbnb.model.House;
import com.lab.airbnb.model.User;
import com.lab.airbnb.model.dao.HouseDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HouseService {

    private HouseDAO houseDAO;

    public HouseService(HouseDAO houseDAO) {
        this.houseDAO = houseDAO;
    }

    public List<House> findByUserId(String userId) {
        return houseDAO.findByUser_UserId(userId);
    }

    public Optional<House> saveOrMode(House house) {
        return Optional.ofNullable(houseDAO.save(house));
    }

    public Optional<House> save(User user, HouseDTO houseDTO) {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "");

        House house = new House();
        house.setHouseId(uuid);
        house.setTitle(houseDTO.getTitle());
        house.setDescription(houseDTO.getDescription());
        house.setLocation(houseDTO.getLocation());
        house.setOwner(user.getUserId());
        house.setStatus(houseDTO.getStatus());
        house.setLongitude(houseDTO.getLongitude());
        house.setLatitude(houseDTO.getLatitude());
        house.setProportion(houseDTO.getProportion());
        house.setCategory(houseDTO.getCategory());
        house.setPhotos(houseDTO.getPhotos());
        house.setUser(user);

        return Optional.ofNullable(houseDAO.save(house));
    }

    public Optional<House> findByHouseId(String houseId) {
        return houseDAO.findByHouseId(houseId);
    }
}
