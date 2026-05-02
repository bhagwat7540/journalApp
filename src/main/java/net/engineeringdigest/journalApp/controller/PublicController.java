package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entities.User;
import net.engineeringdigest.journalApp.entities.Weather;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.service.WeatherServiceV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @Autowired
    private WeatherServiceV1 weatherService;

    @GetMapping("/greet")
    public ResponseEntity<String> healthCheck() {
        Weather weather = weatherService.getCurrentWeather("mumbai");
        if (weather == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>("Hello World, weather currently is : " + weather.current.temp_c + "'c", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User requestBody) {
        try {
            userService.saveUser(requestBody);
            return new ResponseEntity<>(requestBody, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
