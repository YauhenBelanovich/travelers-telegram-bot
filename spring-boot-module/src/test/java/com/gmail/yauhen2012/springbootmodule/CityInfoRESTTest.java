package com.gmail.yauhen2012.springbootmodule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.yauhen2012.service.model.AddCityInfoDTO;
import com.gmail.yauhen2012.springbootmodule.config.TestAPIConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import static com.gmail.yauhen2012.springbootmodule.constant.TestAdminConstant.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(value = "/application-integration.properties")
@Import(TestAPIConfig.class)
@ActiveProfiles("test")
public class CityInfoRESTTest {

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getCityInfo_return401() {

        ResponseEntity<String> entity = template.withBasicAuth(TEST_SOMEONE, TEST_PASSWORD)
                .getForEntity("/api/city-info", String.class);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, entity.getStatusCode());
    }

    @Test
    public void getAllCityInfo_return200() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> httpEntity = new HttpEntity<>("", httpHeaders);
        ResponseEntity<String> entity = template.withBasicAuth(TEST_ADMIN, TEST_PASSWORD)
                .exchange("/api/city-info", HttpMethod.GET, httpEntity, String.class);
        Assertions.assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

    @Test
    public void getCityInfo_return200() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> httpEntity = new HttpEntity<>("", httpHeaders);
        ResponseEntity<String> entity = template.withBasicAuth(TEST_ADMIN, TEST_PASSWORD)
                .exchange("/api/city-info/2", HttpMethod.GET, httpEntity, String.class);
        Assertions.assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

    @Test
    public void postCityInfo_return201() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        AddCityInfoDTO addCityInfoDTO = new AddCityInfoDTO();
        addCityInfoDTO.setCityName("test");
        addCityInfoDTO.setInfo("test");
        String content = objectMapper.writeValueAsString(addCityInfoDTO);

        HttpEntity<String> httpEntity = new HttpEntity<>(content, httpHeaders);
        ResponseEntity<String> entity = template.withBasicAuth(TEST_ADMIN, TEST_PASSWORD)
                .exchange("/api/city-info", HttpMethod.POST, httpEntity, String.class);
        Assertions.assertEquals(HttpStatus.CREATED, entity.getStatusCode());
    }

    @Test
    public void deleteCityInfo_return200() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> httpEntity = new HttpEntity<>("", httpHeaders);
        ResponseEntity<String> entity = template.withBasicAuth(TEST_ADMIN, TEST_PASSWORD)
                .exchange("/api/city-info/1", HttpMethod.DELETE, httpEntity, String.class);
        Assertions.assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

    @Test
    public void deleteCityInfo_return401() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> httpEntity = new HttpEntity<>("", httpHeaders);
        ResponseEntity<String> entity = template.withBasicAuth(TEST_SOMEONE, TEST_PASSWORD)
                .exchange("/api/city-info/3", HttpMethod.DELETE, httpEntity, String.class);
        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, entity.getStatusCode());
    }

    @Test
    public void updateCityInfo_return200() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        AddCityInfoDTO addCityInfoDTO = new AddCityInfoDTO();
        addCityInfoDTO.setCityName("test3");
        addCityInfoDTO.setInfo("test3");
        String content = objectMapper.writeValueAsString(addCityInfoDTO);

        HttpEntity<String> httpEntity = new HttpEntity<>(content, httpHeaders);
        ResponseEntity<String> entity = template.withBasicAuth(TEST_ADMIN, TEST_PASSWORD)
                .exchange("/api/city-info/3", HttpMethod.PUT, httpEntity, String.class);
        Assertions.assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

}
