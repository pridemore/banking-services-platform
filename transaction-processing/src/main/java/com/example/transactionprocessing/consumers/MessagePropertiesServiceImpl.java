package com.example.transactionprocessing.consumers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessagePropertiesServiceImpl implements MessagePropertiesService{
    private final MessageSource messageSource;
    @Override
    public String getByKey(String key, Object[] args) {
        if (key.isEmpty()) {
            throw new IllegalArgumentException("Message Property Key is required");
        }

        log.trace("Processing get Message by key {}", key);

        return messageSource.getMessage(key, args, Locale.getDefault());
    }

    @Override
    public String getByKey(String key) {
        if (key.isEmpty()) {
            throw new IllegalArgumentException("Message Property Key is required");
        }
        log.trace("Processing get Message by key {}", key);
        return messageSource.getMessage(key, null, Locale.getDefault());

    }
}
