package com.gmail.yauhen2012.model;

public class BotCityInfo {

    private String cityName;
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
    public String toString() {
        return cityName + "\n" +
                "Information:" + "\n" +
                info;
    }

}
