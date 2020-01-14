package io.github.mladensavic94.parsing;

import org.junit.jupiter.api.Test;

import java.io.File;


public class FileParserTest {

    @Test
    public void testFile(){
        FileTransactionParser parser = new FileTransactionParser();
        parser.parse(() -> new File("C:\\Workspaces\\pdf-parser\\izvod.PDF"));
    }
}
