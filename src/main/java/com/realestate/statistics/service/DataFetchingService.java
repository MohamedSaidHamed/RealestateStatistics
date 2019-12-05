package com.realestate.statistics.service;

import com.realestate.statistics.model.DataModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DataFetchingService {
    /**
     * @return
     * @throws Exception
     */
    public DataModel dataFetching() throws Exception{
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<DataModel> response
                = restTemplate.getForEntity("https://demo.interfacema.de/programming-assessment-1.0/buildings" + "/1", DataModel.class);
        return response.getBody();
    }
}
