package com.realestate.statistics.service;

import com.realestate.statistics.model.DataModel;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
class DataFetchingServiceTest {

    @Test
    void getAllHousesData() {
        String dataSourceUrl = "https://demo.interfacema.de/programming-assessment-1.0/buildings";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<DataModel> response = restTemplate.getForEntity(dataSourceUrl + "/1", DataModel.class);
        assertEquals(response.getStatusCode(),(HttpStatus.OK));
    }
}