package com.authority.common.utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.contrib.HSSFCellUtil;
import org.apache.poi.hssf.util.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PoiHelper {
	private static final Logger logger = LoggerFactory
			.getLogger(PoiHelper.class);

	public void setRegionStyle(HSSFSheet sheet, Region region, HSSFCellStyle cs) {
		int toprowNum = region.getRowFrom();
		for (int i = region.getRowFrom(); i <= region.getRowTo(); i++) {
			HSSFRow row = HSSFCellUtil.getRow(i, sheet);
			for (int j = region.getColumnFrom(); j <= region.getColumnTo(); j++) {
				HSSFCell cell = HSSFCellUtil.getCell(row, (short) j);
				cell.setCellStyle(cs);
			}

		}

	}

	public static void Excel_pro(List<Map<String, Object>> list,
			String[] col_id, String[] col_name, String fileName) {
		try {
			HttpServletResponse response = null;

			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("sheet");
			// 普通样式
			HSSFCellStyle Style_default = wb.createCellStyle();
			Style_default.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			Style_default.setAlignment(HSSFCellStyle.ALIGN_LEFT);
			// 设置边框
			Style_default.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			Style_default.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			Style_default.setBorderRight(HSSFCellStyle.BORDER_THIN);
			Style_default.setBorderTop(HSSFCellStyle.BORDER_THIN);
			// 自动换行
			Style_default.setWrapText(true);
			// 字体设置
			HSSFFont font_default = wb.createFont();
			font_default.setFontHeightInPoints((short) 10);
			font_default.setFontName("宋体");
			// 打印设置
			HSSFPrintSetup ps = sheet.getPrintSetup();
			ps.setLandscape(false); // 打印方向，true:横向，false:纵向
			ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE); // 纸张
			sheet.setMargin(HSSFSheet.BottomMargin, (double) 0.3); // 页边距（下）
			sheet.setMargin(HSSFSheet.LeftMargin, (double) 0.3); // 页边距（左）
			sheet.setMargin(HSSFSheet.RightMargin, (double) 0.3); // 页边距（右）
			sheet.setMargin(HSSFSheet.TopMargin, (double) 0.3); // 页边距（上）
			sheet.setHorizontallyCenter(true); // 设置打印页面为水平居中

			Style_default.setFont(font_default);

			if (1 > 0) {

				HSSFRow row = sheet.createRow(0);
				// 标题区
				for (int i = 0; i < col_name.length; i++) {
					HSSFCell cell = row.createCell(i);
					cell.setCellValue(new HSSFRichTextString(col_name[i]));
					cell.setCellStyle(Style_default);
				}
			}
			// 数据区
			int num = 1; // 序号
			int row_num = 1;

			for (Map<String, Object> map : list) {

				HSSFRow row = sheet.createRow(row_num);

				for (int i = 0; i < col_id.length; i++) {

					HSSFCell cell = row.createCell(i);

					if (col_id[i].equals("NUM"))
						cell.setCellValue(new HSSFRichTextString(String
								.valueOf(num)));
					else {

						cell.setCellValue(new HSSFRichTextString(
								StringprocessHelper.String_html(String
										.valueOf(map.get(col_id[i]
												.toLowerCase())))));
					}
					cell.setCellStyle(Style_default);
				}
				num++;
				row_num++;
			}

			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(fileName.getBytes("GBK"), "ISO8859_1"));// 下载方式，下载，不用浏览器打开
			response.setContentType("application/vnd.ms-excel");

			ServletOutputStream out = response.getOutputStream();

			wb.write(out);
			out.flush();
			out.close();

		} catch (Exception e) {
			logger.error("Exception: ", e);
		}

	}
}
