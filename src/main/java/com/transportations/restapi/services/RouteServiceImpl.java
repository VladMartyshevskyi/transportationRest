package com.transportations.restapi.services;

import com.transportations.restapi.models.Route;
import com.transportations.restapi.repositories.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteRepository routeRepository;

    @Override
    public void addRoute(Route route) {
        routeRepository.save(route);
    }

    @Override
    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    @Override
    public Route getRouteById(String id) {
        Optional<Route> routeOptional = routeRepository.findById(id);
        return routeOptional.orElse(null);
    }

    @Override
    public List<Route> getRoutesByFromAndTo(String from, String to) {
        return routeRepository.findByFromAndTo(from, to);
    }

    @Override
    public void updateRoute(String id, Route route) {
        Optional<Route> optionalRoute = routeRepository.findById(id);
        if(optionalRoute.isPresent()){
            Route changingRoute = optionalRoute.get();
            if(route.getFrom() != null) {
                changingRoute.setFrom(route.getFrom());
            }
            if(route.getTo() != null){
                changingRoute.setTo(route.getTo());
            }
            if(route.getPrice() != null)
            {
                changingRoute.setPrice(route.getPrice());
            }
            changingRoute.setDurationInMinutes(route.getDurationInMinutes());
            routeRepository.save(changingRoute);
        }
    }

    @Override
    public void deleteRoute(String id) {
        routeRepository.deleteById(id);
    }
}
