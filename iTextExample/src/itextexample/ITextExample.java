/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itextexample;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 *
 * @author patrick
 */
public class ITextExample {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws DocumentException, FileNotFoundException {
        Document doc = new Document();
        PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("Report.pdf"));
        int page = writer.getPageNumber();
        //writer.setPageEvent(new Event());
        doc.open();
        doc.add(new Paragraph("Page: " + page));
        doc.add(new Paragraph("Hello World", FontFactory.getFont(FontFactory.TIMES_BOLD)));
        PdfPTable table = new PdfPTable(2);
        for (int i = 0; i < 100; i++) {
            if (writer.getPageNumber() != page) {
                System.out.println("page change");
                page = writer.getPageNumber();
                doc.add(new Paragraph("Page: " + page));
            } else {
                table.addCell("TestCell1");
                table.addCell("TestCell2");
            }
        }
        doc.add(table);

        doc.close();
    }
}
