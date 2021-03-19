package io.github.mladensavic94.parsing;

import java.text.ParseException;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static io.github.mladensavic94.parsing.FileTransactionsParser.*;
import static io.github.mladensavic94.parsing.FileTransactionsParser.string2Double;

public class RegexTransactionParser implements SingleTransactionParser {

    Pattern pattern = Pattern.compile("([0-9]*,)?[0-9]{1,3}\\.[0-9]{2}\\s");

    @Override
    public Transaction parse(String s) throws ParseException {
        Transaction transaction = new Transaction();
        String date1 = s.substring(0, 10);
        transaction.setDateOfTransaction(string2Date(date1));
        int refValIndex = s.indexOf("0.00");
        String desc = s.substring(22, refValIndex);
        String refVal = s.substring(refValIndex, refValIndex + 4);
        transaction.setRefValue(string2Double(refVal));
        List<MatchResult> collect = pattern.matcher(s).results().collect(Collectors.toList());
        if (collect.size() == 4) {
            transaction.setRefValue(string2Double(collect.get(0).group()));
            transaction.setAmountCredited(string2Double(collect.get(1).group()));
            transaction.setAmountDebited(string2Double(collect.get(2).group()));
            String lastPart = collect.get(3).group();
            transaction.setBalance(string2Double(lastPart));
            transaction.setDesc(desc + " " + s.substring(s.indexOf(lastPart) + lastPart.length()));
        }
        return transaction;
    }
}
