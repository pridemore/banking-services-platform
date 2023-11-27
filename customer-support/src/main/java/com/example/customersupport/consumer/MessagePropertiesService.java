package com.example.customersupport.consumer;

public interface MessagePropertiesService {
    String getByKey(String key, Object[] args);
    String getByKey(String key);
}
