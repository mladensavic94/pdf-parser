package io.github.mladensavic94.parsing;

import java.text.ParseException;

import static io.github.mladensavic94.parsing.FileTransactionsParser.*;

public class DefaultSingleTransactionParser implements SingleTransactionParser {

    @Override
    public Transaction parse(String s) throws ParseException {
        System.out.println(s);
        Transaction transaction = new Transaction();
        String date1 = s.substring(0, 10);
        transaction.setDateOfTransaction(string2Date(date1));
            int refValIndex = s.indexOf("0.00");
            String desc = s.substring(22, refValIndex);
            transaction.setCardNumber(string2CardNumber(desc));
            String refVal = s.substring(refValIndex, refValIndex + 4);
            transaction.setRefValue(string2Double(refVal));
            String subline = s.substring(refValIndex + 4);
            String[] pare = subline.split(" ");
            transaction.setAmountCredited(string2Double(pare[3]));
            transaction.setAmountDebited(string2Double(pare[4]));
            transaction.setBalance(string2Double(pare[5]));
            transaction.setDesc(desc + subline.substring(subline.indexOf(pare[5]) + pare[5].length()));
            if(transaction.getCardNumber() > 0)
                transaction.setDesc(transaction.getDesc().substring(5));
        return transaction;
    }

}
