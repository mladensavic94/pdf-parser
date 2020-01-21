package io.github.mladensavic94.parsing;

import org.junit.jupiter.api.Test;

import java.io.File;


public class FileParserTest {

    @Test
    public void testFile() throws FileNotFoundException {
        FileTransactionParser parser = new FileTransactionParser();
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
}
