package io.github.mladensavic94.parsing;

import io.github.mladensavic94.exporting.CSVExporter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static io.github.mladensavic94.parsing.FileTransactionsParser.string2Double;


public class FileParserTest {

    @Test
    public void testFile() {
        FileTransactionsParser parser = new FileTransactionsParser();
        String directoryPath = "E:\\workspace\\raif\\attachments";
        List<String> parse = new ArrayList<>();

        File directory = new File(directoryPath);
        File[] files = directory.listFiles();

        for (File file : files) {
            try {
                parse.addAll(parser.scrape(() -> file));
            }catch (Exception ignored){
            System.out.println(file.getName());
            }
        }
        CSVExporter csvExporter = new CSVExporter("all.csv");
        csvExporter.raw(parse);
    }

    @Test
    public void testDecimalFormat() throws ParseException {
        Assertions.assertEquals(7.23, string2Double("7.23"));
        Assertions.assertEquals(70.23, string2Double("70.23"));
        Assertions.assertEquals(701.23, string2Double("701.23"));
        Assertions.assertEquals(2427.23, string2Double("2427.23"));
        Assertions.assertEquals(402934.16, string2Double("402,934.16"));
    }
}
