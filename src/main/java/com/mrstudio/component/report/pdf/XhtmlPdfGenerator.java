/**
 * Copyright &copy; 2014 Homesoft All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.mrstudio.component.report.pdf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.URL;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 * @author Jawf Can Lee
 *
 */
public class XhtmlPdfGenerator {
	private static Logger log = LoggerFactory.getLogger(XhtmlPdfGenerator.class);
	
	private static XhtmlPdfGenerator instance = null;
	
	private static FreeMarkerConfigurationFactory fmConfigFactory;
	private static Configuration fmConfig;
	
	
	public static XhtmlPdfGenerator getInstance(){
		if (instance ==null){
			init();
		}
		return instance;
	}
	
	protected static void init(){
		instance = new XhtmlPdfGenerator();
		fmConfigFactory = new FreeMarkerConfigurationFactory();
		try {
			fmConfig = fmConfigFactory.createConfiguration();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (TemplateException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public void generatePdfToPath(String templatePath, Map<String, Object> model, String outputFilePath) throws FileNotFoundException, IOException, DocumentException, TemplateException {
		File file = new File(outputFilePath);
		File parentFile = file.getParentFile();
		if (!parentFile.exists()) {
			boolean parentDir = parentFile.mkdirs();
			if (!parentDir) {
				log.debug("generatePdfToPath create parentDir=false");
			}
		}
		
		ITextRenderer renderer = generatePdf(templatePath, model);
		
		OutputStream os = new FileOutputStream(outputFilePath); 
		renderer.createPDF(os, true);
		os.close();
		
		log.debug("Generate Pdf to File Successfully.");
	}
	
	public InputStream generatePdfToInputStream(String templatePath, Map<String, Object> model) throws FileNotFoundException, IOException, DocumentException, TemplateException {
		ITextRenderer renderer = generatePdf(templatePath, model);
		
		ByteArrayOutputStream pdfOutS = new ByteArrayOutputStream();  
		renderer.createPDF(pdfOutS, true);
        InputStream inputStream = new ByteArrayInputStream(pdfOutS.toByteArray());
        pdfOutS.close();
        
        log.debug("Generate Pdf as InputStream Successfully.");
        
        return inputStream;
	}
	
	private ITextRenderer generatePdf(String templatePath, Map<String, Object> model) throws FileNotFoundException, IOException, DocumentException, TemplateException {
		if (templatePath ==null || "".equals(templatePath) || model ==null){
			return null;
		}
        
		fmConfig.setDefaultEncoding("UTF-8");
		fmConfig.setClassForTemplateLoading(this.getClass(), "/");
		String resourcePath = getResourcePath();
		fmConfig.setSharedVariable("base", resourcePath);
        Template template = fmConfig.getTemplate(templatePath);
        
		StringWriter result = new StringWriter();
		template.process(model, result);
		String content = result.toString();
		//String content = FreeMarkers.renderTemplate(template, model);//<#assign fmt=JspTaglibs["/WEB-INF/tlds/fmt.tld"]><@fmt.message key="label.fn.compnt.name"/> 
		
        ITextRenderer renderer = new ITextRenderer();
 
        ITextFontResolver fontResolver = renderer.getFontResolver(); 
        fontResolver.addFont(resourcePath.concat("/report/resource/font/MSYH.TTF"), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED); 
        fontResolver.addFont(resourcePath.concat("/report/resource/font/ARIAL.TTF"), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED); 
        
        //Image Path
        //renderer.getSharedContext().setBaseURL(resourcePath.concat("/report/resource/image")); 
        //renderer.getSharedContext().setUserAgentCallback(new HttpURLUserAgent(renderer.getOutputDevice()));  

        renderer.setDocumentFromString(content);
        renderer.layout(); 
        log.debug("Generate Pdf as ITextRenderer Successfully.");
        return renderer;
	}

	public String getResourcePath() {
		String resourcePath = null;
		URL url = Thread.currentThread().getContextClassLoader().getResource("/");
		if (url!=null){
			resourcePath = url.getPath();
		} else {
			url = Thread.currentThread().getClass().getResource("/");
		}
		resourcePath = url.getPath();
		return resourcePath;
	} 
}  