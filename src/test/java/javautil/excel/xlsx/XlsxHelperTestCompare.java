package javautil.excel.xlsx;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.junit.BeforeClass;

import org.junit.Test;

public class XlsxHelperTestCompare {

	static String path = "C:\\Users\\RachelRuiLiu\\Downloads\\GACanalysis Requirments Revised 04042019.xlsx";
	// static String path = "target/test.xlsx";

	@Test
	public void TestGetRowValueList() throws IOException, JSONException {

		XlsxHelper helper = new XlsxHelper(path);

		int currentRow = 0;
		List<String> finalList = new ArrayList<String>();
		List<String> currentList = null;
		Sheet sheet = helper.workbook.getSheet("Sample Analysis");

		while (helper.getRowValueList(currentRow, sheet) != null) {
			currentList = helper.getRowValueList(currentRow, sheet);
			if (currentList.contains("Reportable element name") || currentList.contains("Reportable elemnt name")) {
				// System.out.println(currentList.toString());
				for (Iterator<String> i = currentList.iterator(); i.hasNext();) {
					String item = i.next();
					if (!finalList.contains(item))
						finalList.add(item);
				}

			}
			currentRow++;
			// System.out.println(currentRow);
			currentList = null;
		}

		List<Object> sortedNames = finalList.stream().sorted().collect(Collectors.toList());
		for (Iterator<Object> i = sortedNames.iterator(); i.hasNext();) {

			//System.out.println(i.next());
			System.out.println(i.next().toString().replaceAll("\\s", ""));
		}



//		System.out.println(finalList.toString());

	}

}
