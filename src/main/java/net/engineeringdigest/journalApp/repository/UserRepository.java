package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entities.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    public Optional<User> findByUserName(String userName);

    public void deleteByUserName(String userName);
}
