package com.gmail.yauhen2012.springbootmodule.controller;

import java.lang.invoke.MethodHandles;
import java.util.Optional;

import com.gmail.yauhen2012.service.CityInfoService;
import com.gmail.yauhen2012.service.model.CityInfoDTO;
import com.gmail.yauhen2012.springbootmodule.exceptionHandlerAPI.RecordNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bot-city-info")
public class BotAPIController {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final CityInfoService cityInfoService;

    public BotAPIController(CityInfoService cityInfoService) {this.cityInfoService = cityInfoService;}

    @GetMapping
    public CityInfoDTO getCityInfo(@RequestParam("cityName") String cityName, @RequestParam("appId") String appId) {
        logger.debug("Get API getCityInfo method");
        Optional<CityInfoDTO> user = Optional.ofNullable(cityInfoService.findCityInfoByName(cityName, appId));
        return user.orElseThrow(() -> new RecordNotFoundException("City name '" + cityName + "' does no exist"));
    }

}
