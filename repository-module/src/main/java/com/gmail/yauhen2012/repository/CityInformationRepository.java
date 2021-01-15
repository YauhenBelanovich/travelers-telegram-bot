package com.gmail.yauhen2012.repository;

import com.gmail.yauhen2012.repository.model.CityInfo;

public interface CityInformationRepository extends GenericDAO<Long, CityInfo> {

    CityInfo findByCityName(String cityName);

}
