package com.solvd.repaircorpsolvd.accounting;

import com.solvd.repaircorpsolvd.support.CalculationRuntimeException;
import com.solvd.repaircorpsolvd.support.EmptyFileException;
import com.solvd.repaircorpsolvd.support.InvalidFormatException;
import com.solvd.repaircorpsolvd.support.TxtFileDataImporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

public class AccountingProcesses {

    private static final TxtFileDataImporter VALIDATOR = TxtFileDataImporter.getInstance();
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountingProcesses.class);

    private AccountingProcesses() {
    }

    public static BigDecimal sumOfInvoice(String invoice) throws EmptyFileException, CalculationRuntimeException {
        if (invoice.isEmpty()) {
            throw new EmptyFileException("An empty invoice was given!");
        }
        String formattedInvoice = "";
        try {
            formattedInvoice = VALIDATOR.fileProcess(invoice);
        } catch (IOException | InvalidFormatException e) {
            LOGGER.error(e.getMessage());
            throw new IllegalArgumentException("Illegal argument as file");
        }
        BigDecimal finalCount = BigDecimal.ZERO;
        try {
            ArrayList<String> splittedInvoice = new ArrayList<>(Arrays.asList(formattedInvoice.split("/")));
            finalCount = splittedInvoice.stream()
                    .filter(now -> !now.isEmpty())
                    .map(BigDecimal::new)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } catch (Exception e) {
            throw new CalculationRuntimeException("Could not finish process due to calculation error " + e);
        }

        LOGGER.info("Sum of invoice {}", finalCount);

        return finalCount;
    }
}
