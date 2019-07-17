package com.mrstudio.component.report.pdf;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.lowagie.text.DocumentException;
import com.mrstudio.component.report.pdf.PdfWrappedStamper;
import com.mrstudio.component.report.pdf.XhtmlPdfGenerator;

import freemarker.template.TemplateException;

public class PdfWrappedStamperTest {

	@Test
	public void testStampImage() {
		Map<String, Object> model = new HashMap<String, Object>();
		
		try {
			InputStream is = XhtmlPdfGenerator.getInstance().generatePdfToInputStream("report/template/test.ftl", model);
			PdfWrappedStamper.getInstance().stampToPath(is, "d:/test_.pdf");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}

}
