package com.gmail.yauhen2012.service.impl;

import java.lang.invoke.MethodHandles;

import com.gmail.yauhen2012.service.model.AppUser;
import com.gmail.yauhen2012.service.model.User;
import com.gmail.yauhen2012.service.util.ReadPropertyFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final ReadPropertyFile readPropertyFile;

    public MyUserDetailsService(ReadPropertyFile readPropertyFile) {this.readPropertyFile = readPropertyFile;}

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        User user = readPropertyFile.getUser(userName);
        if (user == null) {
            throw new UsernameNotFoundException("User - '" + userName + "' is not found");
        }
        logger.debug("User - '" + userName + "' was found");
        return new AppUser(user);
    }

}
