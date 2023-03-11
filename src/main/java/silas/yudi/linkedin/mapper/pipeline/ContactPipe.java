package silas.yudi.linkedin.mapper.pipeline;

import org.apache.pdfbox.text.TextPosition;
import silas.yudi.linkedin.dtos.response.Contact;
import silas.yudi.linkedin.dtos.response.Profile;

import java.util.List;
import java.util.regex.Pattern;

public class ContactPipe extends Pipeline {

    private final static Pattern EMAIL_REGEX = Pattern.compile("^\\S+@\\S+(\\.+\\S+)+$");
    private final static Pattern LINKEDIN_REGEX = Pattern.compile("^(https?\\:\\/\\/)?(www\\.)?linkedin\\.com\\/in\\/\\S+$");
    private final static int SIZE_SECTION = 13;
    private final Contact contact;

    public ContactPipe() {
        this.contact = new Contact();
    }

    @Override
    public void process(String text, List<TextPosition> textPositions, Profile profile) {
        if (text.equals("Top Skills") && textPositions.get(0).getFontSizeInPt() == SIZE_SECTION) {
            profile.setContact(contact);
            isEnd = true;
            return;
        }

        if (EMAIL_REGEX.matcher(text).matches()) {
            contact.setEmail(text);
            return;
        }

        if (LINKEDIN_REGEX.matcher(text).matches()) {
            contact.setLinkedin(text);
        }
    }

    @Override
    public int order() {
        return 7;
    }
}
