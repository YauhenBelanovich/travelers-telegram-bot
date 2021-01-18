package com.gmail.yauhen2012;

import java.io.IOException;

import com.gmail.yauhen2012.model.BotCityInfo;

public interface BotCityInformationService {

    String getCityInfo(String city, BotCityInfo botCityInfo, String botId) throws IOException;
}
