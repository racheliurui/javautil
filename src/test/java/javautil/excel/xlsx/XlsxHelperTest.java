package javautil.excel.xlsx;

import java.io.File;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.junit.BeforeClass;

import org.junit.Test;

public class XlsxHelperTest {
	static String path = "C:\\Users\\Public\\Documents\\test.xlsx";

	@BeforeClass
	public static void TestCreateXlsxFile() throws IOException, EncryptedDocumentException, InvalidFormatException {

		File f = new File(path);
		if (f.exists() && !f.isDirectory()) {
			if (f.delete())
				System.out.println(path + "   deleted !");
		}
		XlsxHelper.createXlsx(path);

		System.out.println(path + "   created !");

	}

	@Test
	public void TestWriteRow() throws IOException {

		XlsxHelper helper = new XlsxHelper(path);
		Sheet sheet = helper.workbook.createSheet("SheetWriteRow");
		Row header = sheet.createRow(0);
		Row current = sheet.createRow(1);
		helper.writeRow(new JSONObject("{'id':'1','name':'rachel'}"), current, header);

		helper.saveWorkbook(path);
	}

	@Test
	public void TestWriteJSONArrayToSheet() throws IOException {
		XlsxHelper helper = new XlsxHelper(path);
		Sheet sheet = helper.workbook.getSheet("Sheet1");

		JSONArray data = new JSONArray("[" + "{'id':'1','name':'rachel', 'phone': 134},"
				+ "{'id':'2','name':'rachel2','address':'hawk', 'phone': 134},"
				+ "{'id':'3','name':'rachel3','phone': 134}]");
		helper.writeJSONArrayToSheet(sheet, data, 0);
		helper.saveWorkbook(path);
	}

	@Test
	public void TestGetRowValueList() throws IOException, JSONException {

		XlsxHelper helper = new XlsxHelper(path);
		if (helper.getRowValueList(0, helper.workbook.getSheet("Sheet1")) != null)
			System.out.println(helper.getRowValueList(0, helper.workbook.getSheet("Sheet1")).toString());

	}

	@Test
	public void TestGetSheetAsJsonArray() throws IOException {

		this.TestWriteJSONArrayToSheet();
		//System.out.println("TestGetSheetAsJsonArray");
		XlsxHelper helper = new XlsxHelper(path);
		System.out.println(helper.getStringValuesMatrixFromSheet(0, 4, helper.workbook.getSheet("Sheet1")).toString());

		System.out.println(helper.getSheetAsJsonArray("Sheet1", 0, 1, 3).toString());

	}

}
