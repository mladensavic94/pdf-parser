package io.github.mladensavic94.exporting;

import io.github.mladensavic94.parsing.Transaction;

import java.util.List;

/**
 * @author Mladen Savic (mladensavic94@gmail.com)
 */
public interface Exporter {

    public void export(List<Transaction> transactionList);
}
