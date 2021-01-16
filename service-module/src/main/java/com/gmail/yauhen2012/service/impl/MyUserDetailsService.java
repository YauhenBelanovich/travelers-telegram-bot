package com.gmail.yauhen2012.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.Properties;

import com.gmail.yauhen2012.service.model.AppUser;
import com.gmail.yauhen2012.service.model.User;
import com.gmail.yauhen2012.service.model.UserRoleEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        User user = getUser(userName);
        logger.debug("User - '" + userName + "' was found");
        return new AppUser(user);
    }

    private User getUser(String userName) {
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

}
