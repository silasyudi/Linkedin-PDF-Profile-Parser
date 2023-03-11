package silas.yudi.linkedin.mapper.pipeline;

import org.apache.pdfbox.text.TextPosition;
import silas.yudi.linkedin.dtos.response.Profile;

import java.util.List;

public class LocationPipe extends Pipeline {

    @Override
    public void process(String text, List<TextPosition> textPositions, Profile profile) {
        profile.setLocation(text);
        isEnd = true;
    }

    @Override
    public int order() {
        return 3;
    }
}
