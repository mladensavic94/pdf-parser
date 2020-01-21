package io.github.mladensavic94.parsing;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;

import static io.github.mladensavic94.parsing.FileTransactionsParser.string2Double;
import static org.junit.Assert.assertEquals;


public class FileParserTest {

    @Test
    public void testFile() throws FileNotFoundException {
        FileTransactionsParser parser = new FileTransactionsParser();
        List<Transaction> parse = parser.parse(() -> new File(".\\izvod.PDF"));
        System.out.println(parse);
//        PrintWriter out = new PrintWriter("out.csv");
//        for (Transaction transaction : parse) {
//            out.write(transaction.toString());
//            out.write("\n");
//        }
//        out.flush();
//        out.close();
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
