package com.example.xmlvalidator;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class XmlValidator {
    private static final List<String> errors = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        Path startDir = Path.of("src/main");
        validateXmlFiles(startDir.toFile());
        
        if (!errors.isEmpty()) {
            System.err.println("\nXML validation failed:");
            errors.forEach(System.err::println);
            System.exit(1);
        }
    }

    private static void validateXmlFiles(File dir) {
        File[] files = dir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                validateXmlFiles(file);
            } else if (file.getName().endsWith(".xml")) {
                validateSingleFile(file);
            }
        }
    }

    private static void validateSingleFile(File file) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            
            factory.newDocumentBuilder().parse(file);
            System.out.println("✅ Valid XML: " + file);
        } catch (Exception e) {
            String errorMsg = String.format("❗ %s: %s",
                file.getPath(),
                e.getMessage().split("\n")[0]
            );
            errors.add(errorMsg);
        }
    }
}