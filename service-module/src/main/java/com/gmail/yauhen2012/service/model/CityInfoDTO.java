package com.gmail.yauhen2012.service.model;

import java.util.Objects;

public class CityInfoDTO {

    private Long cityInfoId;
    private String cityName;
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
        CityInfoDTO that = (CityInfoDTO) o;
        return Objects.equals(cityInfoId, that.cityInfoId) && Objects.equals(cityName,
                that.cityName) && Objects.equals(info, that.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityInfoId, cityName, info);
    }

}
