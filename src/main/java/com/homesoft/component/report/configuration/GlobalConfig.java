/**
 * Copyright &copy; 2014 Homesoft All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.homesoft.component.report.configuration;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * 
 * @author Jawf Can Lee
 *
 */
public class GlobalConfig {
	private static GlobalConfig instance = new GlobalConfig();

	private Configuration config = null;
	
	public static GlobalConfig getInstance() {
		return instance;
	}

	public GlobalConfig() {
		try {
			config = new PropertiesConfiguration("report-config.properties");
		} catch (Exception e) {
			throw new IllegalStateException("configuration error", e);
		}
	}

	public boolean isProductionMode() {
		return config.getBoolean("isProductionMode", false);
	}
	
	public boolean getStamperImageEnabled() {
		return config.getBoolean("report.stamper.image.enable", true);
	}
	public String getStamperImagePath() {
		return config.getString("report.stamper.image.path");
	}
	public int getStamperImageWidth() {
		return config.getInt("report.stamper.image.width", 165);
	}
	public int getStamperImageHeight() {
		return config.getInt("report.stamper.image.height", 31);
	}
	public boolean getStamperFixedImageEnabled() {
		return config.getBoolean("report.stamper.image.fixed.enable", true);
	}
	public float getStamperFixedImageTransparent() {
		return config.getFloat("report.stamper.image.fixed.transparent", 0.8f);
	}
	public int getStamperFixedImageRotationDegree() {
		return config.getInt("report.stamper.image.fixed.rotationDegree", 0);
	}
	public int getStamperFixedImagePositionX() {
		return config.getInt("report.stamper.image.fixed.position.x", 30);
	}
	public int getStamperFixedImagePositionY() {
		return config.getInt("report.stamper.image.fixed.position.y", 8);
	}
	
	public float getStamperDynamicImageTransparent() {
		return config.getFloat("report.stamper.image.dynamic.transparent", 0.3f);
	}
	public boolean getStamperDynamicImageEnabled() {
		return config.getBoolean("report.stamper.image.dynamic.enable", true);
	}
	public int getStamperDynamicImageRotationDegree() {
		return config.getInt("report.stamper.image.dynamic.rotationDegree", 0);
	}
	public int getStamperDynamicImagePositionXStart() {
		return config.getInt("report.stamper.image.dynamic.position.x.start", 30);
	}
	public int getStamperDynamicImagePositionYStart() {
		return config.getInt("report.stamper.image.dynamic.position.y.start", 8);
	}
	public boolean getStamperDynamicPositionXRepeat() {
		return config.getBoolean("report.stamper.image.dynamic.position.x.repeat", false);
	}
	public boolean getStamperDynamicPositionYRepeat() {
		return config.getBoolean("report.stamper.image.dynamic.position.y.repeat", true);
	}
	public int getStamperDynamicImagePositionXStep() {
		return config.getInt("report.stamper.image.dynamic.position.x.step", 0);
	}
	public int getStamperDynamicImagePositionYStep() {
		return config.getInt("report.stamper.image.dynamic.position.y.step", 0);
	}
	
}
