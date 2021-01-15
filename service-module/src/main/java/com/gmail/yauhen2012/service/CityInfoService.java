package com.gmail.yauhen2012.service;

import java.util.List;

import com.gmail.yauhen2012.service.exception.CityExistsException;
import com.gmail.yauhen2012.service.model.AddCityInfoDTO;
import com.gmail.yauhen2012.service.model.CityInfoDTO;

public interface CityInfoService {

    Boolean add(AddCityInfoDTO addCityInfoDTO) throws CityExistsException;

    CityInfoDTO findCityInfoById(Long id);

    Boolean deleteCityInfoById(Long id);

    List<CityInfoDTO> findAll();

    Boolean updateCityInfo(Long id, AddCityInfoDTO addCityInfoDTO);

}
