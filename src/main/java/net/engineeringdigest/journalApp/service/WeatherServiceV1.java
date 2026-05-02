package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.entities.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherServiceV1 {

    @Value("${weather.api.key}")
    private String api_key;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RestTemplate http;

    public Weather getCurrentWeather(String city) {
        String uri = appCache.getCache().get("weatherApi").replace("<api_key>", api_key).replace("<city>", city);
        ResponseEntity<Weather> response = http.exchange(uri, HttpMethod.GET, null, Weather.class);

        if(response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }

        return null;
    }
}
