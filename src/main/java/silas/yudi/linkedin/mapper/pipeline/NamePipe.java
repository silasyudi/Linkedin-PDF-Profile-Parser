package silas.yudi.linkedin.mapper.pipeline;

import org.apache.pdfbox.text.TextPosition;
import silas.yudi.linkedin.dtos.response.Profile;

import java.util.List;

public class NamePipe extends Pipeline {

    private final static int SIZE_SECTION = 26;

    @Override
    public void process(String text, List<TextPosition> textPositions, Profile profile) {
        if (textPositions.get(0).getFontSizeInPt() == SIZE_SECTION) {
            profile.setName(text);
            isEnd = true;
        }
    }

    @Override
    public int order() {
        return 1;
    }
}
