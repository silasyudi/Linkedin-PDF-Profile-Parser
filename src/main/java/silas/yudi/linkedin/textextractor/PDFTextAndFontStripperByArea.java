package silas.yudi.linkedin.textextractor;

import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.apache.pdfbox.text.TextPosition;
import silas.yudi.linkedin.dtos.response.Profile;
import silas.yudi.linkedin.mapper.pipeline.Pipeline;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

public class PDFTextAndFontStripperByArea extends PDFTextStripperByArea {

    private static final Pattern PAGINATION = Pattern.compile("^Page \\d+ of \\d+$");
    private final Iterator<Pipeline> pipes;
    private Pipeline currentPipe;
    private Profile profile;

    public PDFTextAndFontStripperByArea(Set<Pipeline> pipes) throws IOException {
        super();
        this.pipes = pipes.iterator();
        this.currentPipe = this.pipes.next();
        this.profile = new Profile();
    }

    @Override
    protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
        if (Objects.isNull(currentPipe)
                || text.equals("\u00A0")
                || PAGINATION.matcher(text).matches()) {
            super.writeString(text, textPositions);
            return;
        }

        currentPipe.process(text, textPositions, profile);

        if (currentPipe.isEnd()) {
            currentPipe = pipes.hasNext() ? pipes.next() : null;
        }

        super.writeString(text, textPositions);
    }

    public Profile getProfile() {
        return profile;
    }
}
