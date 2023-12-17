package com.lab.airbnb.service;

import com.lab.airbnb.model.House;
import com.lab.airbnb.model.Photo;
import com.lab.airbnb.model.dao.HouseDAO;
import com.lab.airbnb.model.dao.PhotoDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhotoService {

    private final PhotoDAO photoDAO;

    private final HouseDAO houseDAO;


    public PhotoService(PhotoDAO photoDAO, HouseDAO houseDAO) {
        this.photoDAO = photoDAO;
        this.houseDAO = houseDAO;

    }

    public void save(String houseId,String path) {

        Photo photo = new Photo();
        photo.setFileUrl(path);
        Optional<House> opHouse=houseDAO.findByHouseId(houseId);
        if (opHouse.isPresent()) {
            House house = opHouse.get();
            photo.setHouse(house);
            photoDAO.save(photo);
        }

    }

    public List<Photo> getAllPhoto(String houseId) {
        return photoDAO.findByHouse_HouseId(houseId);
    }
}
