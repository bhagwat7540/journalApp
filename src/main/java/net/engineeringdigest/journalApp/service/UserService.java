package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entities.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository repository;

    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public void saveEntry(User entity) {
        repository.save(entity);
    }

    public void saveUser(User entity) {
        entity.setPassword(encoder.encode(entity.getPassword()));
        entity.setRoles(Collections.singletonList("USER"));
        repository.save(entity);
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public Optional<User> findById(ObjectId id) {
        return repository.findById(id);
    }

    public Optional<User> findByUserName(String userName) {
        return repository.findByUserName(userName);
    }

    public void deleteById(ObjectId id) {
        repository.deleteById(id);
    }

    public void deleteByUsername(String userName) {
        repository.deleteByUserName(userName);
    }

    public void createAdminUser(User entity) {
        entity.setPassword(encoder.encode(entity.getPassword()));
        entity.setRoles(Collections.singletonList("ADMIN"));
        repository.save(entity);
    }
}
