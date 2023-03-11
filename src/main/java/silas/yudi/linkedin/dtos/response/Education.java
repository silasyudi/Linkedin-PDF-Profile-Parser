package silas.yudi.linkedin.dtos.response;

import lombok.Data;

@Data
public class Education {
    private String school;
    private String degree;
    private String course;
    private Period period;
}
