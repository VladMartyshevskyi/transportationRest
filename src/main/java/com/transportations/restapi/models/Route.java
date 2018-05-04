package com.transportations.restapi.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "routes")
public class Route {

    @Id
    private String id;
    private String from;
    private String to;
    private Double price;
    private int durationInMinutes;

    public Route() {
    }

    public Route(String id, String from, String to, Double price, int durationInMinutes) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.price = price;
        this.durationInMinutes = durationInMinutes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return durationInMinutes == route.durationInMinutes &&
                Objects.equals(id, route.id) &&
                Objects.equals(from, route.from) &&
                Objects.equals(to, route.to) &&
                Objects.equals(price, route.price);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, from, to, price, durationInMinutes);
    }
}
