package com.example.transactionprocessing.common;

import java.util.UUID;

public class Utils {
    public static String generateTransactionReference() {
        UUID uuid = UUID.randomUUID();
        String uniqueId = uuid.toString().replace("-", "");
        return "TRX" + uniqueId;
    }
}
