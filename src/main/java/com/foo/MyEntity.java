package com.foo;

import org.bson.types.ObjectId;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

@Entity("MyEntityCollection")
public class MyEntity {
  @Id
  private ObjectId id;
  private String otherData;
  private ListWrapper listWrapper;

  public MyEntity() {
  }

  public MyEntity(ObjectId id, String otherData, ListWrapper listWrapper) {
    this.id = id;
    this.otherData = otherData;
    this.listWrapper = listWrapper;
  }

  public ObjectId getId() {
    return id;
  }

  public void setId(ObjectId id) {
    this.id = id;
  }

  public String getOtherData() {
    return otherData;
  }

  public void setOtherData(String otherData) {
    this.otherData = otherData;
  }

  public ListWrapper getListWrapper() {
    return listWrapper;
  }

  public void setListWrapper(ListWrapper listWrapper) {
    this.listWrapper = listWrapper;
  }

  @Override
  public String toString() {
    return "MyEntity{" +
        "id=" + id +
        ", otherData='" + otherData + '\'' +
        ", listWrapper=" + listWrapper +
        '}';
  }
}
