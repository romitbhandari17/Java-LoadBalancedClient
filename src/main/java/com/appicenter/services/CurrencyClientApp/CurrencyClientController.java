package com.appicenter.services.CurrencyClientApp;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyClientController {

    private final RestTemplate restTemplate;
    private final EurekaClient eurekaClient;

    // Constructor injection
    @Autowired
    public CurrencyClientController(RestTemplate restTemplate, EurekaClient eurekaClient) {
        this.restTemplate = restTemplate;
        this.eurekaClient = eurekaClient;
    }

    @RequestMapping("/")
    public String getCurrencyService(){

        InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("CurrencyService", false);
        String currencyUrl = instanceInfo.getHomePageUrl();
        //restTemplate = getRestTemplate();
        String currencyAppHome = restTemplate.getForObject(currencyUrl, String.class);
        return currencyAppHome;
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
