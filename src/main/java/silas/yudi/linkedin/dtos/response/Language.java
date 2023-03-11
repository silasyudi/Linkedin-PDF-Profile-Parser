package silas.yudi.linkedin.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Language {
    private String name;
    private String proficience;
    private Integer scale;
}
