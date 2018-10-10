package me.will.encorpus;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.EventType;
import com.itextpdf.kernel.pdf.canvas.parser.PdfCanvasProcessor;
import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
import com.itextpdf.kernel.pdf.canvas.parser.filter.TextRegionEventFilter;
import com.itextpdf.kernel.pdf.canvas.parser.listener.FilteredEventListener;
import com.itextpdf.kernel.pdf.canvas.parser.listener.LocationTextExtractionStrategy;

import java.io.IOException;

/**
 * Created by duyisong on 09/10/2018.
 */
public class ExtractPageContent {

    public void manipulatePdf() throws IOException {
        String src = "/Users/duyisong/Downloads/wordfrequency.pdf";
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src));
        Rectangle rect = new Rectangle(0, 0, 100, 100);

        FontFilter fontFilter = new FontFilter(rect);
        FilteredEventListener listener = new FilteredEventListener();
        LocationTextExtractionStrategy extractionStrategy = listener.attachEventListener(new LocationTextExtractionStrategy(), fontFilter);
        new PdfCanvasProcessor(listener).processPageContent(pdfDoc.getPage(2));

        String actualText = extractionStrategy.getResultantText();
        System.out.println(actualText);

        pdfDoc.close();
    }

    class FontFilter extends TextRegionEventFilter {
        public FontFilter(Rectangle filterRect) {
            super(filterRect);
        }

        @Override
        public boolean accept(IEventData data, EventType type) {
            if (type.equals(EventType.RENDER_TEXT)) {
                return true;
            }
            return false;
        }
    }



    public static void main(String[] args) throws IOException {
        new ExtractPageContent().manipulatePdf();
    }
}
