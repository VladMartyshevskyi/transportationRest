package com.transportations.restapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transportations.restapi.models.Route;
import com.transportations.restapi.services.RouteService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class RouteControllerTest {

    private MockMvc mockMvc;
    @Mock
    private RouteService routeService;

    @InjectMocks
    private RouteController controller;

    @Before
    public void init(){
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    public void getAllRoutes() throws Exception {
        Route first = new Route("1", "testKhm", "testKiev", 150.0, 100);
        Route second = new Route("2", "testKiev", "testKhm", 150.0, 100);
        when(routeService.getAllRoutes()).thenReturn(Arrays.asList(first, second));
        mockMvc.perform(get("/routes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].from", is("testKhm")))
                .andExpect(jsonPath("$[0].to", is("testKiev")))
                .andExpect(jsonPath("$[0].price", is(150.0)))
                .andExpect(jsonPath("$[0].durationInMinutes", is(100)))
                .andExpect(jsonPath("$[1].id", is("2")))
                .andExpect(jsonPath("$[1].from", is("testKiev")))
                .andExpect(jsonPath("$[1].to", is("testKhm")))
                .andExpect(jsonPath("$[1].price", is(150.0)))
                .andExpect(jsonPath("$[1].durationInMinutes", is(100)));
        verify(routeService, times(1)).getAllRoutes();
        verifyNoMoreInteractions(routeService);
    }

    @Test
    public void getRouteById() throws Exception {
        Route route = new Route("412", "testKhm", "testKiev", 150.0, 100);
        when(routeService.getRouteById("412")).thenReturn(route);
        mockMvc.perform(get("/routes/{id}", "412"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is("412")))
                .andExpect(jsonPath("$.from", is("testKhm")))
                .andExpect(jsonPath("$.to", is("testKiev")))
                .andExpect(jsonPath("$.price", is(150.0)))
                .andExpect(jsonPath("$.durationInMinutes", is(100)));
        verify(routeService, times(1)).getRouteById("412");
        verifyNoMoreInteractions(routeService);
    }

    @Test
    public void getRouteByIdToFail() throws Exception {
        when(routeService.getRouteById("412")).thenReturn(null);
        mockMvc.perform(get("/routes/{id}", "412"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string("{}"));
        verify(routeService, times(1)).getRouteById("412");
        verifyNoMoreInteractions(routeService);
    }

    @Test
    public void getRouteByFromTo() throws Exception {
        String from = "testKhm";
        String to = "testKiev";
        Route first = new Route("1", from, to, 150.0, 100);
        Route second = new Route("2", from, to, 120.0, 90);
        when(routeService.getRoutesByFromAndTo(from, to)).thenReturn(Arrays.asList(first, second));
        mockMvc.perform(get("/routes/{from}/{to}", from, to))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].from", is(from)))
                .andExpect(jsonPath("$[0].to", is(to)))
                .andExpect(jsonPath("$[0].price", is(150.0)))
                .andExpect(jsonPath("$[0].durationInMinutes", is(100)))
                .andExpect(jsonPath("$[1].id", is("2")))
                .andExpect(jsonPath("$[1].from", is(from)))
                .andExpect(jsonPath("$[1].to", is(to)))
                .andExpect(jsonPath("$[1].price", is(120.0)))
                .andExpect(jsonPath("$[1].durationInMinutes", is(90)));
        verify(routeService, times(1)).getRoutesByFromAndTo(from, to);
        verifyNoMoreInteractions(routeService);
    }

    @Test
    public void addRoute() throws Exception {
        Route route = new Route("1", "testKhm", "testKiev", 150.0, 100);
        doNothing().when(routeService).addRoute(route);
        mockMvc.perform(post("/routes/")
                .content(asJsonString(route))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(routeService, times(1)).addRoute(eq(route));
        verifyNoMoreInteractions(routeService);
    }

    @Test
    public void updateRoute() throws Exception {
        Route route = new Route("1", "testKhm", "testKiev", 150.0, 100);
        doNothing().when(routeService).updateRoute("1", route);
        mockMvc.perform(put("/routes/{id}", "1")
                .content(asJsonString(route))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(routeService, times(1)).updateRoute(eq("1"), eq(route)); // To change
        verifyNoMoreInteractions(routeService);
    }

    @Test
    public void deleteRoute() throws Exception {
        doNothing().when(routeService).deleteRoute("1");
        mockMvc.perform(delete("/routes/{id}", "1"))
                .andExpect(status().isOk());
        verify(routeService, times(1)).deleteRoute(eq("1")); // To change
        verifyNoMoreInteractions(routeService);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}