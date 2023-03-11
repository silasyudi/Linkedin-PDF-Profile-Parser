package silas.yudi.linkedin.mapper.pipeline;

import org.apache.pdfbox.text.TextPosition;
import silas.yudi.linkedin.dtos.response.Profile;

import java.util.List;

public class SubtitlePipe extends Pipeline {

    @Override
    public void process(String text, List<TextPosition> textPositions, Profile profile) {
        profile.setSubtitle(text);
        isEnd = true;
    }

    @Override
    public int order() {
        return 2;
    }
}
