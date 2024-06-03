package com.foo;

public enum MyEnum {
  FIRST("first"), SECOND("second"), THIRD("third");

  private String key;

  MyEnum(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }

  public String toString() {
    return this.name();
  }
}
