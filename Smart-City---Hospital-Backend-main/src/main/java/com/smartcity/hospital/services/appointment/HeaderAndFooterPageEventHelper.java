package com.smartcity.hospital.services.appointment;

import java.net.URL;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

class HeaderAndFooterPageEventHelper extends PdfPageEventHelper {

    @Override
    public void onStartPage(PdfWriter writer, Document document) {

        /* Header */
        PdfPTable table = new PdfPTable(3);
        table.setTotalWidth(510);
        table.setWidths(new int[]{38, 36, 36});
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setPaddingBottom(5);
        table.getDefaultCell().setBorder(Rectangle.BOTTOM);

        PdfPCell emptyCell = new PdfPCell(new Paragraph(""));
        emptyCell.setBorder(Rectangle.NO_BORDER);

        // Row#1 having 1 empty cell, 1 title cell and empty cell.
        table.addCell(emptyCell);
        Paragraph title =  new Paragraph("Smart City.", new Font(Font.COURIER, 20, Font.BOLD));
        PdfPCell titleCell = new PdfPCell(title);
        titleCell.setPaddingBottom(10);
        titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        titleCell.setBorder(Rectangle.NO_BORDER);
        table.addCell(titleCell);
        table.addCell(emptyCell);

        //Row#2 having 3 cells
        Font cellFont = new Font(Font.HELVETICA, 8);
        table.addCell(new Paragraph("Phone Number: +91-9834984086", cellFont));
        table.addCell(new Paragraph("Address : Pune, India", cellFont));
        table.addCell(new Paragraph("Website : http://www.smartcity.com", cellFont));

        // write the table on PDF
        table.writeSelectedRows(0, -1, 34, 828, writer.getDirectContent());
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {

        /* Footer */
        PdfPTable table = new PdfPTable(2);
        table.setTotalWidth(510);
        table.setWidths(new int[]{50,50});
        // Magic about default cell - if you add styling to default cell it will apply to all cells except cells added using addCell(PdfPCell) method.
        table.getDefaultCell().setPaddingBottom(5);
        table.getDefaultCell().setBorder(Rectangle.TOP);

        Paragraph title =  new Paragraph("Smart City.", new Font(Font.HELVETICA, 10));
        PdfPCell titleCell = new PdfPCell(title);
        titleCell.setPaddingTop(4);
        titleCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        titleCell.setBorder(Rectangle.TOP);
        table.addCell(titleCell);

        Paragraph pageNumberText =  new Paragraph("Page "+document.getPageNumber(), new Font(Font.HELVETICA, 10));
        PdfPCell pageNumberCell = new PdfPCell(pageNumberText);
        pageNumberCell.setPaddingTop(4);
        pageNumberCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        pageNumberCell.setBorder(Rectangle.TOP);
        table.addCell(pageNumberCell);

        // write the table on PDF
        table.writeSelectedRows(0, -1, 34, 36, writer.getDirectContent());
    }
}