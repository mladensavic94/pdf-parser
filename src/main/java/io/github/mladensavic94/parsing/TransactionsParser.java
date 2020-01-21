package io.github.mladensavic94.parsing;

import java.util.List;
import java.util.function.Supplier;

public interface TransactionsParser<I> {


    List<Transaction> parse(Supplier<I> input);
}
