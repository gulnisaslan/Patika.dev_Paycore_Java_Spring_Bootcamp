package com.paycoreandpatika.airportapplication.controllers;

import com.paycoreandpatika.airportapplication.service.AirportService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AirportController {


    private final AirportService airportService;

 
    

}
