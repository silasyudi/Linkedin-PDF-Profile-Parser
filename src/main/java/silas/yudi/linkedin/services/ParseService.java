package silas.yudi.linkedin.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import silas.yudi.linkedin.dtos.request.BinaryFile;
import silas.yudi.linkedin.dtos.response.Profile;
import silas.yudi.linkedin.exceptions.InvalidFileException;
import silas.yudi.linkedin.mapper.pipeline.ContactPipe;
import silas.yudi.linkedin.mapper.pipeline.EducationPipe;
import silas.yudi.linkedin.mapper.pipeline.ExperiencePipe;
import silas.yudi.linkedin.mapper.pipeline.LanguagesPipe;
import silas.yudi.linkedin.mapper.pipeline.LocationPipe;
import silas.yudi.linkedin.mapper.pipeline.NamePipe;
import silas.yudi.linkedin.mapper.pipeline.Pipeline;
import silas.yudi.linkedin.mapper.pipeline.SubtitlePipe;
import silas.yudi.linkedin.mapper.pipeline.SummaryPipe;
import silas.yudi.linkedin.mapper.pipeline.TopSkillsPipe;
import silas.yudi.linkedin.textextractor.TextExtractor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

@Service
public class ParseService {

    public Profile parse(MultipartFile file) {
        try (InputStream stream = getStreamFromFile(file)) {
            return extractProfile(stream);
        } catch (IOException e) {
            throw new InvalidFileException(e);
        }
    }

    public Profile parse(BinaryFile file) {
        try (InputStream stream = new ByteArrayInputStream(file.getFile())) {
            return extractProfile(stream);
        } catch (IOException e) {
            throw new InvalidFileException(e);
        }
    }

    private InputStream getStreamFromFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new InvalidFileException("The file is empty or could not be uploaded.");
        }

        if (!file.getContentType().equals("application/pdf")) {
            throw new InvalidFileException("This file is not PDF.");
        }

        return file.getInputStream();
    }

    private Profile extractProfile(InputStream stream) throws IOException {
        Set<Pipeline> pipeline = createPipeline();
        TextExtractor extractor = new TextExtractor(pipeline);
        return extractor.extract(stream);
    }

    private SortedSet<Pipeline> createPipeline() {
        SortedSet<Pipeline> pipeline = new TreeSet<>();
        pipeline.add(new NamePipe());
        pipeline.add(new SubtitlePipe());
        pipeline.add(new LocationPipe());
        pipeline.add(new SummaryPipe());
        pipeline.add(new ExperiencePipe());
        pipeline.add(new EducationPipe());
        pipeline.add(new ContactPipe());
        pipeline.add(new TopSkillsPipe());
        pipeline.add(new LanguagesPipe());
        return pipeline;
    }
}
