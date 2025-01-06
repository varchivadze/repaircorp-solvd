package com.solvd.repaircorpsolvd.support;

import java.io.IOException;

public interface AutoCloseable {

    String fileProcess(String filename) throws IOException, InvalidFormatException;
}
