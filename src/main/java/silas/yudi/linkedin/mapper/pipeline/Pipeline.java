package silas.yudi.linkedin.mapper.pipeline;

import org.apache.pdfbox.text.TextPosition;
import silas.yudi.linkedin.dtos.response.Profile;

import java.util.List;

public abstract class Pipeline implements Comparable<Pipeline> {

    protected boolean isEnd = false;

    abstract public void process(String text, List<TextPosition> textPositions, Profile profile);

    public boolean isEnd() {
        return isEnd;
    }

    abstract public int order();

    @Override
    public int compareTo(Pipeline o) {
        return this.order() - o.order();
    }
}
