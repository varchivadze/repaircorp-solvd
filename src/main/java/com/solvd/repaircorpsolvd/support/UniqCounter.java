package com.solvd.repaircorpsolvd.support;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class UniqCounter {

    private static final Logger LOGGER = LoggerFactory.getLogger(UniqCounter.class);

    private UniqCounter() {
    }

    public static Map<String, Integer> uniqWordsTxt(String file) {
        HashMap<String, Integer> uniqDict = new HashMap<>();
        try {
            File bookFile = Paths.get(file).toFile();
            String book = FileUtils.readFileToString(bookFile, StandardCharsets.UTF_8);
            Arrays.stream(book.replaceAll("[^a-zA-Z]", " ").split(" "))
                    .toList()
                    .stream()
                    .filter(now -> !StringUtils.isBlank(now))
                    .forEach(now -> uniqDict.put(now.trim().toLowerCase(), uniqDict.getOrDefault(now, 0) + 1));

            File output = new File(bookFile.getParent(), "txt_unique_words.txt");
            StringBuilder dictToString = new StringBuilder("Word: counter\n");
            uniqDict.forEach((word, count) -> dictToString.append(word).append(": ").append(count).append("\n"));
            FileUtils.write(output, dictToString.toString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.error("Got input error {}", e.getMessage());
        }
        return uniqDict;
    }

    public static Map<String, Integer> uniqWordsPdf(String file) {
        HashMap<String, Integer> uniqDict = new HashMap<>();
        Path filePath = Paths.get(file);
        try (PDDocument document = Loader.loadPDF(filePath.toFile())) {
            String book = new PDFTextStripper().getText(document);

            Arrays.stream(book.replaceAll("[^a-zA-Z]", " ").split(" "))
                    .toList()
                    .stream()
                    .filter(now -> !StringUtils.isBlank(now))
                    .forEach(now -> uniqDict.put(now.trim().toLowerCase(), uniqDict.getOrDefault(now, 0) + 1));

            File output = new File(filePath.getParent().toFile(), "pdf_unique_words.txt");
            StringBuilder dictToString = new StringBuilder("Word: counter\n");
            uniqDict.forEach((word, count) -> dictToString.append(word).append(": ").append(count).append("\n"));
            FileUtils.write(output, dictToString.toString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.error("Got input error {}", e.getMessage());
        }
        return uniqDict;
    }
}
