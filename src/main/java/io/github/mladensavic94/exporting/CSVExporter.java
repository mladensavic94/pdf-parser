package io.github.mladensavic94.exporting;

import io.github.mladensavic94.parsing.Transaction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * @author Mladen Savic (mladensavic94@gmail.com)
 */
public class CSVExporter implements Exporter {

    String fileName;

    public CSVExporter(String fileName) {
        this.fileName = fileName;
    }


    @Override
    public void export(List<Transaction> transactionList) {
        try {
            File file = new File(fileName);
            PrintWriter out = new PrintWriter(file, StandardCharsets.UTF_8);
            out.println(Transaction.csvHeaders());
            transactionList.stream().filter(Objects::nonNull).forEach(out::println);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void raw(List<String> transactionList) {
        try {
            File file = new File(fileName);
            PrintWriter out = new PrintWriter(file, StandardCharsets.UTF_8);
            out.println(Transaction.csvHeaders());
            transactionList.stream().filter(Objects::nonNull).forEach(out::println);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
