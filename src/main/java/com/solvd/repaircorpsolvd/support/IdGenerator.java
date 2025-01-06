package com.solvd.repaircorpsolvd.support;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

// to prevent creating multiple generators anywhere with own implementation and possibility to cause collisions
public final class IdGenerator {

    // moved generator to static to show static block and access anywhere // singleton is better, I believe
    private static long idCount = 0;
    private static long custIdCount = 0;
    private static long repairId = 0;
    private static long partsOrder = 0;
    private static long delivOrder = 0;
    private static long unknown = 0;

    private IdGenerator() {

    }

    static {
        // simulate DB connection to reach last given id
        idCount = 100;
        custIdCount = 3;
        repairId = 25;
        partsOrder = 35;
        delivOrder = 21;
        unknown = 200;
    }

    // all methods are static to reach anywhere
    private static String getCallingClassName() {
        ArrayList<StackTraceElement> stackTrace = new ArrayList<>(Arrays.asList(Thread.currentThread().getStackTrace()));

        if (stackTrace.size() > 3) {
            return stackTrace.get(3).getClassName();
        } else {
            return "Unknown";
        }
    }

    private static String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return LocalDate.now().format(formatter);
    }

    private static long newEmpId() {
        return Long.parseLong("99" + ++idCount);
    }

    private static long newCustId() {
        return Long.parseLong("1400" + ++custIdCount);
    }

    private static long repairOrdId() {
        return Long.parseLong(getDate() + ++repairId);
    }

    private static long partsOrdId() {
        return Long.parseLong(getDate() + ++partsOrder);
    }

    private static long deliveryId() {
        return Long.parseLong(getDate() + delivOrder);
    }

    public static long createId() {
        String callingClass = getCallingClassName();
        return switch (callingClass) {
            case "staff.Employee" -> newEmpId();
            case "staff.Customer" -> newCustId();
            case "operations.RepairOrder" -> repairOrdId();
            case "operations.PartsOrder" -> partsOrdId();
            case "operations.DeliverOrder" -> deliveryId();
            default -> ++unknown;
        };
    }
}
