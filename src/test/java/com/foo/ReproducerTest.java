package com.foo;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import org.bson.UuidRepresentation;
import org.jetbrains.annotations.NotNull;
import org.testcontainers.containers.MongoDBContainer;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.mongodb.MongoClientSettings.builder;

public class ReproducerTest {

    private MongoDBContainer mongoDBContainer;
    private String connectionString;

    private Datastore datastore;

    @Test
    public void reproduce() {
    }

    @NotNull
    public String databaseName() {
        return "morphia_repro";
    }

    @NotNull
    public String dockerImageName() {
        return "mongo:7";
    }

    @BeforeClass
    private void setup() {
        mongoDBContainer = new MongoDBContainer(dockerImageName());
        mongoDBContainer.start();
        connectionString = mongoDBContainer.getReplicaSetUrl(databaseName());

        MongoClient mongoClient = MongoClients.create(builder()
                                                  .uuidRepresentation(UuidRepresentation.STANDARD)
                                                  .applyConnectionString(new ConnectionString(connectionString))
                                                  .build());

        datastore = Morphia.createDatastore(mongoClient, databaseName());
    }
}
