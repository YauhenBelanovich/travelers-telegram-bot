package com.gmail.yauhen2012.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import com.gmail.yauhen2012.repository.model.CityInfo;
import com.gmail.yauhen2012.service.CityInfoService;
import com.gmail.yauhen2012.repository.CityInformationRepository;
import com.gmail.yauhen2012.service.exception.CityExistsException;
import com.gmail.yauhen2012.service.model.AddCityInfoDTO;
import com.gmail.yauhen2012.service.model.CityInfoDTO;
import org.springframework.stereotype.Service;

@Service
public class CityInfoServiceImpl implements CityInfoService {

    private final CityInformationRepository cityInformationRepository;

    public CityInfoServiceImpl(CityInformationRepository cityInformationRepository) {this.cityInformationRepository = cityInformationRepository;}

    @Override
    @Transactional
    public Boolean add(AddCityInfoDTO addCityInfoDTO) throws CityExistsException {
        if (cityExists(addCityInfoDTO.getCityName())) {
            throw new CityExistsException("City with name: " + addCityInfoDTO.getCityName() + " already exists");
        }
        CityInfo cityInfo = convertAddCityInfoDTOToDatabaseCityInfo(addCityInfoDTO);
        cityInformationRepository.add(cityInfo);
        return cityInformationRepository.findByCityName(addCityInfoDTO.getCityName()) != null;
    }

    @Override
    @Transactional
    public CityInfoDTO findCityInfoById(Long id) {
        CityInfo cityInfo = cityInformationRepository.findById(id);
        if (cityInfo != null) {
            return convertDatabaseObjectToDTO(cityInfo);
        }
        return null;
    }

    @Override
    @Transactional
    public Boolean deleteCityInfoById(Long id) {
        CityInfo cityInfo = cityInformationRepository.findById(id);
        if (cityInfo != null) {
            cityInformationRepository.remove(cityInfo);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public List<CityInfoDTO> findAll() {
        List<CityInfo> cityInfoDTOList = cityInformationRepository.findAll();
        return cityInfoDTOList.stream()
                .map(this::convertDatabaseObjectToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Boolean updateCityInfo(Long id, AddCityInfoDTO addCityInfoDTO) {

        CityInfo cityInfo = cityInformationRepository.findById(id);
        if (cityInfo != null) {
            if (addCityInfoDTO.getCityName() != null && !addCityInfoDTO.getCityName().equals("")) {
                cityInfo.setCityName(addCityInfoDTO.getCityName());
            }
            if (addCityInfoDTO.getInfo() != null && !addCityInfoDTO.getInfo().equals("")) {
                cityInfo.setInfo(addCityInfoDTO.getInfo());
            }
            cityInformationRepository.merge(cityInfo);
            return true;
        }
        return false;
    }

    private boolean cityExists(String cityName) {
        CityInfo cityInfo = cityInformationRepository.findByCityName(cityName);
        return cityInfo != null;
    }

    private CityInfo convertAddCityInfoDTOToDatabaseCityInfo(AddCityInfoDTO addCityInfoDTO) {
        CityInfo cityInfo = new CityInfo();
        cityInfo.setCityName(addCityInfoDTO.getCityName());
        cityInfo.setInfo(addCityInfoDTO.getInfo());
        return cityInfo;
    }

    private CityInfoDTO convertDatabaseObjectToDTO(CityInfo cityInfo) {
        CityInfoDTO cityInfoDTO = new CityInfoDTO();
        cityInfoDTO.setCityInfoId(cityInfo.getCityInfoId());
        cityInfoDTO.setCityName(cityInfo.getCityName());
        cityInfoDTO.setInfo(cityInfo.getInfo());
        return cityInfoDTO;
    }

}
