package com.gmail.yauhen2012;

import java.util.ArrayList;
import java.util.List;

import com.gmail.yauhen2012.repository.CityInformationRepository;
import com.gmail.yauhen2012.repository.model.CityInfo;
import com.gmail.yauhen2012.service.CityInfoService;
import com.gmail.yauhen2012.service.exception.CityExistsException;
import com.gmail.yauhen2012.service.impl.CityInfoServiceImpl;
import com.gmail.yauhen2012.service.model.AddCityInfoDTO;
import com.gmail.yauhen2012.service.model.CityInfoDTO;
import com.gmail.yauhen2012.service.util.ReadPropertyFile;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CityInfoServiceTest {

    @Mock
    private CityInformationRepository cityInformationRepository;
    @Mock
    private ReadPropertyFile readPropertyFile;
    private CityInfoService cityInfoService;

    private static final String TEST_CITY_INFO = "info, info";
    private static final Long TEST_ID = 1L;
    private static final String TEST_CITY_NAME = "minsk";
    private static final String TEST_APP_ID = "12345";

    @BeforeEach
    public void setup() {
        cityInfoService = new CityInfoServiceImpl(cityInformationRepository, readPropertyFile);
    }

    @Test
    public void saveAlreadyExistCityInfo_returnCityInfoExistException() {
        AddCityInfoDTO addCityInfoDTO = setAddCityInfoDTO();
        CityInfo cityInfo = setCityInfo();
        when(cityInformationRepository.findByCityName(TEST_CITY_NAME)).thenReturn(cityInfo);
        assertThatExceptionOfType(CityExistsException.class)
                .isThrownBy(() -> cityInfoService.add(addCityInfoDTO));
        org.junit.jupiter.api.Assertions.assertThrows(
                CityExistsException.class,
                () -> cityInfoService.add(addCityInfoDTO),
                "User with email: " + addCityInfoDTO.getCityName() + " already exists"
        );
    }

    @Test
    public void updateCityInfo_verifyCallMethod() {
        AddCityInfoDTO addCityInfoDTO = setAddCityInfoDTO();
        CityInfo cityInfo = setCityInfo();
        when(cityInformationRepository.findById(TEST_ID)).thenReturn(cityInfo);
        cityInfoService.updateCityInfo(TEST_ID, addCityInfoDTO);

        verify(cityInformationRepository, times(1)).findById(TEST_ID);
    }

    @Test
    public void findCityInfoByID_returnCityInfo() {
        CityInfo cityInfo = setCityInfo();
        when(cityInformationRepository.findById(TEST_ID)).thenReturn(cityInfo);
        CityInfoDTO cityInfoDTO = cityInfoService.findCityInfoById(TEST_ID);

        Assertions.assertThat(cityInfo.getInfo().equals(cityInfoDTO.getInfo()));
        verify(cityInformationRepository, times(1)).findById(anyLong());

        Assertions.assertThat(cityInfoDTO).isNotNull();

    }

    @Test
    public void findAllCityInfo_returnCityInfoList() {

        List<CityInfo> cityInfoList = new ArrayList<>();
        for (long i = 0; i < 5; i++) {
            CityInfo cityInfo = setCityInfo();
            cityInfo.setCityInfoId(i);
            cityInfoList.add(cityInfo);
        }
        when(cityInformationRepository.findAll()).thenReturn(cityInfoList);
        List<CityInfoDTO> cityInfoDTOList = cityInfoService.findAll();

        Assertions.assertThat(cityInfoList.size()).isEqualTo(cityInfoDTOList.size());
        verify(cityInformationRepository, times(1)).findAll();
        Assertions.assertThat(cityInfoDTOList).isNotEmpty();
    }

    @Test
    public void deleteCityInfo_verifyCallMethod() {

        CityInfo cityInfo = setCityInfo();
        when(cityInformationRepository.findById(TEST_ID)).thenReturn(cityInfo);
        cityInfoService.deleteCityInfoById(TEST_ID);
        verify(cityInformationRepository, times(1)).remove(any());
    }

    @Test
    public void saveCityInfo_returnCallMethod() throws CityExistsException {
        AddCityInfoDTO addCityInfoDTO = setAddCityInfoDTO();
        cityInfoService.add(addCityInfoDTO);
        verify(cityInformationRepository, times(1)).add(any());
    }

    @Test
    public void updateCityInfo_verifyChanging() {
        CityInfo cityInfo = setCityInfo();
        CityInfo cityInfoFromDB = setCityInfo();
        AddCityInfoDTO addCityInfoDTO = setAddCityInfoDTO();
        addCityInfoDTO.setInfo("another info");
        when(cityInformationRepository.findById(TEST_ID)).thenReturn(cityInfoFromDB);
        cityInfoService.updateCityInfo(TEST_ID, addCityInfoDTO);
        verify(cityInformationRepository, times(1)).merge(cityInfoFromDB);

        Assertions.assertThat(cityInfo.getInfo()).isNotEqualTo(cityInfoFromDB.getInfo());
    }

    @Test
    public void findCityInfoByName_returnCityInfo() {
        CityInfo cityInfo = setCityInfo();
        when(cityInformationRepository.findByCityName(TEST_CITY_NAME)).thenReturn(cityInfo);
        when(readPropertyFile.getBotProperty()).thenReturn(TEST_APP_ID);
        CityInfoDTO cityInfoDTO = cityInfoService.findCityInfoByName(TEST_CITY_NAME, TEST_APP_ID);

        Assertions.assertThat(cityInfo.getInfo().equals(cityInfoDTO.getInfo()));
        verify(cityInformationRepository, times(1)).findByCityName(anyString());

        Assertions.assertThat(cityInfoDTO).isNotNull();

    }


    private CityInfo setCityInfo() {
        CityInfo cityInfo = new CityInfo();
        cityInfo.setCityInfoId(TEST_ID);
        cityInfo.setCityName(TEST_CITY_NAME);
        cityInfo.setInfo(TEST_CITY_INFO);
        return cityInfo;
    }
    private AddCityInfoDTO setAddCityInfoDTO() {
        AddCityInfoDTO addCityInfoDTO = new AddCityInfoDTO();
        addCityInfoDTO.setCityName(TEST_CITY_NAME);
        addCityInfoDTO.setInfo(TEST_CITY_INFO);
        return addCityInfoDTO;
    }

}
