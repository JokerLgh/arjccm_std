package com.arjjs.ccm.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;

public class WordUtils {

	/**
	 * 方法说明：单元格内添加一行
	 * @param cell
	 * @param content
	 * @param bfChinese
	 * @param font
	 * @param location
	 */
	public static void addLineInCell(Cell cell,String content,BaseFont bfChinese,Font font,int location){
		Paragraph paragraph = new Paragraph(content);
		paragraph.setFont(font);
		paragraph.setAlignment(location);
		cell.add(paragraph);
	}

	/**
	 * 方法说明：添加一行
	 * @param document
	 * @param content
	 * @param bfChinese
	 * @param font
	 * @param location
	 * @throws DocumentException
	 */
	public static void addLine(Document document, String content,BaseFont bfChinese,Font font,int location) throws DocumentException{
		Paragraph paragraph = new Paragraph(content);
		paragraph.setFont(font);
		paragraph.setAlignment(location);
		document.add(paragraph);
	}

	/**
	 * 方法说明：一行添加一张图片
	 * @param URL
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public static Table ONEPNG(String URL) throws Exception {
		Table table = new Table(1);
		//表格的主体
		table.setAutoFillEmptyCells(true);
		table.setBorder(0);
		table.disableBorderSide(0);
		table.setBorderWidth(0);
		int width[] = {100};//设置每列宽度比例
		table.setWidths(width);
		table.setWidth(100);
		String suffix = null;
		Cell cell = new Cell();
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);
		String picName = URL;
		suffix = picName.substring(picName.lastIndexOf("."));
		Image png = Image.getInstance(picName);
		png.scaleAbsolute(200f, 200f);
		cell.add(png);
		return table;
	}

	/**
	 * 文件转存（不修改文件名）
	 * @param path
	 * @param name
	 * @param targetPath
	 * @return
	 */
	public static String unloadingFile_New(String path,String name,String targetPath){
		if (path == null) {
			return null;
		}
		File f=new File(path);
		if(f.exists()) {
			File fileNewPatn = new File(targetPath+"/"+name);
			//判断服务器文件夹是否存在，如果不存则创建目录
			File realFilePath = new File(targetPath);
			if (!realFilePath.exists()) {
				realFilePath.mkdirs();//创建目录
			}
			try {
				FileOutputStream fos = new FileOutputStream(fileNewPatn);//新文件路径
				FileInputStream fis = new FileInputStream(path);//要转存的文件路径
				byte[] buf = new byte[1024];
				int len = 0;
				while((len=fis.read(buf))!=-1)
				{
					fos.write(buf,0,len);
				}
				fos.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("复制文件失败");
			}
			return name;
		}
		return null;
	}

	/**
	 * 方法说明：转化时间，时间为null时转换成空字符
	 * @param date
	 * @return
	 */
	public static String DateUtils(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateTime = "";
		if(date != null) {
			dateTime = dateFormat.format(date);
		}else {
			dateTime = " ";
		}
		return dateTime;
	}
}