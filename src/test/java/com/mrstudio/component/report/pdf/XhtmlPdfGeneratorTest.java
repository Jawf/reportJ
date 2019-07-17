package com.mrstudio.component.report.pdf;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.lowagie.text.DocumentException;
import com.mrstudio.component.report.pdf.XhtmlPdfGenerator;

import freemarker.template.TemplateException;

public class XhtmlPdfGeneratorTest {

	@Test
	public void testGeneratePdf() {
		Map<String, Object> model = new HashMap<String, Object>();
		
		try {
			XhtmlPdfGenerator.getInstance().generatePdfToPath("report/template/test.ftl", model, "d:/test.pdf");
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
