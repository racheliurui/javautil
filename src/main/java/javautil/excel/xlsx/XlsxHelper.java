package javautil.excel.xlsx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * http://www.codejava.net/coding/how-to-read-excel-files-in-java-using-apache-poi
 * A dirty simple program that reads an Excel file.
 * 
 * @author www.codejava.net
 *
 */
public class XlsxHelper {
	
	Workbook workbook;
	
	public XlsxHelper (String xlsxFilePath) throws IOException{
		
		FileInputStream inputStream = new FileInputStream(new File(xlsxFilePath));
		this.workbook = new XSSFWorkbook(inputStream);
	}
	
	/**
	 * given sheet and row number, get list of values 
	 * */
	private getRowHeaderList(int headerRowNum, Sheet sheet){
		
		
	}

	public static void main(String[] args) throws IOException {
		String excelFilePath = "Books.xlsx";
		FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();

		while (iterator.hasNext()) {
			Row nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();

			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();

				switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					System.out.print(cell.getStringCellValue());
					break;
				case Cell.CELL_TYPE_BOOLEAN:
					System.out.print(cell.getBooleanCellValue());
					break;
				case Cell.CELL_TYPE_NUMERIC:
					System.out.print(cell.getNumericCellValue());
					break;
				}
				System.out.print(" - ");
			}
			System.out.println();
		}

		workbook.close();
		inputStream.close();
	}

}
