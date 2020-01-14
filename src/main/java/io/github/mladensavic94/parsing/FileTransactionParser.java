package io.github.mladensavic94.parsing;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.*;
import org.w3c.dom.ls.LSOutput;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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
            String firstPageText = pageTextExtractor(reader, new Rectangle(0, 92,612, 455), 1);
//            System.out.println(firstPageText);
//            System.out.println(firstPageText.split("\\n").length);
            String restPagesText = "";
            for (int i = 2; i <= reader.getNumberOfPages(); i++) {
                restPagesText = pageTextExtractor(reader, new Rectangle(0, 92,612, 740), i);
//                System.out.println(restPagesText);
//                System.out.println(restPagesText.split("\\n").length);
            }
            List<String> allLines = firstPageText.lines().collect(Collectors.toList());
            allLines.addAll(restPagesText.lines().collect(Collectors.toList()));
            List<String> blocks = transformLinesIntoBlock(allLines);
            blocks.forEach(System.out::println);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String pageTextExtractor(PdfReader reader, Rectangle rectangle, int page) throws IOException {
        RenderFilter filter = new RegionTextRenderFilter(rectangle);
        TextExtractionStrategy strategy;
        strategy = new FilteredTextRenderListener(new YAxisLocationExtractionStrategy(), filter);
        return PdfTextExtractor.getTextFromPage(reader, page, strategy);
    }

    private List<String> transformLinesIntoBlock(List<String> lines){
        List<String> blocks = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if(isFirstWordDate(line)){
                for (int j = i+1; j < lines.size(); j++) {
                    line += lines.get(j);
                    i = j;
                    if(!isFirstWordDate(lines.get(j)))
                        break;
                }
            }
            blocks.add(line);
        }
        return blocks;
    }

    private boolean isFirstWordDate(String line){
        String temp = line.split(" ")[0];
        try{
            LocalDate.parse(temp, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
