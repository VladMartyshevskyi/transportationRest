package com.transportations.restapi.controllers;

import com.transportations.restapi.models.Route;
import com.transportations.restapi.repositories.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
public class RouteController {
    @Autowired
    RouteRepository routeRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/routes")
    public Iterable<Route> routes(){
        return routeRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/routes")
    public String save(@RequestBody Route route){
        routeRepository.save(route);
        return route.getId();
    }

    @RequestMapping(method=RequestMethod.GET, value="/routes/{id}")
    public Route showById(@PathVariable String id) {
        return routeRepository.findById(id).get();
    }

    @RequestMapping(method=RequestMethod.GET, value="/routes/{from}/{to}")
    public List<Route> showByFromTo(@PathVariable String from, @PathVariable String to) {
        return routeRepository.findByFromAndTo(from, to);
    }

    @RequestMapping(method=RequestMethod.PUT, value="/routes/{id}")
    public Route update(@PathVariable String id, @RequestBody Route requestRoute) {
        Optional<Route> optionalRoute = routeRepository.findById(id);

        if(optionalRoute.isPresent()) {
            Route route = optionalRoute.get();
            if(requestRoute.getFrom() != null)
                route.setFrom(requestRoute.getFrom());
            if(requestRoute.getTo() != null)
                route.setTo(requestRoute.getTo());
            if(requestRoute.getPrice() != null)
                route.setPrice(requestRoute.getPrice());
            route.setDurationInMinutes(requestRoute.getDurationInMinutes());
            routeRepository.save(route);
            return route;
        }
        return null;
    }

    @RequestMapping(method=RequestMethod.DELETE, value="/routes/{id}")
    public String delete(@PathVariable String id) {
        Route route = routeRepository.findById(id).get();
        routeRepository.delete(route);
        return "route deleted";
    }
}
