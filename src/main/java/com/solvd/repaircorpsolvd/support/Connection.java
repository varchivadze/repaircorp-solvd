package com.solvd.repaircorpsolvd.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.IntStream;

public class Connection {


    private static final Logger LOGGER = LoggerFactory.getLogger(Connection.class);

    public Connection() {
    }

    public String mockFunc() {

        StringBuilder toRet = new StringBuilder("Return has vals -> ");

        IntStream.range(0, 10).forEach(i -> {
            try {
                toRet.append(i);
                LOGGER.info("Appended -> {}", i);
                Thread.sleep(200);
            } catch (InterruptedException e) {
                LOGGER.warn(e.getMessage());
            }
        });

        return toRet.toString();
    }


}
