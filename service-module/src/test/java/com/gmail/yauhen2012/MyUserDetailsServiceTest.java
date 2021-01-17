package com.gmail.yauhen2012;

import com.gmail.yauhen2012.service.impl.MyUserDetailsService;
import com.gmail.yauhen2012.service.model.AppUser;
import com.gmail.yauhen2012.service.model.User;
import com.gmail.yauhen2012.service.model.UserRoleEnum;
import com.gmail.yauhen2012.service.util.ReadPropertyFile;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MyUserDetailsServiceTest {

    @Mock
    private ReadPropertyFile readPropertyFile;
    private MyUserDetailsService myUserDetailsService;

    private static final String TEST_USER_NAME = "name";
    private static final String TEST_PASS = "test";
    private static final Long TEST_ID = 1L;

    @BeforeEach
    public void setup() {
        myUserDetailsService = new MyUserDetailsService(readPropertyFile);
    }

    @Test
    public void tryToFindNotExistUser_returnUsernameNotFoundException() {
        User user = null;

        when(readPropertyFile.getUser(TEST_USER_NAME)).thenReturn(user);
        Assertions.assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> myUserDetailsService.loadUserByUsername(TEST_USER_NAME));
        org.junit.jupiter.api.Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> myUserDetailsService.loadUserByUsername(TEST_USER_NAME),
                "User - '" + TEST_USER_NAME + "' is not found"
        );
    }

    @Test
    public void returnAppUserByName() {
        User user = setUserFromPropertyFile();
        when(readPropertyFile.getUser(TEST_USER_NAME)).thenReturn(user);
        AppUser appUser = (AppUser) myUserDetailsService.loadUserByUsername(TEST_USER_NAME);

        Assertions.assertThat(appUser).isNotNull();
        Assertions.assertThat(appUser.getUsername().equals(user.getName()));

        verify(readPropertyFile, times(1)).getUser(TEST_USER_NAME);
    }

    private User setUserFromPropertyFile() {
        User user = new User();
        user.setName(TEST_USER_NAME);
        user.setPassword(TEST_PASS);
        user.setRole(UserRoleEnum.ADMINISTRATOR);

        return user;
    }

}
