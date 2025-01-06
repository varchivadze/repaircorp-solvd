package com.solvd.repaircorpsolvd.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.IntStream;

public class Connection {

    private final BlockingQueue<Connection> connectionPool;
    private static final int MAX_CONNECTIONS = 5;
    private static Connection instance;
    private static final Logger LOGGER = LoggerFactory.getLogger(Connection.class);

    private Connection() {
        connectionPool = new ArrayBlockingQueue<>(MAX_CONNECTIONS);
    }


    public static Connection getInstance() {
        if (instance == null) {
            synchronized (Connection.class) {
                instance = new Connection();
                instance.initConnections();
            }
        }
        return instance;
    }

    private void initConnections() {
        for (int i = 0; i < MAX_CONNECTIONS; i++) {
            connectionPool.add(new Connection());
        }
    }

    public Connection getConnection() {
        try {
            return connectionPool.take();
        } catch (InterruptedException e) {
            LOGGER.warn("Could not get connection -> {}", e.getMessage());
            return null;
        }
    }

    public void releaseConnection(Connection connection) {
        if (connection != null) {
            try {
                connectionPool.put(connection);
            } catch (InterruptedException e) {
                LOGGER.warn("Could not release connection -> {}", e.getMessage());
            }
        }
    }

    public String mockFunc(int inters) {
        if (inters < 0) {
            throw new IndexOutOfBoundsException("Arg must be positive integer");
        }
        StringBuilder toRet = new StringBuilder("Return has vals -> ");

        IntStream.range(0, inters).forEach(i -> {
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
