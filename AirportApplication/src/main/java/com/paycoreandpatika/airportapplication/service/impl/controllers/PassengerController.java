package com.paycoreandpatika.airportapplication.service.impl.controllers;

import com.paycoreandpatika.airportapplication.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(name = "/api/passengers")
public class PassengerController {
    private  final PassengerService passengerService;


}
