package com.ozgecmis.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.ozgecmis.model.Education;
import com.ozgecmis.model.PersonalInfo;
import com.ozgecmis.model.ResumeData;
import com.ozgecmis.model.WorkExperience;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ResumePdfGenerator {

    private Font nameFont;
    private Font titleFont;
    private Font sectionFont;
    private Font subHeaderFont;
    private Font normalFont;
    private Font italicFont;

    public ResumePdfGenerator() {
        initFonts();
    }

    private void initFonts() {
        try {
            // Türkçe karakter desteği için CP1254 kodlaması
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, "CP1254", BaseFont.NOT_EMBEDDED);
            BaseFont bfBold = BaseFont.createFont(BaseFont.HELVETICA_BOLD, "CP1254", BaseFont.NOT_EMBEDDED);
            BaseFont bfOblique = BaseFont.createFont(BaseFont.HELVETICA_OBLIQUE, "CP1254", BaseFont.NOT_EMBEDDED);

            nameFont = new Font(bfBold, 20, Font.NORMAL, new BaseColor(44, 62, 80));
            titleFont = new Font(bf, 13, Font.NORMAL, new BaseColor(52, 73, 94));
            sectionFont = new Font(bfBold, 13, Font.NORMAL, new BaseColor(41, 128, 185));
            subHeaderFont = new Font(bfBold, 11, Font.NORMAL, BaseColor.BLACK);
            normalFont = new Font(bf, 10, Font.NORMAL, new BaseColor(50, 50, 50));
            italicFont = new Font(bfOblique, 9, Font.NORMAL, BaseColor.DARK_GRAY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createPdf(ResumeData data, String outputPath) {
        Document document = new Document(PageSize.A4, 40, 40, 40, 40);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();

            addHeaderAndPhoto(document, data.getPersonalInfo());
            addSummarySection(document, data.getSummary());
            addWorkExperiences(document, data.getExperiences());
            addEducationSection(document, data.getEducations());
            addSkillsSection(document, data.getSkills());

            System.out.println("[BAŞARILI] PDF Özgeçmiş oluşturuldu: " + outputPath);
        } catch (Exception e) {
            System.err.println("[HATA] PDF oluşturulurken bir sorun oluştu: " + e.getMessage());
        } finally {
            if (document.isOpen()) {
                document.close();
            }
        }
    }

    private void addHeaderAndPhoto(Document document, PersonalInfo info) throws DocumentException, IOException {
        PdfPTable headerTable = new PdfPTable(2);
        headerTable.setWidthPercentage(100);
        headerTable.setWidths(new float[]{1.2f, 3.8f});
        headerTable.setSpacingAfter(15f);

        // Sol Hücre: Fotoğraf
        PdfPCell photoCell = new PdfPCell();
        photoCell.setBorder(Rectangle.NO_BORDER);
        ensurePhotoExists(info.getPhotoPath());

        Image profileImg = Image.getInstance(info.getPhotoPath());
        profileImg.scaleToFit(100, 120);
        photoCell.addElement(profileImg);
        headerTable.addCell(photoCell);

        // Sağ Hücre: Kişisel Bilgiler
        PdfPCell infoCell = new PdfPCell();
        infoCell.setBorder(Rectangle.NO_BORDER);
        infoCell.setPaddingLeft(15f);
        infoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        Paragraph namePara = new Paragraph(info.getFullName(), nameFont);
        Paragraph titlePara = new Paragraph(info.getTitle(), titleFont);
        titlePara.setSpacingAfter(8f);

        Paragraph contactPara = new Paragraph();
        contactPara.setFont(normalFont);
        contactPara.add("E-posta: " + info.getEmail() + "\n");
        contactPara.add("Telefon: " + info.getPhone() + "\n");
        contactPara.add("Adres: " + info.getAddress());

        infoCell.addElement(namePara);
        infoCell.addElement(titlePara);
        infoCell.addElement(contactPara);
        headerTable.addCell(infoCell);

        document.add(headerTable);
    }

    private void addSummarySection(Document document, String summary) throws DocumentException {
        addSectionTitle(document, "ÖZET BİLGİLER");
        Paragraph summaryPara = new Paragraph(summary, normalFont);
        summaryPara.setAlignment(Element.ALIGN_JUSTIFIED);
        summaryPara.setSpacingAfter(12f);
        document.add(summaryPara);
    }

    private void addWorkExperiences(Document document, List<WorkExperience> experiences) throws DocumentException {
        addSectionTitle(document, "İŞ DENEYİMLERİ");
        for (WorkExperience exp : experiences) {
            PdfPTable expTable = new PdfPTable(2);
            expTable.setWidthPercentage(100);
            expTable.setWidths(new float[]{3.5f, 1.5f});

            PdfPCell roleCell = new PdfPCell(new Phrase(exp.getPosition() + " - " + exp.getCompanyName(), subHeaderFont));
            roleCell.setBorder(Rectangle.NO_BORDER);

            PdfPCell dateCell = new PdfPCell(new Phrase(exp.getPeriod(), italicFont));
            dateCell.setBorder(Rectangle.NO_BORDER);
            dateCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

            expTable.addCell(roleCell);
            expTable.addCell(dateCell);
            document.add(expTable);

            Paragraph descPara = new Paragraph(exp.getDescription(), normalFont);
            descPara.setSpacingBefore(3f);
            descPara.setSpacingAfter(10f);
            document.add(descPara);
        }
    }

    private void addEducationSection(Document document, List<Education> educations) throws DocumentException {
        addSectionTitle(document, "EĞİTİM BİLGİLERİ");
        for (Education edu : educations) {
            Paragraph eduPara = new Paragraph();
            eduPara.add(new Chunk(edu.getSchoolName() + " - " + edu.getDepartment() + "\n", subHeaderFont));
            eduPara.add(new Chunk("Mezuniyet: " + edu.getGraduationYear() + " | Genel Not Ortalaması (GANO): " + edu.getGpa(), normalFont));
            eduPara.setSpacingAfter(10f);
            document.add(eduPara);
        }
    }

    private void addSkillsSection(Document document, List<String> skills) throws DocumentException {
        addSectionTitle(document, "TEKNİK YETKİNLİKLER");
        String joinedSkills = String.join("  •  ", skills);
        Paragraph skillsPara = new Paragraph(joinedSkills, normalFont);
        skillsPara.setSpacingBefore(4f);
        document.add(skillsPara);
    }

    private void addSectionTitle(Document document, String title) throws DocumentException {
        Paragraph sectionPara = new Paragraph(title, sectionFont);
        sectionPara.setSpacingBefore(6f);
        sectionPara.setSpacingAfter(4f);
        document.add(sectionPara);

        LineSeparator line = new LineSeparator(1f, 100f, new BaseColor(41, 128, 185), Element.ALIGN_CENTER, -2);
        document.add(new Chunk(line));
    }

    // Eğer profile.jpg dosyası dizinde yoksa otomatik temsili bir fotoğraf üretir
    private void ensurePhotoExists(String photoPath) throws IOException {
        File file = new File(photoPath);
        if (!file.exists()) {
            BufferedImage img = new BufferedImage(200, 240, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = img.createGraphics();
            g2d.setColor(new Color(44, 62, 80));
            g2d.fillRect(0, 0, 200, 240);
            g2d.setColor(new Color(236, 240, 241));
            g2d.fillOval(60, 45, 80, 80); // Baş figürü
            g2d.fillArc(30, 140, 140, 120, 0, 180); // Omuz figürü
            g2d.dispose();
            ImageIO.write(img, "jpg", file);
        }
    }
}