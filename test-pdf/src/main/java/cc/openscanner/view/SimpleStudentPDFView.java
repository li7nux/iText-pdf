package cc.openscanner.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

import cc.openscanner.bean.Student;

public class SimpleStudentPDFView extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model,
			Document document, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Student student = (Student) model.get("stud");

		Image image = Image.getInstance("/background.jpg");
		image.setAlignment(Image.UNDERLYING);
		
		Table table = new Table(2);
		table.addCell("First Name");
		table.addCell("Last Name");

		table.addCell(student.getFirstName());
		table.addCell(student.getLastName());

		document.add(image);
		document.add(table);
	}
}
