package silas.yudi.linkedin.dtos.response;

import lombok.Data;

import java.util.List;

@Data
public class Profile {
    private String name;
    private String subtitle;
    private String location;
    private String summary;
    private List<Experience> experiences;
    private List<Education> educations;
    private Contact contact;
    private List<String> topSkills;
    private List<Language> languages;
}
