package com.solvd.repaircorpsolvd.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class ConnectionPool {

    private final BlockingQueue<Connection> connectionPool;
    private static final int MAX_CONNECTIONS = 5;
    private static ConnectionPool instance;
    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionPool.class);

    private ConnectionPool() {
        connectionPool = new ArrayBlockingQueue<>(MAX_CONNECTIONS);
    }


    public static ConnectionPool getInstance() {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                instance = new ConnectionPool();
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
}
