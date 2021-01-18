package com.gmail.yauhen2012.repository.model;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "city_info")
public class CityInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_info_id")
    private Long cityInfoId;
    @Column(name = "city_name")
    private String cityName;
    @Column
    private String info;

    public Long getCityInfoId() {
        return cityInfoId;
    }

    public void setCityInfoId(Long cityInfoId) {
        this.cityInfoId = cityInfoId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CityInfo cityInfo = (CityInfo) o;
        return Objects.equals(cityInfoId, cityInfo.cityInfoId) && Objects.equals(cityName,
                cityInfo.cityName) && Objects.equals(info, cityInfo.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityInfoId, cityName, info);
    }

}
