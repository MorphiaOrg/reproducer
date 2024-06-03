package com.foo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.antwerkz.bottlerocket.BottleRocket;
import com.antwerkz.bottlerocket.BottleRocketTest;
import com.github.zafarkhaja.semver.Version;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.mapping.MapperOptions;
import dev.morphia.mapping.codec.Conversions;
import dev.morphia.query.UpdateOperations;
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
        datastore = Morphia.createDatastore(mongo, getDatabase().getName(), MapperOptions.legacy().build());
        Conversions.register(MyEnum.class, String.class, MyEnum::getKey);
        Conversions.register(String.class, MyEnum.class, MyEnumConversionUtility::getFromKey);
        datastore.getMapper().map(MyEntity.class);
    }

    private static final class MyEnumConversionUtility {
        public static MyEnum getFromKey(String key) {
            if (key.equals("first")) {
                return MyEnum.FIRST;
            } else if (key.equals("second")) {
                return MyEnum.SECOND;
            } else if (key.equals("third")) {
                return MyEnum.THIRD;
            }
            throw new RuntimeException();
        }
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
        MyEntity entity = new MyEntity();
        entity.setId(ObjectId.get());
        Map<MyEnum, String> values = new HashMap<>();
        values.put(MyEnum.FIRST, "first");
        entity.setValues(values);
        entity.setUpdatedOn(new Date());
        datastore.save(entity);

        System.out.println(datastore.find(MyEntity.class)
            .filter("id", entity.getId())
            .first());

        Map<MyEnum, String> newValues = new HashMap<>();
        newValues.put(MyEnum.SECOND, "second");

        UpdateOperations<MyEntity> updateOps = datastore.createUpdateOperations(MyEntity.class)
            .set("values", newValues)
            .set("updatedOn", new Date());

        datastore.update(datastore.find(MyEntity.class)
            .filter("id", entity.getId()), updateOps);

        System.out.println(datastore.find(MyEntity.class)
            .filter("id", entity.getId())
            .first());
    }
}
