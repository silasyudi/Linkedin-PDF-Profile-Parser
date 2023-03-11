package silas.yudi.linkedin.mapper.pipeline;

import org.apache.pdfbox.text.TextPosition;
import silas.yudi.linkedin.dtos.response.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SummaryPipe extends Pipeline {

    private final static int SIZE_SECTION = 15;
    private List<String> summary;

    public SummaryPipe() {
        this.summary = new ArrayList<>();
    }

    @Override
    public void process(String text, List<TextPosition> textPositions, Profile profile) {
        if (text.equals("Summary") && textPositions.get(0).getFontSizeInPt() == SIZE_SECTION) {
            return;
        }

        if (text.equals("Experience") && textPositions.get(0).getFontSizeInPt() == SIZE_SECTION) {
            String summarize = summary.stream().collect(Collectors.joining(" "));
            profile.setSummary(summarize);
            isEnd = true;
            return;
        }

        summary.add(text);
    }

    @Override
    public int order() {
        return 4;
    }
}
