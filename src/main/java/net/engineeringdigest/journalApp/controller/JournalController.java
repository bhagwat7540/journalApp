package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entities.Journal;
import net.engineeringdigest.journalApp.entities.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Journal>> getAll() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userService.findByUserName(userName);
        if (!user.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        List<Journal> list = user.get().getJournals();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<Journal> getById(@PathVariable ObjectId myId) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userService.findByUserName(userName);
        if (!user.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        List<Journal> list = user.get().getJournals().stream().filter(journal -> journal.getId().equals(myId)).collect(Collectors.toList());
        if (list.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(list.get(0), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Journal> createEntry(@RequestBody Journal entity) {
        try {
            String userName = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<User> user = userService.findByUserName(userName);
            if (!user.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            journalEntryService.saveEntry(entity, user.get());
            return new ResponseEntity<>(entity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<Journal> deleteEntity(@PathVariable ObjectId myId) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userService.findByUserName(userName);
        if (!user.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        journalEntryService.deleteById(myId, user.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<Journal> updateEntity(@PathVariable ObjectId id, @RequestBody Journal entity) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userService.findByUserName(userName);
        if (!user.isPresent()) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        List<Journal> list = user.get().getJournals().stream().filter(journal -> journal.getId().equals(id)).collect(Collectors.toList());
        if (list.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        Journal oldEntity = list.get(0);
        oldEntity.setDate(LocalDateTime.now());
        oldEntity.setTitle(entity.getTitle() != null && !entity.getTitle().equals("") ? entity.getTitle() : oldEntity.getTitle());
        oldEntity.setContent(entity.getContent() != null && !entity.getContent().equals("") ? entity.getContent() : oldEntity.getContent());
        journalEntryService.saveEntry(oldEntity);
        return new ResponseEntity<>(oldEntity, HttpStatus.OK);
    }
}
