package silas.yudi.linkedin.mapper.pipeline;

import org.apache.pdfbox.text.TextPosition;
import silas.yudi.linkedin.dtos.response.Experience;
import silas.yudi.linkedin.dtos.response.Period;
import silas.yudi.linkedin.dtos.response.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ExperiencePipe extends Pipeline {

    private final static int SIZE_SECTION = 15;
    private final static int SIZE_COMPANY = 12;
    private final static int SIZE_TITLE_EXPERIENCE = 11;
    private List<Experience> experiences;
    private Experience experience;
    private List<String> description;

    public ExperiencePipe() {
        this.experiences = new ArrayList<>();
        this.experience = null;
        this.description = new ArrayList<>();
    }

    @Override
    public void process(String text, List<TextPosition> textPositions, Profile profile) {
        if (text.equals("Education") && textPositions.get(0).getFontSizeInPt() == SIZE_SECTION) {
            endsPipe(profile);
            return;
        }

        if (textPositions.get(0).getFontSizeInPt() == SIZE_COMPANY) {
            processCompanyName(text);
            return;
        }

        if (textPositions.get(0).getFontSizeInPt() == SIZE_TITLE_EXPERIENCE) {
            processTitleExperience(text);
            return;
        }

        if (Objects.isNull(experience.getTitle())) {
            return;
        }

        if (Objects.isNull(experience.getPeriod())) {
            processPeriod(text);
            return;
        }

        if (Objects.isNull(experience.getLocation())) {
            experience.setLocation(text);
            return;
        }

        description.add(text);
    }

    @Override
    public int order() {
        return 5;
    }

    private void endsPipe(Profile profile) {
        finishExperience();
        profile.setExperiences(experiences);
        isEnd = true;
    }

    private void processCompanyName(String text) {
        if (Objects.nonNull(experience)) {
            finishExperience();
        }

        experience = new Experience();
        experience.setCompany(text);
    }

    private void processTitleExperience(String text) {
        if (Objects.nonNull(experience.getTitle())) {
            String company = experience.getCompany();
            finishExperience();

            experience = new Experience();
            experience.setCompany(company);
        }

        experience.setTitle(text);
    }

    private void processPeriod(String text) {
        int dash = text.indexOf("-");
        int parentesis = text.indexOf("(");

        String start = text.substring(0, dash - 1);
        String end = text.substring(dash + 2, parentesis - 1);

        experience.setPeriod(new Period(start, end));
    }

    private void finishExperience() {
        String join = description.stream().collect(Collectors.joining(" "));
        experience.setDescription(join);
        experiences.add(experience);

        description = new ArrayList<>();
    }
}
