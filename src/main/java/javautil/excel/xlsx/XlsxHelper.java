package javautil.excel.xlsx;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//https://www.mkyong.com/java/apache-poi-reading-and-writing-excel-file-in-java/
//https://mvnrepository.com/artifact/org.apache.poi/poi/3.16
public class XlsxHelper {

	public Workbook workbook;

	public static void createXlsx(String filePath)
			throws EncryptedDocumentException, InvalidFormatException, FileNotFoundException, IOException {
		Workbook wb = new XSSFWorkbook(); // or new HSSFWorkbook();
		FileOutputStream fileOut = new FileOutputStream(filePath);
		@SuppressWarnings("unused")
		Sheet sheet = wb.createSheet("Sheet1");
		wb.write(fileOut);
		wb.close();
		fileOut.close();
	}

	public XlsxHelper(String filePath) throws IOException {
		InputStream inputStream = new FileInputStream(filePath);
		workbook = new XSSFWorkbook(inputStream);

	}

	public void saveWorkbook(String filePath) throws IOException {

		FileOutputStream fileOut = new FileOutputStream(filePath);
		workbook.write(fileOut);
		fileOut.close();
	}

	/***
	 * write JSON Array data into spreadsheet, each object will be write to one
	 * row The key will write to the header for example [
	 * {"id":"1","name":"rachel","address":"hawk", "phone": 134},
	 * {"id":"2","name":"max","address":"hawk1", "phone": 234},
	 * {"id":"2","name":"raymond", "phone": 234} ] then, id, name, address,
	 * phone will be used as header and values fill in as string
	 */
	public void writeJSONArrayToSheet(Sheet sheet, JSONArray data, int startingRow) throws IOException {
		Row headerRow = sheet.createRow(startingRow);
		for (int i = 0; i < data.length(); i++) {
			JSONObject currentObj = data.optJSONObject(i);
			Row currentRow = sheet.createRow(startingRow + i + 1);

			writeRow(currentObj, currentRow, headerRow);
		}
	}

	/**
	 * write a single JSONObject into spreadsheet numeric values will remain the
	 * format
	 **/
	protected void writeRow(JSONObject obj, Row row, Row headerRow) {

		Iterator<String> keys = obj.keys();

		while (keys.hasNext()) {
			// check if key exist in header
			String key = keys.next();
			int colNum = this.returnColNum(key, headerRow);
			// not found
			if (colNum == -1) {
				colNum = this.appendRowEnd(key, headerRow);
			}

			row.createCell(colNum).setCellValue(obj.optString(key).toString());
		}

	}

	/**
	 * find out the col num with has the same value matched with given value
	 * 
	 * return -1 if no match
	 **/

	private int returnColNum(String value, Row row) {

		Iterator<Cell> cells = row.iterator();
		while (cells.hasNext()) {

			Cell cell = cells.next();
			if (value.equalsIgnoreCase(this.getCellStringValue(cell)))
				return cell.getColumnIndex();
		}

		return -1;
	}

	/**
	 * write the value to end of the row and return the written col num
	 * 
	 **/
	private int appendRowEnd(String value, Row row) {
		int col = row.getLastCellNum();
		if (col ==-1)
			col =0;
		Cell newcell = row.createCell(col);
		newcell.setCellValue(value);
		return col;
	}

	/***
	 * get JSON Array data from sheet, each object will be read from one row
	 * headerRow is the row to read the labels for json for example [
	 * {"id":"1","name":"rachel","address":"hawk", "phone": 134},
	 * {"id":"2","name":"max","address":"hawk1", "phone": 234},
	 * {"id":"3","name":"raymond","address":"", "phone": 234} ] then, id, name,
	 * address, phone will be the value header and values fill in as string
	 */
	public JSONArray getSheetAsJsonArray(String sheetName, int headerRow, int startRow, int endRow)
			throws JSONException {
		JSONArray array = new JSONArray();
		Sheet sheet = workbook.getSheet(sheetName);
		List<String> headers = getRowValueList(headerRow, sheet);
		List<List<String>> valueMatrix = getStringValuesMatrixFromSheet(startRow, endRow, sheet);

		for (int i = 0; i < valueMatrix.size(); i++) {
			JSONObject currentRowObj = new JSONObject();

			for (int j = 0; j < headers.size(); j++) {
				if (headers.get(j).length() > 0) {
					String value = "";
					if( valueMatrix.get(i).size()>j && valueMatrix.get(i).get(j).length() > 0){
						value= valueMatrix.get(i).get(j);
					}
					currentRowObj.put(headers.get(j), value);
				}
			}

			array.put(currentRowObj);
		}
		return array;
	}

	/**
	 * from startRow, get mitrax value as string from spreadsheet
	 **/
	protected List<List<String>> getStringValuesMatrixFromSheet(int startRow, int endRow, Sheet sheet) {
		List<List<String>> result = new ArrayList<List<String>>();

		Iterator<Row> iterator = sheet.iterator();
		while (iterator.hasNext()) {
			Row nextRow = iterator.next();
			if (nextRow.getRowNum() >= startRow && nextRow.getRowNum() <= endRow)
				result.add(getRowValueList(nextRow));
		}

		return result;
	}

	/**
	 * given sheet and row number, get list of values putting using column
	 * number as index
	 */
	protected List<String> getRowValueList(int rowNum, Sheet sheet) {
		return getRowValueList(sheet.getRow(rowNum));
	}

	protected List<String> getRowValueList(Row row) {
		List<String> values = new ArrayList<String>();
		if (row == null)
			return null;
		Iterator<Cell> cellIterator = row.cellIterator();
        int col=0;
		while (cellIterator.hasNext()) {
			Cell cell = cellIterator.next();
			values.add(col, getCellStringValue(cell));
			col++;		

		}
		return values;
	}

	
	
	
	/**
	 * return cell's string value; ignore the value type
	 * 
	 **/
	@SuppressWarnings("deprecation")
	private String getCellStringValue(Cell cell) {
		String cellStringValue = "";
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			cellStringValue = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			cellStringValue = cell.getBooleanCellValue() ? "true" : "false";
			break;
		case Cell.CELL_TYPE_NUMERIC:
			cellStringValue = new Double(cell.getNumericCellValue()).toString();
			break;
		default:
			cellStringValue = "";
		}

		return cellStringValue;
	}

	
}