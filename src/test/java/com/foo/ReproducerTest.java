package com.foo;

import java.util.ArrayList;

import com.antwerkz.bottlerocket.BottleRocket;
import com.antwerkz.bottlerocket.BottleRocketTest;
import com.github.zafarkhaja.semver.Version;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.mapping.DiscriminatorFunction;
import dev.morphia.mapping.MapperOptions;
import dev.morphia.query.filters.Filters;

import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.testng.annotations.Test;

public class ReproducerTest extends BottleRocketTest {
    private Datastore datastore;

    public ReproducerTest() {
        MongoClient mongo = getMongoClient();
        MongoDatabase database = getDatabase();
        database.drop();
        MapperOptions options = MapperOptions.builder()
            .discriminator(DiscriminatorFunction.className())
            .discriminatorKey("className")
            .storeEmpties(false)
            .storeNulls(false)
            .build();
        datastore = Morphia.createDatastore(mongo, getDatabase().getName(), options);
        datastore.getMapper().map(MyEntity.class, ListWrapper.class);
    }

    @NotNull
    @Override
    public String databaseName() {
        return "morphia_repro";
    }

    @Nullable
    @Override
    public Version version() {
        return BottleRocket.DEFAULT_VERSION;
    }

    @Test
    public void reproduce() {
        MyEntity entity = new MyEntity(ObjectId.get(), "otherData", new ListWrapper(new ArrayList<>()));
        datastore.save(entity);

        MyEntity foundEntity = datastore.find(MyEntity.class).filter(Filters.eq("id", entity.getId())).first();
        assert foundEntity.getId().equals(entity.getId());
        // I would assume that this wouldn't be persisted, but I don't think this is happening right now.
        System.out.println(foundEntity);
        assert foundEntity.getListWrapper() == null;
    }

}
