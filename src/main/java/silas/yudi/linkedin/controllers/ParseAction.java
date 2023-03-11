package silas.yudi.linkedin.controllers;

import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import silas.yudi.linkedin.dtos.request.BinaryFile;
import silas.yudi.linkedin.dtos.response.Profile;
import silas.yudi.linkedin.services.ParseService;

@RestController
public class ParseAction {

    private final ParseService parseService;

    public ParseAction(ParseService parseService) {
        this.parseService = parseService;
    }

    @PostMapping(value = "/parse/multipart", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public Profile parse(@RequestParam("file") MultipartFile file) {
        return parseService.parse(file);
    }

    @PostMapping(value = "/parse", consumes = MimeTypeUtils.APPLICATION_JSON_VALUE, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public Profile parse(@RequestBody BinaryFile file) {
        return parseService.parse(file);
    }
}
