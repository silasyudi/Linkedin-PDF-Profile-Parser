package silas.yudi.linkedin.enums;

public enum Proficience {

    ELEMENTARY("Elementary"),
    LIMITED("Limited Working"),
    PROFESSIONAL("Professional Working"),
    FULL_PROFESSIONAL("Full Professional Working"),
    NATIVE_OR_BILINGUAL("Native or Bilingual"),
    ;

    private final String description;

    Proficience(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Proficience getByDescription(String description) {
        for (Proficience proficience : values()) {
            if (proficience.getDescription().equalsIgnoreCase(description)) {
                return proficience;
            }
        }
        return null;
    }
}
