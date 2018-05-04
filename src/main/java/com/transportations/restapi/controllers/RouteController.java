package com.transportations.restapi.controllers;

import com.transportations.restapi.models.Route;
import com.transportations.restapi.services.RouteService;
import com.transportations.restapi.utils.EmptyJsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class RouteController {

    @Autowired
    private RouteService routeService;

    @RequestMapping(method = RequestMethod.GET, value = "/routes")
    public ResponseEntity getAllRoutes(){
        List<Route> routes = routeService.getAllRoutes();
        if(routes.isEmpty()){
            return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity(routes, HttpStatus.OK);
        }
    }

    @RequestMapping(method=RequestMethod.GET, value="/routes/{id}")
    public ResponseEntity getRouteById(@PathVariable String id) {
        Route route = routeService.getRouteById(id);
        if(route == null){
            return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity(route, HttpStatus.OK);
        }
    }

    @RequestMapping(method=RequestMethod.GET, value="/routes/{from}/{to}")
    public ResponseEntity getRouteByFromTo(@PathVariable String from, @PathVariable String to) {
        List<Route> routes = routeService.getRoutesByFromAndTo(from, to);
        if(routes.isEmpty()){
            return new ResponseEntity(new EmptyJsonResponse(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(routes, HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/routes")
    public void addRoute(@RequestBody Route route){
        routeService.addRoute(route);
    }

    @RequestMapping(method=RequestMethod.PUT, value="/routes/{id}")
    public ResponseEntity updateRoute(@PathVariable String id, @RequestBody Route requestRoute) {
       routeService.updateRoute(id, requestRoute);
       return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(method=RequestMethod.DELETE, value="/routes/{id}")
    public ResponseEntity deleteRoute(@PathVariable String id) {
        routeService.deleteRoute(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
