package silas.yudi.linkedin.mapper.pipeline;

import org.apache.pdfbox.text.TextPosition;
import silas.yudi.linkedin.dtos.response.Profile;

import java.util.ArrayList;
import java.util.List;

public class TopSkillsPipe extends Pipeline {

    private final static int SIZE_SECTION = 13;
    private final List<String> skills;

    public TopSkillsPipe() {
        this.skills = new ArrayList<>();
    }

    @Override
    public void process(String text, List<TextPosition> textPositions, Profile profile) {
        if (text.equals("Languages") && textPositions.get(0).getFontSizeInPt() == SIZE_SECTION) {
            profile.setTopSkills(skills);
            isEnd = true;
            return;
        }

        skills.add(text);
    }

    @Override
    public int order() {
        return 8;
    }
}
