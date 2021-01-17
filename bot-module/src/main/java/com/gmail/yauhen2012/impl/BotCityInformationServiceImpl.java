package com.gmail.yauhen2012.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import com.gmail.yauhen2012.BotCityInformationService;
import com.gmail.yauhen2012.model.BotCityInfo;
import org.json.JSONObject;

public class BotCityInformationServiceImpl implements BotCityInformationService {

    @Override
    public String getCityInfo(String city, BotCityInfo botCityInfo, String botId) throws IOException {
        URL url = new URL("http://localhost:8081/api/bot-city-info?cityName=" + city + "&appId=" + botId);

        Scanner scanner = new Scanner((InputStream) url.getContent());
        String result = "";

        while (scanner.hasNext()) {
            result += scanner.next() + " ";
        }

        JSONObject jsonObject = new JSONObject(result);
        botCityInfo.setCityName(jsonObject.getString("cityName"));
        botCityInfo.setInfo(jsonObject.getString("info"));

        return botCityInfo.toString();
    }

}
