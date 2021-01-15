package com.gmail.yauhen2012.springbootmodule.controller;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.gmail.yauhen2012.service.CityInfoService;
import com.gmail.yauhen2012.service.exception.CityExistsException;
import com.gmail.yauhen2012.service.model.AddCityInfoDTO;
import com.gmail.yauhen2012.service.model.CityInfoDTO;
import com.gmail.yauhen2012.springbootmodule.exceptionHandlerAPI.RecordNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/city-info")
public class CityInfoAPIController {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final CityInfoService cityInfoService;

    public CityInfoAPIController(CityInfoService cityInfoService) {this.cityInfoService = cityInfoService;}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String saveCityInfo(@RequestBody @Valid AddCityInfoDTO addCityInfoDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return getValidationMessage(bindingResult);
        } else {
            try {
                logger.debug("POST API save CityInfo method");
                cityInfoService.add(addCityInfoDTO);
                return "Added Successfully";

            } catch (CityExistsException e) {
                logger.error(e.getMessage());
                return "City exist already";
            }
        }
    }

    @PutMapping("/{id}")
    public String updateCityInfo(@RequestBody @Valid AddCityInfoDTO addCityInfoDTO, BindingResult bindingResult, @PathVariable Long id) {
        if (bindingResult.hasErrors()) {
            return getValidationMessage(bindingResult);
        } else {
            logger.debug("PUT API update CityInfo method");
            if (cityInfoService.updateCityInfo(id, addCityInfoDTO)) {
                return "Updated Successfully";
            } else {
                return "City id '" + id + "' does no exist";
            }
        }
    }

    @GetMapping("/{id}")
    public CityInfoDTO getCityInfo(@PathVariable Long id) {
        logger.debug("Get API getCityInfo method");
        Optional<CityInfoDTO> user = Optional.ofNullable(cityInfoService.findCityInfoById(id));
        return user.orElseThrow(() -> new RecordNotFoundException("City id '" + id + "' does no exist"));
    }

    @DeleteMapping("/{id}")
    public String deleteCityInfo(@PathVariable Long id) {
        if (cityInfoService.deleteCityInfoById(id)) {
            logger.debug("DELETE API deleteCityInfo method");
            return "Deleted Successfully";
        }
        return "CityInfo " + id + " does not exist already";
    }

    @GetMapping
    public List<CityInfoDTO> getAllCityInfo() {
        logger.debug("Get API getAllCityInfo method");
        return cityInfoService.findAll();
    }

    private String getValidationMessage(BindingResult bindingResult) {
        List<FieldError> errors = bindingResult.getFieldErrors();
        List<String> message = new ArrayList<>();
        for (FieldError e : errors) {
            message.add("@" + e.getField().toUpperCase() + ":" + e.getDefaultMessage());
        }
        return message.toString();
    }

}
