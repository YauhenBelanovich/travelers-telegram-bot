package com.gmail.yauhen2012.service.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.Properties;

import com.gmail.yauhen2012.service.model.User;
import com.gmail.yauhen2012.service.model.UserRoleEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class ReadPropertyFile {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public User getUser(String userName) {
        User user = new User();
        try (InputStream inputStream = getClass().getResourceAsStream("/application.properties")) {
            Properties prop = new Properties();
            if (inputStream == null) {
                logger.error("Sorry, unable to find application.properties");
            }
            prop.load(inputStream);
            user.setName(prop.getProperty(userName + ".name"));
            user.setRole(UserRoleEnum.valueOf(prop.getProperty(userName + ".role")));
            user.setPassword(prop.getProperty(userName + ".password"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    public String getBotProperty() {
        try (InputStream inputStream = getClass().getResourceAsStream("/application.properties")) {
            Properties prop = new Properties();
            if (inputStream == null) {
                logger.error("Sorry, unable to find application.properties");
            }
            prop.load(inputStream);
            return prop.getProperty("bot.id");
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.error("Cannot find bot property - " + "bot.id");
        return null;
    }

}
