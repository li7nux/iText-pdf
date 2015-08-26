package cc.openscanner.view;

import java.awt.Color;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Anchor;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Cell;
import com.lowagie.text.Chapter;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Section;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfAction;
import com.lowagie.text.pdf.PdfDestination;
import com.lowagie.text.pdf.PdfWriter;

import cc.openscanner.bean.Student;

public class RichStudentPDFView extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model,
			Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		document.setPageSize(PageSize.A4);

		Anchor anchorTarget = new Anchor("First page of the document.");
	    anchorTarget.setName("BackToTop");
	    
	    Paragraph paragraph1 = new Paragraph();

	    paragraph1.setSpacingBefore(50);

	    paragraph1.add(anchorTarget);
		
		response.setHeader("Content-Disposition", "inline; filename=" + new String("学生").getBytes() + ".pdf");
		
		Student student = (Student) model.get("stud");

		Table table = new Table(2);
		
//		table.setBackgroundColor(Color.GRAY);
		
		table.addCell("First Name");
		table.addCell("Last Name");

		table.addCell(student.getFirstName());
		table.addCell(student.getLastName());

//		BaseFont cnBaseFont = BaseFont.createFont("STSongStd-Light", "UniGB-USC2-H", false);
		//解决中文字体问题
		BaseFont baseFont = BaseFont.createFont("C:/Windows/Fonts/SIMYOU.TTF",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
		//方法二：使用iTextAsian.jar中的字体    
//		BaseFont baseFont = BaseFont.createFont("STSong-Light",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);    
		
		//方法三：使用资源字体(ClassPath)    
		//BaseFont baseFont = BaseFont.createFont("/SIMYOU.TTF",BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);    
		Font cnFont = new Font(baseFont, 10, Font.NORMAL, Color.BLUE);
		

		table.addCell(buildFontCell(student.getFirstName(), cnFont));
		table.addCell(buildFontCell(student.getLastName(), cnFont));
		
		Paragraph paragraph=new Paragraph();
		paragraph.add("A Paragraph");
		
		Image image = Image.getInstance("/background.jpg");
//		image.setAbsolutePosition(Image.LEFT, Image.TOP);
//		image.scalePercent(1);
		image.setAlignment(Image.UNDERLYING);
		
		Paragraph title1 = new Paragraph("Chapter 1", FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLDITALIC, new CMYKColor(0, 255, 255,17)));
		Chapter chapter1 = new Chapter(title1, 1);
		chapter1.setNumberDepth(0);
		Paragraph title11 = new Paragraph("This is Section 1 in Chapter 1", 
		FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD, new CMYKColor(0, 255, 255,17)));
		Section section1 = chapter1.addSection(title11);
		Paragraph someSectionText = new Paragraph("This text comes as part of section 1 of chapter 1.");
		section1.add(someSectionText);
		someSectionText = new Paragraph("Following is a 3 X 2 table.");
		section1.add(someSectionText);
		Image image2 = Image.getInstance("/wangwa.jpg");
		image2.scaleAbsolute(120f, 120f);
		section1.add(image2);
		
		document.add(image);
		document.add(paragraph);
		document.add(table);
		document.add(paragraph1);
		document.add(section1);
		document.add(chapter1);
		
		document.add(new Paragraph("Some more text on the \first page with different color and font type.", FontFactory.getFont(FontFactory.COURIER, 14, Font.BOLD,	new CMYKColor(0, 255, 0, 0))));
		
		writer.setOpenAction(PdfAction.gotoLocalPage(1, new PdfDestination(
				PdfDestination.XYZ, 0, 10000, 1), writer));
	}

	private Cell buildFontCell(String content, Font font) throws RuntimeException{
		Phrase phrase = new Phrase(content, font);
		try {
			return new Cell(phrase);
		} catch (BadElementException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
