package io.github.mladensavic94.parsing;

import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy.TextChunk;
import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy.TextChunkLocation;

import java.util.Comparator;

import static io.github.mladensavic94.parsing.YAxisLocationExtractionStrategy.Y_AXIS_TOLERANCE_MAGIC_NUMBER;

public class TextChunkComparator implements Comparator<TextChunk> {

    @Override
    public int compare(TextChunk t1, TextChunk t2) {
        TextChunkLocation tl1 = t1.getLocation();
        TextChunkLocation tl2 = t2.getLocation();
        int ydist = tl1.distPerpendicular() - tl2.distPerpendicular();
        if(Math.abs(ydist) < Y_AXIS_TOLERANCE_MAGIC_NUMBER){
            return Float.compare(tl1.distParallelStart(), tl2.distParallelStart());
        }
        return Integer.compare(tl1.distPerpendicular(), tl2.distPerpendicular());
    }
}
