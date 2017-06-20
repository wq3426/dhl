package com.souvi.common;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class GlobalProperties {

private static final Logger logger = LoggerFactory.getLogger(GlobalProperties.class);
	
	public static String SKU_IMPORT_TEMPLATE_PATH;
	public static String SKU_IMPORT_FILE_PATH;
	public static String EDILOG_IMPORT_SRCFILE_PATH;
	public static String EDILOG_IMPORT_DESTFILE_PATH;
	public static String EDITEXTTASK_IMPORT_SRCFILE_PATH;
	
	static{
		logger.info("Loading System Properties...start");
		String _propertiesName = "/flmsConfig.properties";
		Resource resource = new ClassPathResource(_propertiesName);
		Properties props = null;
		try {
			props = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			logger.error("Loading System Properties Error",e);
		}
		
		SKU_IMPORT_TEMPLATE_PATH = props.getProperty("sku_import_template_path");
		SKU_IMPORT_FILE_PATH = props.getProperty("sku_import_file_path");
		EDILOG_IMPORT_SRCFILE_PATH=props.getProperty("ediLog_import_srcfile_path"); 
		EDILOG_IMPORT_DESTFILE_PATH=props.getProperty("ediLog_import_destfile_path");
		EDITEXTTASK_IMPORT_SRCFILE_PATH=props.getProperty("ediTextTask_import_srcfile_path");
		logger.info("Loading System Properties...end");
	}

}
