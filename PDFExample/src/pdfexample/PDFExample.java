/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pdfexample;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 *
 * @author Patrick
 */
public class PDFExample {

    /**
     * @param args the command line arguments
     * @throws com.itextpdf.text.DocumentException
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws DocumentException, FileNotFoundException {
        Document doc = new Document();
        
        PdfWriter.getInstance(doc, new FileOutputStream(new File("test.pdf")));
        
        doc.open();
        
        PdfPTable table = new PdfPTable(4);
        PdfPCell cell1 = new PdfPCell(new Phrase("Test1"));
        cell1.setBackgroundColor(BaseColor.BLUE);
        PdfPCell cell2 = new PdfPCell(new Phrase("Test2"));
        PdfPCell cell3 = new PdfPCell(new Phrase("Test3"));
        PdfPCell cell4 = new PdfPCell(new Phrase("Test4"));
        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        
        for(int i = 0; i < 10; i++) {
            PdfPCell cell = new PdfPCell(new Phrase("Test"));
            table.addCell(cell);
        }
        
        doc.add(table);
        doc.close();
        
    }
    
}
