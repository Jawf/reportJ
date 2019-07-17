/**
 * Copyright &copy; 2014 Homesoft All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.mrstudio.component.report.pdf;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.mrstudio.component.report.configuration.GlobalConfig;

/**
 * 
 * @author Jawf Can Lee
 *
 */
public class PdfWrappedStamper {
	private static Logger log = LoggerFactory.getLogger(PdfWrappedStamper.class);
	
	private static PdfWrappedStamper instance = null;
	private static Boolean imageEnabled = null;
	private static Image image = null;
	private static PdfGState gsFixed = null;
	private static PdfGState gsDynamic = null;
	
	public static PdfWrappedStamper getInstance(){
		if (instance ==null){
			init();  
		}
		return instance;
	}

	public static void init() {
		instance = new PdfWrappedStamper();
		try {
			//png image  
			imageEnabled = GlobalConfig.getInstance().getStamperImageEnabled();
			if (imageEnabled){
				image = Image.getInstance(getResourcePath()+GlobalConfig.getInstance().getStamperImagePath());
				image.setAlignment(Image.LEFT | Image.TEXTWRAP);  
			    image.setBorderWidth(10); 
			    image.scaleToFit(GlobalConfig.getInstance().getStamperImageWidth(), GlobalConfig.getInstance().getStamperImageHeight());//size
				gsFixed = new PdfGState();
				gsDynamic = new PdfGState();
				gsFixed.setFillOpacity(GlobalConfig.getInstance().getStamperFixedImageTransparent());
			    gsDynamic.setFillOpacity(GlobalConfig.getInstance().getStamperDynamicImageTransparent());
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
/*	public ByteArrayOutputStream generateStampedPdfToOutputStream(InputStream inputStream) throws FileNotFoundException, IOException, DocumentException, TemplateModelException {
		ByteArrayOutputStream os = stamp(inputStream);
	    return os;
	}
*/	
	
	
	public void stampToPath(InputStream is, String path) throws IOException {
		ByteArrayOutputStream bos = stamp(is);
		FileOutputStream fos = new FileOutputStream(path);
		fos.write(bos.toByteArray());
		bos.close();
		fos.close();
	}
	
	public ByteArrayOutputStream stamp(InputStream is) {
		return stampInputStream(is);
	}
	
	private ByteArrayOutputStream stampInputStream(InputStream is) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PdfReader reader = null;
		PdfStamper stamp = null;
		try {
			reader = new PdfReader(is);
			stamp = new PdfStamper(reader, os);
		} catch (DocumentException e) {
			log.error(e.getMessage(),e);
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}  

		if (reader==null || stamp==null){
			return null;
		}
	    
	    int total = reader.getNumberOfPages();
		for (int i = 1; i <= total; i++) {
			PdfContentByte over = stamp.getOverContent(i);
			
			boolean fixedImageEnabled = GlobalConfig.getInstance().getStamperFixedImageEnabled();
			boolean dynamicImageEnabled = GlobalConfig.getInstance().getStamperDynamicImageEnabled();
			if (imageEnabled && fixedImageEnabled){
				image.setRotationDegrees(GlobalConfig.getInstance().getStamperFixedImageRotationDegree());
				image.setAbsolutePosition(GlobalConfig.getInstance().getStamperFixedImagePositionX(), GlobalConfig.getInstance().getStamperFixedImagePositionY());// position
				over.setGState(gsFixed);
				try {
					over.addImage(image);
				} catch (DocumentException e) {
					log.error(e.getMessage(), e);
				}
			}
			
			if (imageEnabled && dynamicImageEnabled){
				int jx = GlobalConfig.getInstance().getStamperDynamicImagePositionXStart();
				int jy = GlobalConfig.getInstance().getStamperDynamicImagePositionYStart();
				int imageWidth = GlobalConfig.getInstance().getStamperImageWidth();
				do {
					if (GlobalConfig.getInstance().getStamperDynamicPositionXRepeat()){
						jx += GlobalConfig.getInstance().getStamperDynamicImagePositionXStep();
						jy += GlobalConfig.getInstance().getStamperDynamicImagePositionYStep();
						int stepJx = jx;
						int stepJy = jy;
						jx = jx % imageWidth - imageWidth;
						while (jx < 600) {
							// img.setRotation(30);
							image.setRotationDegrees(GlobalConfig.getInstance().getStamperDynamicImageRotationDegree());
							image.setAbsolutePosition(jx, jy);
							over.setGState(gsDynamic);
							try {
								over.addImage(image);
							} catch (DocumentException e) {
								log.error(e.getMessage(), e);
							}
							jx += (GlobalConfig.getInstance().getStamperDynamicImagePositionXStep() + imageWidth);
							//jy += GlobalConfig.getInstance().getStamperDynamicImagePositionYStep();
						}
						jx = stepJx;
						jy = stepJy;
					} else {
						// img.setRotation(30);
						image.setRotationDegrees(GlobalConfig.getInstance().getStamperDynamicImageRotationDegree());
						image.setAbsolutePosition(jx, jy);
						over.setGState(gsDynamic);
						try {
							over.addImage(image);
						} catch (DocumentException e) {
							log.error(e.getMessage(), e);
						}
						jx += GlobalConfig.getInstance().getStamperDynamicImagePositionXStep();
						jy += GlobalConfig.getInstance().getStamperDynamicImagePositionYStep();
					}
				} while (jy < 800 && GlobalConfig.getInstance().getStamperDynamicPositionYRepeat());
			}
	    }
	    
	    try {
			stamp.close();
		} catch (DocumentException e) {
			log.error(e.getMessage(),e);
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		} 
	    reader.close();
		return os;
	}
	
	public static String getResourcePath() {
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
