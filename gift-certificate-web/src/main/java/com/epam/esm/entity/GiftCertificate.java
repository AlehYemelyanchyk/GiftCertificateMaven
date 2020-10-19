package com.epam.esm.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class GiftCertificate implements Serializable {
    private static final long serialVersionUID = 1724820758632935338L;

    private long id;
    private String name;
    private String description;
    private double price;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private int duration;

    public GiftCertificate() {
    }

    public GiftCertificate(String name, String description, double price,
                           LocalDateTime createDate, LocalDateTime lastUpdateDate, int duration) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.duration = duration;
    }

    public GiftCertificate(long id, String name, String description, double price,
                           LocalDateTime createDate, LocalDateTime lastUpdateDate, int duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.duration = duration;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "GiftCertificate{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", createDate=" + createDate +
                ", lastUpdateDate=" + lastUpdateDate +
                ", duration=" + duration +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GiftCertificate that = (GiftCertificate) o;
        return name.equals(that.name) &&
                createDate.equals(that.createDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, createDate);
    }
}
