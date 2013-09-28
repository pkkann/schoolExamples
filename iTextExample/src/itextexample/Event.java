/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itextexample;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author patrick
 */
public class Event extends PdfPageEventHelper{
    
    private int page = 1;
    
    public Event() {
        
    }
    
    @Override
    public void onStartPage(PdfWriter writer, Document document) {
        try {
            document.add(new Paragraph("Page: " + page));
            page++;
        } catch (DocumentException ex) {
            Logger.getLogger(Event.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
