package silas.yudi.linkedin.mapper.pipeline;

import org.apache.pdfbox.text.TextPosition;
import silas.yudi.linkedin.dtos.response.Language;
import silas.yudi.linkedin.dtos.response.Profile;
import silas.yudi.linkedin.enums.Proficience;

import java.util.ArrayList;
import java.util.List;

public class LanguagesPipe extends Pipeline {

    private final static int SIZE_SECTION = 13;
    private final List<Language> languages;

    public LanguagesPipe() {
        this.languages = new ArrayList<>();
    }

    @Override
    public void process(String text, List<TextPosition> textPositions, Profile profile) {
        if (text.equals("Certifications") && textPositions.get(0).getFontSizeInPt() == SIZE_SECTION) {
            profile.setLanguages(languages);
            isEnd = true;
            return;
        }

        languages.add(parseLanguage(text));
    }

    private Language parseLanguage(String text) {
        int parentesis = text.indexOf("(");

        String language = text.substring(0, parentesis - 1);
        String proficienceDescription = text.substring(parentesis + 1, text.length() - 1);
        Proficience proficience = Proficience.getByDescription(proficienceDescription);

        return new Language(language, proficienceDescription, proficience.ordinal() + 1);
    }

    @Override
    public int order() {
        return 9;
    }
}
