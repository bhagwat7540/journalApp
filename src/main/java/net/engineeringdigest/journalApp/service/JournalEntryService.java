package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entities.Journal;
import net.engineeringdigest.journalApp.entities.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository repository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(Journal entity, User user) {
        try {
            entity.setDate(LocalDateTime.now());
            repository.save(entity);

            List<Journal> list = user.getJournals();
            list.add(entity);
            userService.saveEntry(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void saveEntry(Journal entity) {
        repository.save(entity);
    }

    public List<Journal> getAll() {
        return repository.findAll();
    }

    public Optional<Journal> findById(ObjectId id) {
        return repository.findById(id);
    }

    @Transactional
    public void deleteById(ObjectId id, User user) {
        List<Journal> list = user.getJournals();
        list.removeIf(x -> x.getId().equals(id));

        userService.saveEntry(user);
        repository.deleteById(id);
    }
}
