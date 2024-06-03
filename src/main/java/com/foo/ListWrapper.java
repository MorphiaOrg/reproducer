package com.foo;

import java.util.List;

import dev.morphia.annotations.Entity;

@Entity
public class ListWrapper {
  private List<String> data;

  public ListWrapper() {
  }

  public ListWrapper(List<String> data) {
    this.data = data;
  }

  public List<String> getData() {
    return data;
  }

  public void setData(List<String> data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return "ListWrapper{" +
        "data=" + data +
        '}';
  }
}
