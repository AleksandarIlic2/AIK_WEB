package rs.aik.testautomation.AIKTestAutomation.Data;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import rs.aik.testautomation.AIKTestAutomation.Helpers.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelFactory {


    public static ArrayList<String> getEnvironmentsFromListFOrExecution(String filePath) {
        ArrayList<String> environments = new ArrayList<String>();
        try {

            FileInputStream file = new FileInputStream(new File(filePath));

            XSSFWorkbook workbook = new XSSFWorkbook(file);

            XSSFSheet sheet = workbook.getSheet("Sheet1");
            Row row = sheet.getRow(0);
            //For each row, iterate through all the columns
            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if (cell.getColumnIndex() > 1 && !cell.getStringCellValue().isEmpty()) {
                    environments.add(cell.getStringCellValue());
                }
            }
            file.close();
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return environments;
    }

    /**
     * mathod getDataFromExcel
     *
     * @param filepath  path to excel file
     * @param sheetName from which we need data
     * @return data from the file as ArrayList
     */

    public static ArrayList<HashMap<String, String>> getDataFromExcel(String filepath, String sheetName) {
        ArrayList<HashMap<String, String>> mydata = new ArrayList<HashMap<String, String>>();
        try {
            FileInputStream file = new FileInputStream(filepath);

            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheet(sheetName);
            Row HeaderRow = sheet.getRow(0);

            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                Row currentRow = sheet.getRow(i);
                HashMap<String, String> currentHash = new HashMap<String, String>();
                for (int j = 0; j < currentRow.getPhysicalNumberOfCells(); j++) {
                    //Cell currentCell = currentRow.getCell(j);
                    String celija = new DataFormatter().formatCellValue(currentRow.getCell(j));
                    currentHash.put(HeaderRow.getCell(j).getStringCellValue(), celija);
                }
                mydata.add(currentHash);
            }
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mydata;
    }

    /**
     * method to write result in excel file
     *
     * @param testName   name of a test to write
     * @param state      FAILED or PASSED
     * @param screenshot path to a screenshot file, if present
     */

    public static void WriteTestResult(String testName, String state, String screenshot) {
        try {
            FileInputStream file = new FileInputStream(new File("results/TestResults.xlsx"));

            XSSFWorkbook workbook1 = new XSSFWorkbook(file);

            XSSFSheet sheet = workbook1.getSheet("Sheet1");
            String strDate = new SimpleDateFormat("dd.MM.yyyy:HH:mm").format(Calendar.getInstance().getTime());

            String testCaseId = "";
            try {
                testCaseId = testName.split("[\\[\\]]")[1];
            } catch (Exception ex) {
            }

            Object[][] testData = {{testName, state, strDate, DataManager.envName, "", DataManager.getHostname(), DataManager.webBrowser, "Web",screenshot}};

            int rowCount = sheet.getLastRowNum();

            for (Object[] test : testData) {
                Row row = sheet.createRow(++rowCount);

                int columnCount = 0;

                for (Object field : test) {
                    Cell cell = row.createCell(++columnCount);
                    if (field instanceof String) {
                        cell.setCellValue((String) field);

                        XSSFCellStyle style = workbook1.createCellStyle();
                        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);


                        if (state.equals("PASSED")) {
                            style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
                        }

                        if (state.equals("FAILED")) {
                            style.setFillForegroundColor(IndexedColors.RED.getIndex());
                        }
                        row.getCell(columnCount).setCellStyle(style);

                    } else if (field instanceof Integer) {
                        cell.setCellValue((Integer) field);
                    }
                }
            }
            file.close();
            FileOutputStream outputStream = new FileOutputStream("results/TestResults.xlsx");
            workbook1.write(outputStream);

            workbook1.close();
            outputStream.close();
        } catch (Exception e) {
            Log.error("Exception" + e);
        }
    }

    /**
     * Make a new list of execution from failed tests
     */

    public static void WriteFailedTestResultInNewExecutionList(String testName, String featureID, String featurePriority){
        try{
            FileInputStream file = new FileInputStream(new File("results/failedExecutionList.xlsx"));
            XSSFWorkbook workbook1 = new XSSFWorkbook(file);

            XSSFSheet sheet = workbook1.getSheet("Sheet1");

            Object[][] testData = {{testName,featureID,featurePriority, "data", "on", "off"}};
            int rowCount = sheet.getLastRowNum();

            for (Object[] test : testData) {
                Row row = sheet.createRow(++rowCount);

                int columnCount = 0;

                for (Object field : test) {
                    Cell cell = row.createCell(columnCount);
                    if (field instanceof String) {
                        cell.setCellValue((String) field);

                        XSSFCellStyle style = workbook1.createCellStyle();
                        //style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                        row.getCell(columnCount).setCellStyle(style);
                        columnCount++;
                    } else if (field instanceof Integer) {
                        cell.setCellValue((Integer) field);
                    }
                }
            }
            file.close();
            FileOutputStream outputStream = new FileOutputStream("results/failedExecutionList.xlsx");
            workbook1.write(outputStream);

            workbook1.close();
            outputStream.close();

        } catch (Exception e) {
            Log.error("Exception" + e);
        }
    }


    /**
     * read list of tests for execution from excel file based on env
     *
     * @param filePath   path to excel file
     * @param ColumnName name of the column to get test name
     * @param env        env to use for checking if test should run
     * @return list of test names to be run
     */
    public static ArrayList<String> GetListOfTestForExecution(String filePath, String ColumnName, String env) {
        ArrayList<String> listForExecution = new ArrayList<String>();
        ArrayList<Integer> listRowIndex = new ArrayList<Integer>();
        try {

            FileInputStream file = new FileInputStream(new File(filePath));

            XSSFWorkbook workbook = new XSSFWorkbook(file);

            XSSFSheet sheet = workbook.getSheet("Sheet1");
            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            int columnIndex = 0;
            int columnIndex1 = 0;

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    if (cell.getRowIndex() == 0) {
                        if (cell.getStringCellValue().equals(ColumnName)) {
                            columnIndex = cell.getColumnIndex();
                        }

                        if (cell.getStringCellValue().toUpperCase().equals(env.toUpperCase())) {
                            columnIndex1 = cell.getColumnIndex();
                        }
                    }

                    if (cell.getColumnIndex() == columnIndex1) {
                        if (cell.getStringCellValue().toUpperCase().equals("ON")) {
                            listRowIndex.add(cell.getRowIndex());
                            listForExecution.add(sheet.getRow(cell.getRowIndex()).getCell(columnIndex).getStringCellValue()
                                    .toString());
                        }
                    }

                }
            }
            file.close();
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listForExecution;
    }

    public static void addRowInExcel(String xlFullName, String sheetName, String... line){
        try{
            FileInputStream file = new FileInputStream(new File(xlFullName));
            XSSFWorkbook workbook1 = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook1.getSheet(sheetName);
            Object[][] testData = {line};

            int rowCount = sheet.getLastRowNum();

            for (Object[] test : testData) {
                Row row = sheet.createRow(++rowCount);

                int columnCount = 0;

                for (Object field : test) {
                    Cell cell = row.createCell(columnCount++);
                    cell.setCellValue((String) field);
                }
            }
            file.close();
            FileOutputStream outputStream = new FileOutputStream(xlFullName);
            workbook1.write(outputStream);

            workbook1.close();
            outputStream.close();
        } catch (Exception e) {
            //log.error("Exception" + e);
        }
    }

    public static String ReadExcelByKey(String filePath, String Speccolumn, String RowValue, String ColumnName) {
        String data = "";
        try {

            FileInputStream file = new FileInputStream(new File(filePath));

            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            // XSSFSheet sheet = workbook.getSheetAt(0);

            XSSFSheet sheet = workbook.getSheet("Sheet1");
            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            int rowIndex = 0;
            int columnIndex = 0;
            int columnIndex1 = 0;

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    if (cell.getRowIndex() == 0) {
                        if (cell.getStringCellValue().equals(ColumnName)) {
                            columnIndex = cell.getColumnIndex();
                        }

                        if (cell.getStringCellValue().equals(Speccolumn)) {
                            columnIndex1 = cell.getColumnIndex();
                        }
                    }

                    if (cell.getColumnIndex() == columnIndex1) {
                        if (cell.getStringCellValue().toUpperCase().equals(RowValue.toUpperCase())) {
                            rowIndex = cell.getRowIndex();
                        }
                    }

                    if (columnIndex != 0 && rowIndex != 0) {
                        data = sheet.getRow(rowIndex).getCell(columnIndex).getStringCellValue().toString();
                        rowIndex = 0;
                        columnIndex = 0;
                    }
                }
            }
            file.close();
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * read data from excel file in specified sheet and column from the first row
     *
     * @param ColumnName name of column
     * @param path       path to file
     * @param sheetName  name of the sheet to read
     * @return value of excel cell as string
     * @throws IOException if an error occurs while reading file
     */
    public static String readDataFromExcelUsingColumnName(String ColumnName, String path, String sheetName) throws IOException {
        String SheetName = sheetName;//Sheet1
        //String Active = active;//ovde mora da se procita iz excela i na osnovu on ide na getRow(1) ili na getRow(2)
        File file = new File(path);//testdata/configuration.xlsx
        FileInputStream fi = new FileInputStream(file);
        Workbook wb = WorkbookFactory.create(fi);
        Sheet sheet = wb.getSheet(SheetName);
        // it will take value from first row
        Row row = sheet.getRow(0);
        // it will give you count of row which is used or filled
        short lastcolumnused = row.getLastCellNum();
        //String Active = row.getCell(lastcolumnused).getStringCellValue();
        int colnum = 0;
        // int brojac = 1;

        for (int i = 0; i < lastcolumnused; i++) {
            if (row.getCell(i).getStringCellValue().equalsIgnoreCase(ColumnName)) {

                colnum = i;
                break;

            }

        }
        // it will take value from Second row
        row = sheet.getRow(1);
        Cell column = row.getCell(colnum);
        String CellValue = column.getStringCellValue();

        return CellValue;

    }

    /**
     * read excel file to a map
     *
     * @param filepath  path to excel file
     * @param SheetName name of sheet to read
     * @return map of sheetname -> sheetdata
     * @throws IOException if an error occures while reading file
     */

    public static Map<String, Map<String, String>> setMapData(String filepath, String SheetName) throws IOException {

        FileInputStream fis = new FileInputStream(filepath);

        Workbook workbook = new XSSFWorkbook(fis);

        Sheet sheet = workbook.getSheetAt(0);

        int lastRow = sheet.getLastRowNum();

        Map<String, Map<String, String>> excelFileMap = new HashMap<String, Map<String, String>>();

        Map<String, String> dataMap = new HashMap<String, String>();

        //Looping over entire row
        for (int i = 0; i <= lastRow; i++) {

            Row row = sheet.getRow(i);

            //1st Cell as Value
            Cell valueCell = row.getCell(1);

            //0th Cell as Key
            Cell keyCell = row.getCell(0);

            String value = valueCell.getStringCellValue().trim();
            String key = keyCell.getStringCellValue().trim();

            //Putting key & value in dataMap
            dataMap.put(key, value);

            //Putting dataMap to excelFileMap
            excelFileMap.put(SheetName, dataMap);
        }

        //Returning excelFileMap
        return excelFileMap;

    }

    public static List<HashMap<String, String>> setListData(String filepath, String SheetName) throws IOException {

        FileInputStream fis = new FileInputStream(filepath);

        Workbook workbook = new XSSFWorkbook(fis);

        Sheet sheet = workbook.getSheetAt(0);

        int lastRow = sheet.getLastRowNum();

        List<HashMap<String, String>> excelFileMap = new ArrayList<>();



        Row headRow = sheet.getRow(0);

        //Looping over entire row
        for (int i = 1; i <= lastRow; i++) {
            HashMap<String, String> dataMap = new HashMap<String, String>();
            Row row = sheet.getRow(i);

            int lastCell = row.getLastCellNum();
            for(int j = 0; j < lastCell; j++){
                String key = headRow.getCell(j).getStringCellValue().trim();
                String value = row.getCell(j).getStringCellValue().trim();
                dataMap.put(key, value);
            }


            //1st Cell as Value
            //Cell valueCell = row.getCell(1);

            //0th Cell as Key
            //Cell keyCell = row.getCell(0);

            //String value = valueCell.getStringCellValue().trim();
            //String key = keyCell.getStringCellValue().trim();

            //Putting key & value in dataMap
            //dataMap.put(key, value);

            //Putting dataMap to excelFileMap
            excelFileMap.add( dataMap);
        }

        //Returning excelFileMap
        return excelFileMap;

    }
}
