package silas.yudi.linkedin.textextractor;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import silas.yudi.linkedin.dtos.response.Profile;
import silas.yudi.linkedin.mapper.pipeline.Pipeline;

import java.awt.Rectangle;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

public class TextExtractor {

    private static final String LEFT_COLUMN = "left_column";
    private static final String RIGHT_COLUMN = "right_column";
    private static final Rectangle LEFT_RECTANGLE = new Rectangle(0, 0, 200, 800);
    private static final Rectangle RIGHT_RECTANGLE = new Rectangle(210, 0, 400, 800);

    private PDFTextAndFontStripperByArea stripper;

    public TextExtractor(Set<Pipeline> pipes) throws IOException {
        this.stripper = new PDFTextAndFontStripperByArea(pipes);
    }

    public Profile extract(InputStream file) throws IOException {
        PDPageTree allPages = getAllPagesFromFile(file);

        this.stripper.setSortByPosition(true);
        this.stripper.addRegion(RIGHT_COLUMN, RIGHT_RECTANGLE);
        processRegion(allPages);

        this.stripper.removeRegion(RIGHT_COLUMN);
        this.stripper.addRegion(LEFT_COLUMN, LEFT_RECTANGLE);
        processRegion(allPages);

        return stripper.getProfile();
    }

    private PDPageTree getAllPagesFromFile(InputStream file) throws IOException {
        PDDocument document = Loader.loadPDF(file);
        return document.getDocumentCatalog().getPages();
    }

    private void processRegion(PDPageTree allPages) throws IOException {
        for (int i = 0; i < allPages.getCount(); i++) {
            PDPage page = allPages.get(i);
            stripper.extractRegions(page);
        }
    }
}
