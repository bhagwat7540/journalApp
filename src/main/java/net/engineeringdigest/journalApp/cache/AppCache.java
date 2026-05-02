package net.engineeringdigest.journalApp.cache;

import lombok.Getter;
import net.engineeringdigest.journalApp.service.AppCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
@Getter
public class AppCache {
    private Map<String, String> cache;

    @Autowired
    private AppCacheService appCacheService;

    @PostConstruct
    private void init() {
        cache = new HashMap<>();
        appCacheService.getAllConfigs().forEach(config -> cache.put(config.getKey(), config.getValue()));
    }
}
