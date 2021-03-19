package io.github.mladensavic94.parsing;

import io.github.mladensavic94.exporting.CSVExporter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;

import static io.github.mladensavic94.parsing.FileTransactionsParser.string2Double;


public class FileParserTest {

    @Test
    public void testFile() throws FileNotFoundException {
        FileTransactionsParser parser = new FileTransactionsParser();
        List<Transaction> parse = parser.parse(() -> new File("src/test/resources/11-20.PDF"));
        parse.addAll(parser.parse(() -> new File("src/test/resources/12-20.PDF")));
        parse.addAll(parser.parse(() -> new File("src/test/resources/01-21.PDF")));
        parse.addAll(parser.parse(() -> new File("src/test/resources/02-21.PDF")));
        CSVExporter csvExporter = new CSVExporter("1120-0221.csv");
        csvExporter.export(parse);
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
