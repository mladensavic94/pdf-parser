package io.github.mladensavic94.parsing;

import java.text.ParseException;

public interface SingleTransactionParser {

    Transaction parse(String s) throws ParseException;
}
