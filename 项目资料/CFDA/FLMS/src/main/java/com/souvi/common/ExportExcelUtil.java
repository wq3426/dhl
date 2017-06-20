package com.souvi.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

// 读取Excel

public class ExportExcelUtil {

	/**
	 * 导出excel
	 */
	public static void exportExcel(OutputStream out, List<String> titleArray, List<List<String>> filedArray)
			throws Exception {

		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("sheet1");

		for (int i = 0; i < titleArray.size(); i++) {
			sheet.setColumnWidth(i, 256 * 20);
		}

		XSSFCell xssfCell = null;
		XSSFCellStyle style = null;
		XSSFCellStyle styleContent = (XSSFCellStyle) wb.createCellStyle();
		int rowIndex = 0;

		// 标题行
		XSSFRow titleRow = sheet.createRow(rowIndex++);
		style = createTitleStyle(wb, 10);

		for (int i = 0; i < titleArray.size(); i++) {
			xssfCell = titleRow.createCell(i);
			xssfCell.setCellStyle(style);
			xssfCell.setCellValue(titleArray.get(i));
		}

		// 内容
		if (null != filedArray && filedArray.size() > 0) {
			styleContent = createContentStyle(wb, 10);
			for (int i = 0; i < filedArray.size(); i++) {
				XSSFRow filedRow = sheet.createRow(i + rowIndex);
				for (int j = 0; j < filedArray.get(i).size(); j++) {
					xssfCell = filedRow.createCell(j);
					String value = filedArray.get(i).get(j);
					xssfCell.setCellValue(null == value ? "" : value);
					xssfCell.setCellStyle(styleContent);
				}
			}
		}
		wb.write(out);
	}

	private static XSSFCellStyle createTitleStyle(XSSFWorkbook wb, int fontSize) {
		XSSFCellStyle style = wb.createCellStyle();

		XSSFFont font = wb.createFont();// 创建字体对象
		font.setFontHeightInPoints((short) fontSize);// 设置字体大小
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 设置粗体

		style.setFont(font);// 将字体加入到样式对象
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER_SELECTION);
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		style.setWrapText(true);

		setBorder(style);

		return style;
	}

	private static XSSFCellStyle createContentStyle(XSSFWorkbook wb, int fontSize) {
		XSSFCellStyle style = wb.createCellStyle();

		XSSFFont font = wb.createFont();// 创建字体对象
		font.setFontHeightInPoints((short) fontSize);// 设置字体大小

		style.setFont(font);// 将字体加入到样式对象
		style.setAlignment(XSSFCellStyle.ALIGN_CENTER_SELECTION);
		style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		style.setWrapText(false);

		setBorder(style);

		return style;
	}

	// 设备边框
	private static void setBorder(XSSFCellStyle style) {
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
	}

	/**
	 * 
	 * 去掉字符串右边的空格
	 * 
	 * @param str
	 *            要处理的字符串
	 * 
	 * @return 处理后的字符串
	 * 
	 */

	public static String rightTrim(String str) {
		if (str == null) {
			return "";
		}
		str = str.replaceAll("\u00A0", " ");
		int length = str.length();
		for (int i = length - 1; i >= 0; i--) {
			if (str.charAt(i) != 0x20) {
				break;
			}
			length--;
		}
		return str.substring(0, length);
	}

	/**
	 * 去掉字符串左边的空格
	 * 
	 * @param str
	 *            要处理的字符串
	 * 
	 * @return 处理后的字符串
	 */

	public static String leftTrim(String str) {
		if (str == null || "".equalsIgnoreCase(str)) {
			return "";
		}
		str = str.replaceAll("\u00A0", " ");
		int length = str.length();
		int laa = 0;
		for (int i = 0; i < length; i++) {
			if (str.charAt(i) != 0x20) {
				break;
			}
			laa++;
		}
		return str.substring(laa, length);
	}

	// 去掉sql decimal数据类型后面多余的零
	public static String removeZeroFromDecimal(String data) {
		data = data == null ? "" : data;
		int iflag = 0;
		if (!"".equalsIgnoreCase(data) && data.indexOf(".") > 0) {
			for (int i = data.length() - 1; i > 0; i--) {
				char c = data.charAt(i);
				if (c == '0') {
					iflag += 1;
				} else if (c == '.') {
					iflag += 1;
					break;
				} else {
					break;
				}
			}
			data = data.substring(0, data.length() - iflag);
		}
		return data;
	}
	/**
	 * 文件下载
	 * @throws IOException 
	 */
	public void downLoadFile(HttpServletRequest request,HttpServletResponse response,String filePath) throws IOException  {
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName="+ URLEncoder.encode(indexOfEnd(filePath, "/"), "utf-8"));
		File file = new File(filePath);
		if (file.exists()) {
			InputStream inputStream = new FileInputStream(file);

			OutputStream os = response.getOutputStream();
			byte[] b = new byte[2048];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}

			 // 这里主要关闭。
			os.close();
			inputStream.close();
		}
	}
	public String indexOfEnd(String str,String toStr){
		str = str.replaceAll("\\\\", "/");
		return str.substring(str.lastIndexOf(toStr)+1, str.length());
	}
}
