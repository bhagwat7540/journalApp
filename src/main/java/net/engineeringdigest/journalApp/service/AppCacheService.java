package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entities.Config;
import net.engineeringdigest.journalApp.repository.AppCacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppCacheService {

    @Autowired
    private AppCacheRepository repository;

    public List<Config> getAllConfigs() {
        return repository.findAll();
    }
}
