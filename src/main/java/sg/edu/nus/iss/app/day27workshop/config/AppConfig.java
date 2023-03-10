package sg.edu.nus.iss.app.day27workshop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import static sg.edu.nus.iss.app.day27workshop.Constants.*;


@Configuration
public class AppConfig {
    
    // Inject the mongo connection string
    @Value("${MONGO_URL}")
    private String connectionString;

    private MongoClient client = null;

    @Bean
    public MongoClient mongoClient() {
        if (null == client)
            client = MongoClients.create(connectionString);
        return client;
    }

    // Create and initialize MongoTemplate
    @Bean
    public MongoTemplate createMongoTemplate() {
        return new MongoTemplate(mongoClient(), BOARDGAMES);        
    }

}
