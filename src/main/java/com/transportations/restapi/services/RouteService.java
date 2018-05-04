package com.transportations.restapi.services;

import com.transportations.restapi.models.Route;
import java.util.List;

public interface RouteService {

    List<Route> getAllRoutes();
    Route getRouteById(String id);
    List<Route> getRoutesByFromAndTo(String from, String to);
    void addRoute(Route route);
    void updateRoute(String id, Route route);
    void deleteRoute(String id);
}
