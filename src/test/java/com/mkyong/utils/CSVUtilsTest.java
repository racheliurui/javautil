package com.mkyong.utils;

import javautil.excel.xlsx.XlsxHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.json.JSONObject;
import org.junit.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by liur9 on 28/06/2017.
 */
public class CSVUtilsTest {

    @Test
    public void WriteCSV() throws IOException {

        FileWriter writer = new FileWriter("target/CSVUtilsTest1.csv");
        for (int i = 0; i < 100; i++) {
            CSVUtils.writeLine(writer, Arrays.asList("line", (new Integer(i)).toString(), "end"));
        }

        writer.flush();
        writer.close();

    }
}
