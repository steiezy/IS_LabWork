package com.lab.airbnb.controller.property;

import com.lab.airbnb.domain.dto.HouseDTO;
import com.lab.airbnb.exception.HouseNotExistException;
import com.lab.airbnb.model.House;
import com.lab.airbnb.model.User;
import com.lab.airbnb.service.HouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/house")
public class HouseController {

    private HouseService houseService;

    public HouseController(HouseService houseService) {
        this.houseService = houseService;
    }

    @GetMapping("/list")
    public List<House> getAllHouse(@AuthenticationPrincipal User user) {
        if (user == null) {
            return new ArrayList<>();
        }
        String userId = user.getUserId();
        return houseService.findByUserId(userId);
    }
    //rewrite in user
   /* @PostMapping("/add")
    public ResponseEntity addHouse(@AuthenticationPrincipal User user, @RequestBody HouseDTO houseDTO) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Optional<House> opHouse = houseService.save(user, houseDTO);
        if(opHouse.isPresent()) {
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.badRequest().build();
        }
    }*/

    //rewrite in user
    /*@PostMapping("/update")
    public ResponseEntity updateHouse(@AuthenticationPrincipal User user, @RequestBody HouseDTO houseDTO) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<House> ophouse = houseService.findByHouseId(houseDTO.getHouseId());
        if (ophouse.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HouseNotExistException().getMessage());
        }
        House house = ophouse.get();
        if (houseDTO.getTitle() != null) house.setTitle(houseDTO.getTitle());
        if (houseDTO.getDescription() != null) house.setDescription(houseDTO.getDescription());
        if (houseDTO.getLocation() != null) house.setLocation(houseDTO.getLocation());
        if (houseDTO.getStatus() != null) house.setStatus(houseDTO.getStatus());
        if (houseDTO.getLongitude() != null) house.setLongitude(houseDTO.getLongitude());
        if (houseDTO.getLatitude() != null) house.setLatitude(houseDTO.getLatitude());
        if (houseDTO.getProportion() != null) house.setProportion(houseDTO.getProportion());
        if (houseDTO.getCategory() != null) house.setCategory(houseDTO.getCategory());
        if (houseDTO.getPhotos() != null) house.setPhotos(houseDTO.getPhotos());

        Optional<House> opHouse = houseService.saveOrMode(house);
        if(opHouse.isPresent()) {
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.badRequest().build();
        }
    }*/
}
