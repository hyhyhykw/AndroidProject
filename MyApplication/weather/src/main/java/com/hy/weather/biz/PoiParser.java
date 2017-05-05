package com.hy.weather.biz;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.hy.weather.entity.CityInfo;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created Time: 2017/1/25 19:12.
 *
 * @author HY
 */

@SuppressWarnings("deprecation")
public class PoiParser {
    private static final String TAG = PoiParser.class.getSimpleName();
    //    private static final String XLS="xls";
//    private static final String XLSX="xlsx";
    private static final String XLS_FILE = "cities.xls";

    private Handler mHandler;

    public PoiParser(Handler handler) {
        mHandler = handler;
    }

    //
    public List<CityInfo> readExcel(Context context) {
        List<CityInfo> cityInfos = new ArrayList<>();
        InputStream is;
        Workbook workbook;
        try {
            is = context.getAssets().open(XLS_FILE);
            workbook = new HSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);
            int firstRowNum = sheet.getFirstRowNum();
            int lastRowNum = sheet.getLastRowNum();
            // 循环列
            for (int i = firstRowNum + 1; i <= lastRowNum; i++) {
                Row row = sheet.getRow(i);
                if (null == row) continue;

                int firstCellNum = row.getFirstCellNum();
                int lastCellNum = row.getPhysicalNumberOfCells();
                //循环行
                List<String> tempStr = new ArrayList<>();
                for (int j = firstCellNum; j < lastCellNum; j++) {
                    tempStr.add(getCellValue(row.getCell(j)));
                }
                cityInfos.add(new CityInfo(tempStr));
            }
            workbook.close();
            is.close();
        } catch (IOException e) {
            Log.e(TAG, "Excel Open Error");
            return null;
        }
        return cityInfos;
    }

    private String getCellValue(Cell cell) {
        String cellValue = "";
        if (null == cell) return cellValue;
        switch (cell.getCellType()) {
            default://出错或空白
                break;
            case Cell.CELL_TYPE_NUMERIC://数字
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING://字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN://Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA://公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
        }
        return cellValue;
    }
}
