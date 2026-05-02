package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entities.Config;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AppCacheRepository extends MongoRepository<Config, ObjectId> {
}
