package silas.yudi.linkedin.dtos.response;

import lombok.Data;

@Data
public class Experience {
    private String company;
    private String title;
    private Period period;
    private String location;
    private String description;
}
