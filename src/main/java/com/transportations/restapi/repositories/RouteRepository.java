package com.transportations.restapi.repositories;

import com.transportations.restapi.models.Route;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface RouteRepository extends MongoRepository<Route, String> {

    List<Route> findByFromAndTo(String from, String to);

}

