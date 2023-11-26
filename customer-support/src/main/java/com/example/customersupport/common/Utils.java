package com.example.customersupport.common;

import java.util.UUID;

public class Utils {
    public static String generateTransactionReference() {
        UUID uuid = UUID.randomUUID();
        String uniqueId = uuid.toString().replace("-", "");
        return "TRX" + uniqueId;
    }

    public static String generateTicketReference() {
        UUID uuid = UUID.randomUUID();
        String uniqueId = uuid.toString().replace("-", "");
        return "QURY" + uniqueId;
    }
}
