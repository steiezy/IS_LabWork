package com.lab.airbnb.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_order")
public class Order {
    @Id
    @Column(name = "order_id", nullable = false, length = 32)
    private String orderId;
    //0:未出租 1:已出租 2:已下架
    @Column(name = "status", length = 10)
    private String status;
    @Column(name = "buyer", length = 32)
    private String buyer;

    //pricePerLeastTime
    @Column(name = "price")
    private Double price;

    @ManyToOne(optional = false)
    @JoinColumn(name = "house_id", nullable = false)
    private House house;

    @Column(name = "release_time")
    private LocalDateTime releaseTime;

    @Column(name = "least_time", length = 10)
    private String leastTime;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLeastTime() {
        return leastTime;
    }

    public void setLeastTime(String leastTime) {
        this.leastTime = leastTime;
    }

    public LocalDateTime getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(LocalDateTime releaseTime) {
        this.releaseTime = releaseTime;
    }

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}