package io.github.mladensavic94.parsing;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class FileTransactionsParser implements TransactionsParser<File> {

    List<SingleTransactionParser> parsers = List.of(new DefaultSingleTransactionParser(), new PaymentSingleTransactionParser());

    @Override
    public List<Transaction> parse(Supplier<File> input) {
        return textExtractor(input.get());
    }

    private List<Transaction> textExtractor(File in) {
        try {
            PdfReader reader = new PdfReader(new FileInputStream(in));
            String firstPageText = pageTextExtractor(reader, new Rectangle(0, 92, 612, 455), 1);
            String restPagesText = "";
            for (int i = 2; i <= reader.getNumberOfPages(); i++) {
                restPagesText += "\n" + pageTextExtractor(reader, new Rectangle(0, 92, 612, 738), i);
            }
            List<String> allLines = firstPageText.lines().collect(Collectors.toList());
            allLines.addAll(restPagesText.lines().collect(Collectors.toList()));
            List<String> blocks = transformLinesIntoBlock(allLines);
            reader.close();
            return blocks.stream().map(this::lineToTransaction).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String pageTextExtractor(PdfReader reader, Rectangle rectangle, int page) throws IOException {
        RenderFilter filter = new RegionTextRenderFilter(rectangle);
        TextExtractionStrategy strategy;
        strategy = new FilteredTextRenderListener(new YAxisLocationExtractionStrategy(), filter);
        return PdfTextExtractor.getTextFromPage(reader, page, strategy);
    }

    private List<String> transformLinesIntoBlock(List<String> lines) {
        List<String> blocks = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i) + " ";
            int j = i + 1;
            for (; j < lines.size(); j++) {
                if (!isFirstWordDate(lines.get(j))) {
                    line += lines.get(j);
                } else {
                    i = j - 1;
                    break;
                }
            }
            blocks.add(line);
            if(j == lines.size()) break;
        }
        return blocks;
    }

    private Transaction lineToTransaction(String line) {
        System.out.println(line);
        for (SingleTransactionParser parser : parsers) {
            try {
                return parser.parse(line);
            }catch (Exception ignored){}
        }
        return null;
    }

    private boolean isFirstWordDate(String line) {
        String temp = line.split(" ")[0];
        try {
            LocalDate.parse(temp, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static LocalDate string2Date(String date){
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static int string2CardNumber(String desc){
        try {
            return Integer.parseInt(desc.substring(0,4));
        }catch (Exception e){
            return 0;
        }
    }
    public static double string2Double(String d) throws ParseException {
        DecimalFormat decimalFormat = new DecimalFormat("###,###.##");
        Number number = decimalFormat.parse(d);
        return number.doubleValue();
    }
}
