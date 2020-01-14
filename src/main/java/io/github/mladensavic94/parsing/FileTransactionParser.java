package io.github.mladensavic94.parsing;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class FileTransactionParser implements TransactionParser<File> {

    @Override
    public List<Transaction> parse(Supplier<File> input) {
        File in = input.get();
        List<Transaction> transactions = new ArrayList<>();
        textExtractor(in);
        return transactions;
    }

    private void textExtractor(File in) {
        try {
            PdfReader reader = new PdfReader(new FileInputStream(in));
            Rectangle rect = new Rectangle(0, 92,612, 455);
            RenderFilter filter = new RegionTextRenderFilter(rect);
            TextExtractionStrategy strategy;
            strategy = new FilteredTextRenderListener(new LocationTextExtractionStrategy(), filter);
            String textFromPage = PdfTextExtractor.getTextFromPage(reader, 1, strategy);
            String[] split = textFromPage.split("Kurs:");
            for (String block : split) {
                transactionExtractor(block);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void transactionExtractor(String block){
        String[] s = block.split(" ");
        System.out.println(block.trim());
        for (String s1 : s) {
            System.out.println(s1.trim());
        }
    }


}
