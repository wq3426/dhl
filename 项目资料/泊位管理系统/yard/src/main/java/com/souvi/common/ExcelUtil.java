package com.souvi.common;

import java.io.OutputStream;
import java.util.List;

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

public class ExcelUtil {

	/**
	 * 导出excel
	 */
	public static void exportExcel(OutputStream out, List<String> titleList, List<List<String>> contentList)
			throws Exception {
		try {
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet("sheet1");

			for (int i = 0; i < titleList.size(); i++) {
				sheet.setColumnWidth(i, 256 * 20);
			}

			XSSFCell xssfCell = null;
			XSSFCellStyle style = null;
			XSSFCellStyle styleContent = (XSSFCellStyle) wb.createCellStyle();
			int rowIndex = 0;

			// 标题行
			XSSFRow titleRow = sheet.createRow(rowIndex++);
			style = createTitleStyle(wb, 10);

			for (int i = 0; i < titleList.size(); i++) {
				xssfCell = titleRow.createCell(i);
				xssfCell.setCellStyle(style);
				xssfCell.setCellValue(titleList.get(i));
			}

			// 内容
			if (null != contentList && contentList.size() > 0) {
				styleContent = createContentStyle(wb, 10);
				for (int i = 0; i < contentList.size(); i++) {
					XSSFRow filedRow = sheet.createRow(i + rowIndex);
					for (int j = 0; j < contentList.get(i).size(); j++) {
						xssfCell = filedRow.createCell(j);
						String value = contentList.get(i).get(j);
						xssfCell.setCellValue(null == value ? "" : value);
						xssfCell.setCellStyle(styleContent);
					}
				}
			}
			wb.write(out);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new Exception(e);
		} finally{
			out.flush();
			out.close();
		}
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
}
