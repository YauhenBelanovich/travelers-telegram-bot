package com.gmail.yauhen2012.service.model;

import java.util.Objects;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AddCityInfoDTO {

    @NotEmpty(message = "City name is required. Cannot be empty")
    @Size(max = 50)
    private String cityName;

    @NotNull(message = "City info cannot be null")
    @Size(max = 200)
    private String info;

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
        AddCityInfoDTO that = (AddCityInfoDTO) o;
        return Objects.equals(cityName, that.cityName) && Objects.equals(info, that.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityName, info);
    }

}
