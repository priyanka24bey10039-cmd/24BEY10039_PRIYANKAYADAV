package com.library.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

/**
 * MongoDB Configuration
 *
 * <p>Configures MongoDB connection, template, and removes the default
 * {@code _class} field from stored documents for cleaner JSON output.</p>
 */
@Slf4j
@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.host:localhost}")
    private String host;

    @Value("${spring.data.mongodb.port:27017}")
    private int port;

    @Value("${spring.data.mongodb.database:library_db}")
    private String database;

    @Override
    protected String getDatabaseName() {
        return database;
    }

    @Override
    public MongoClient mongoClient() {
        String connectionString = String.format("mongodb://%s:%d/%s", host, port, database);
        log.info("[MongoDB] Connecting to: {}", connectionString);
        ConnectionString cs = new ConnectionString(connectionString);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(cs)
                .build();
        return MongoClients.create(settings);
    }

    /**
     * Removes the '_class' field that Spring Data MongoDB adds by default.
     * This keeps the stored documents clean and free from Java class references.
     */
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        MongoTemplate template = new MongoTemplate(mongoClient(), getDatabaseName());
        MappingMongoConverter converter = (MappingMongoConverter) template.getConverter();
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        log.info("[MongoDB] MongoTemplate configured — '_class' field suppressed.");
        return template;
    }
}
