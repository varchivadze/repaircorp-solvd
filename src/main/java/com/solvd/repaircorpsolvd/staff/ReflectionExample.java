package com.solvd.repaircorpsolvd.staff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class ReflectionExample {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionExample.class);

    private ReflectionExample() {
    }

    public static void processReflection() {
        try {
            Class<?> refClas = Class.forName("com.solvd.repaircorpsolvd.staff.Customer");
            LOGGER.info("Constructors ->");
            Arrays.stream(refClas.getConstructors())
                    .forEach(constructor -> LOGGER.info("Constructor {}, Modifiers {}", constructor.getName(), Modifier.toString(constructor.getModifiers())));

            Arrays.stream(refClas.getConstructors()).flatMap(constructor -> Arrays.stream(constructor.getParameterTypes()))
                    .forEach(parametr -> LOGGER.info(parametr.getName()));

            Arrays.stream(refClas.getDeclaredFields())
                    .forEach(field -> LOGGER.info("Field -> {}, Modif -> {}, Type -> {}", field.getName(), Modifier.toString(field.getModifiers()), field.getType().getName()));

            Arrays.stream(refClas.getDeclaredMethods())
                    .forEach(method -> LOGGER.info("Method -> {}, Modif -> {}, RetType -> {}",
                            method.getName(), Modifier.toString(method.getModifiers()), method.getReturnType().getName()));


            Constructor<?> constructor = refClas.getConstructor(String.class, String.class, int.class);
            Object custObj = constructor.newInstance("Burger", "Cheese", 2);

            Method setDiscount = refClas.getMethod("setDiscount", double.class);
            setDiscount.invoke(custObj, 15);

            Method setNumber = refClas.getMethod("setPhoneNumber", String.class);
            setNumber.invoke(custObj, "+3897");

            Method notifPerson = refClas.getMethod("notifyPerson", String.class);
            notifPerson.invoke(custObj, "My notification");


        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }

    }
}
