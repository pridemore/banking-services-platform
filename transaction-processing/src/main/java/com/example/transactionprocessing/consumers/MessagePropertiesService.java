package com.example.transactionprocessing.consumers;

public interface MessagePropertiesService {
    String getByKey(String key, Object[] args);
    String getByKey(String key);
}
