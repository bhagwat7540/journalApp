package net.engineeringdigest.journalApp.entities;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "journal_app_config")
@Data
public class Config {
    private String key;
    private String value;
}
