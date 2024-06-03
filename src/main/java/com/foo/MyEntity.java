package com.foo;

import java.util.Date;
import java.util.Map;

import org.bson.types.ObjectId;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

@Entity("MyEntityCollection")
public class MyEntity {
  @Id
  private ObjectId id;
  private Map<ObjectId, String> values;
  private Date updatedOn;
  public MyEntity() {}

  public ObjectId getId() {
    return id;
  }

  public void setId(ObjectId id) {
    this.id = id;
  }

  public Map<ObjectId, String> getValues() {
    return values;
  }

  public void setValues(Map<ObjectId, String> values) {
    this.values = values;
  }

  @Override
  public String toString() {
    return "MyEntity{" +
        "id=" + id +
        ", values=" + values +
        '}';
  }

  public Date getUpdatedOn() {
    return updatedOn;
  }

  public void setUpdatedOn(Date updatedOn) {
    this.updatedOn = updatedOn;
  }
}
