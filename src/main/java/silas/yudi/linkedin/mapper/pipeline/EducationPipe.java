package silas.yudi.linkedin.mapper.pipeline;

import org.apache.pdfbox.text.TextPosition;
import silas.yudi.linkedin.dtos.response.Education;
import silas.yudi.linkedin.dtos.response.Period;
import silas.yudi.linkedin.dtos.response.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EducationPipe extends Pipeline {

    private final static int SIZE_SECTION = 13;
    private final static int SIZE_SCHOOL = 12;
    private List<Education> educations;
    private Education education;

    public EducationPipe() {
        this.educations = new ArrayList<>();
        this.education = null;
    }

    @Override
    public void process(String text, List<TextPosition> textPositions, Profile profile) {
        if (text.equals("Contact") && textPositions.get(0).getFontSizeInPt() == SIZE_SECTION) {
            profile.setEducations(educations.isEmpty() ? null : educations);
            isEnd = true;
            return;
        }

        if (textPositions.get(0).getFontSizeInPt() == SIZE_SCHOOL) {
            education = new Education();
            education.setSchool(text);
            return;
        }

        if (Objects.nonNull(education)) {
            processInformation(text);
            educations.add(education);
        }
    }

    @Override
    public int order() {
        return 6;
    }

    private void processInformation(String text) {
        int comma = text.indexOf(",");
        int dot = text.indexOf((char) 183);
        int dash = text.indexOf("-");
        int openParentesis = text.indexOf("(");

        String degree = text.substring(0, comma);
        String course = text.substring(comma + 2, dot - 1);
        String start = text.substring(openParentesis + 1, dash - 1);
        String end = text.substring(dash + 2, text.length() - 1);

        education.setDegree(degree);
        education.setCourse(course);
        education.setPeriod(new Period(start, end));
    }
}
