package com.example.bco.service;

import com.example.bco.model.Student;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.pdf.draw.LineSeparator;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;

@Service
public class AdmitCardPdfService {

    public String generateAdmitCard(Student student) {

        String outputPath = "output/admit-cards/" + student.getRollNumber() + ".pdf";

        try {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();

            Font headerFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Font titleFont = new Font(Font.HELVETICA, 14, Font.BOLD);
            Font normalFont = new Font(Font.HELVETICA, 11);
            Font rollFont = new Font(Font.HELVETICA, 13, Font.BOLD);

            // Header
            Paragraph header = new Paragraph("Bangladesh Chemistry Olympiad Society", headerFont);
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);

            Paragraph title = new Paragraph("ADMIT CARD\n\n", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new LineSeparator());

            // Roll number box
            PdfPTable rollTable = new PdfPTable(1);
            rollTable.setWidthPercentage(100);

            PdfPCell rollCell = new PdfPCell(
                    new Paragraph("Roll Number: " + student.getRollNumber(), rollFont)
            );
            rollCell.setPadding(10);
            rollCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            rollCell.setBorderWidth(2);
            rollTable.addCell(rollCell);

            document.add(rollTable);
            document.add(new Paragraph("\n"));

            // Main table (info + photo)
            PdfPTable mainTable = new PdfPTable(2);
            mainTable.setWidthPercentage(100);
            mainTable.setWidths(new float[]{70, 30});

            PdfPCell infoCell = new PdfPCell();
            infoCell.setBorder(Rectangle.NO_BORDER);
            infoCell.setPaddingRight(10);

            infoCell.addElement(new Paragraph("Name (English): " + student.getNameEnglish(), normalFont));
            infoCell.addElement(new Paragraph("Name (Bangla): " + student.getNameBangla(), normalFont));
            infoCell.addElement(new Paragraph("Father's Name: " + student.getFatherName(), normalFont));
            infoCell.addElement(new Paragraph("Mother's Name: " + student.getMotherName(), normalFont));
            infoCell.addElement(new Paragraph("Institution: " + student.getInstitution(), normalFont));
            infoCell.addElement(new Paragraph("Date of Birth: " + student.getDateOfBirth(), normalFont));
            infoCell.addElement(new Paragraph("Medium: " + student.getMedium(), normalFont));

            mainTable.addCell(infoCell);

            // Improved Photo Handling
            PdfPCell photoCell;
            try {
                Image photo = Image.getInstance("src/main/resources/" + student.getPhotoPath());
                // Scaling to fit a standard passport size aspect ratio
                photo.scaleToFit(110, 135);
                
                // Using image constructor for better alignment control
                photoCell = new PdfPCell(photo, false);
                photoCell.setPadding(5);
            } catch (Exception e) {
                photoCell = new PdfPCell(new Paragraph("Photo Not Found", normalFont));
            }

            photoCell.setFixedHeight(145);
            photoCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            photoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            photoCell.setBorderWidth(1);
            photoCell.setBorderColor(java.awt.Color.GRAY);

            mainTable.addCell(photoCell);

            document.add(mainTable);
            document.add(new Paragraph("\n"));

            // Instructions
            document.add(new LineSeparator());
            document.add(new Paragraph("Instructions:", titleFont));

            List instructionList = new List(List.ORDERED);
            instructionList.add(new ListItem("Bring this admit card to the examination hall."));
            instructionList.add(new ListItem("Bring a valid photo ID."));
            instructionList.add(new ListItem("Mobile phones are not allowed."));
            document.add(instructionList);

            document.add(new Paragraph("\n\n"));

            // Footer
            PdfPTable footerTable = new PdfPTable(2);
            footerTable.setWidthPercentage(100);

            PdfPCell left = new PdfPCell(new Paragraph("Signature of Authority"));
            left.setBorder(Rectangle.NO_BORDER);

            PdfPCell right = new PdfPCell(new Paragraph("Exam Date: __________"));
            right.setHorizontalAlignment(Element.ALIGN_RIGHT);
            right.setBorder(Rectangle.NO_BORDER);

            footerTable.addCell(left);
            footerTable.addCell(right);

            document.add(footerTable);

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return outputPath;
    }
}