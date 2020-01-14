package io.github.mladensavic94.parsing;

import com.itextpdf.text.pdf.parser.*;
import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy.TextChunk;
import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy.TextChunkLocationDefaultImp;
import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy.TextChunkLocationStrategy;

import java.util.ArrayList;
import java.util.List;

public class YAxisLocationExtractionStrategy implements TextExtractionStrategy {

    public static final int Y_AXIS_TOLERANCE_MAGIC_NUMBER = 5;

    private List<TextChunk> textChunks = new ArrayList<>();
    private TextChunkLocationStrategy tclStrat;

    public YAxisLocationExtractionStrategy() {
        tclStrat = (renderInfo, baseline) -> new TextChunkLocationDefaultImp(baseline.getStartPoint(), baseline.getEndPoint(), renderInfo.getSingleSpaceWidth());
    }

    @Override
    public String getResultantText() {
        StringBuilder sb = new StringBuilder();
        TextChunk lastChunk = null;
        textChunks.sort(new TextChunkComparator());
        for (TextChunk chunk : textChunks) {
            if (lastChunk != null) {
                if (approxSameLine(chunk, lastChunk)) {
                    if (isChunkAtWordBoundary(chunk, lastChunk) && !startsWithSpace(chunk.getText()) && !endsWithSpace(lastChunk.getText()))
                        sb.append(' ');
                } else {
                    sb.append('\n');
                }
            }
            sb.append(chunk.getText());
            lastChunk = chunk;
        }
        return sb.toString();
    }

    @Override
    public void renderText(TextRenderInfo renderInfo) {
        LineSegment segment = renderInfo.getBaseline();
        if (renderInfo.getRise() != 0){
            Matrix riseOffsetTransform = new Matrix(0, -renderInfo.getRise());
            segment = segment.transformBy(riseOffsetTransform);
        }
        TextChunk tc = new TextChunk(renderInfo.getText(), tclStrat.createLocation(renderInfo, segment));
        textChunks.add(tc);
    }
    private boolean endsWithSpace(String text) {
        if (text == null || text.length() == 0) return false;
        return text.charAt(text.length()-1) == ' ';
    }

    private boolean startsWithSpace(String text) {
        if (text == null || text.length() == 0) return false;
        return text.charAt(0) == ' ';
    }

    private boolean isChunkAtWordBoundary(TextChunk chunk, TextChunk lastChunk) {
        return chunk.getLocation().isAtWordBoundary(lastChunk.getLocation());
    }

    private boolean approxSameLine(TextChunk chunk, TextChunk lastChunk){
        return  chunk.getLocation().orientationMagnitude() == lastChunk.getLocation().orientationMagnitude() &&
                Math.abs(chunk.getLocation().distPerpendicular() - lastChunk.getLocation().distPerpendicular()) < Y_AXIS_TOLERANCE_MAGIC_NUMBER;
    }

    @Override
    public void beginTextBlock() {}

    @Override
    public void endTextBlock() {}

    @Override
    public void renderImage(ImageRenderInfo renderInfo) {}
}
