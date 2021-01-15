package com.gmail.yauhen2012;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class CityInformation {

    public static String getCityInfo(String city, Model model) throws IOException {

        URL url = new URL("http://localhost:8081/api/bot-city-info/" + city);

        Scanner scanner = new Scanner((InputStream) url.getContent());
        String result = "";

        while (scanner.hasNext()) {
            result += scanner.next() + " ";
        }

        JSONObject jsonObject = new JSONObject(result);
        model.setCityName(jsonObject.getString("cityName"));
        model.setInfo(jsonObject.getString("info"));

        return model.toString();
    }
}
