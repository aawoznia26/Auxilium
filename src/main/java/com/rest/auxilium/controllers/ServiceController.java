package com.rest.auxilium.controllers;


import com.rest.auxilium.domain.Services;
import com.rest.auxilium.dto.ServicesDto;
import com.rest.auxilium.service.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
public class ServiceController {

    @Autowired
    private ServicesService servicesService;

    @RequestMapping(method = RequestMethod.POST, value = "/services")
    public ServicesDto createService(@RequestBody ServicesDto servicesDto) { return Services.mapToServicesDto(servicesService.addService(ServicesDto.mapToServices(servicesDto))); }

    @RequestMapping(method = RequestMethod.GET, value = "/services")
    public List<ServicesDto> getAllServicesList() { return Services.mapToServicesDtoList(servicesService.getServicesList()); }

    @RequestMapping(method = RequestMethod.GET, value = "/services/city/{cityName}")
    public List<ServicesDto> getServicesByCity(@PathVariable String cityName) { return Services.mapToServicesDtoList(servicesService.getServicesByCity(cityName)); }

    @RequestMapping(method = RequestMethod.GET, value = "/services/type/{type}")
    public List<ServicesDto> getServicesByType(@PathVariable String type) { return Services.mapToServicesDtoList(servicesService.getServicesByName(type)); }

    @RequestMapping(method = RequestMethod.GET, value = "/services/filter/{cityName}/{type}")
    public List<ServicesDto> getServicesByTypeAndCity(@PathVariable String cityName, @PathVariable String type) { return Services.mapToServicesDtoList(servicesService.getServicesByCityAndName(cityName, type)); }


}
