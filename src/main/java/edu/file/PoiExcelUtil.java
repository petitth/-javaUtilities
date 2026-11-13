package edu.file;

import edu.bean.Employee;
import edu.time.TimeUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import static edu.file.DocumentConstants.*;

public class PoiExcelUtil {

    private PoiExcelUtil() { }

    /**
     * Create an Excel document with XSSF.
     * XSSF is adapted for Excel 2007 OOXML (.xlsx) file format.
     */
    public static byte[] excelExportWithXSSF(List<Employee> employeeList) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("employees");
        addEmployeeHeader(workbook, sheet);
        addEmployeeContent(employeeList, sheet);
        return getResult(workbook);
    }

    /**
     * Create an Excel document with HSSF.
     * HSSF is adapted for Microsoft Office 97-2003 (.xls) file format.
     */
    public static byte[] excelExportWithHSSF(List<Employee> employeeList) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("employees");
        addEmployeeHeader(workbook, sheet);
        addEmployeeContent(employeeList, sheet);
        return getResult(workbook);
    }

    private static byte[] getResult(Workbook workbook) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            workbook.write(baos);
        } finally {
            baos.close();
        }

        return baos.toByteArray();
    }

    private static void addEmployeeContent(List<Employee> employeeList, Sheet sheet) {
        Row row;
        int cellNo;
        int rowNo = 1;

        for (Employee employee : employeeList) {
            row = sheet.createRow(rowNo);
            cellNo = 0;
            row.createCell(cellNo++, CellType.STRING).setCellValue(employee.getId());
            row.createCell(cellNo++, CellType.STRING).setCellValue(employee.getLastName());
            row.createCell(cellNo++, CellType.STRING).setCellValue(employee.getFirstName());
            row.createCell(cellNo++, CellType.STRING).setCellValue(employee.getCountry());
            row.createCell(cellNo, CellType.NUMERIC).setCellValue(employee.getSalary());
            rowNo++;
        }

        if (!employeeList.isEmpty()) {
            rowNo = rowNo + 4;
            row = sheet.createRow(rowNo);
            row.createCell(10, CellType.STRING).setCellValue(EMPLOYEE_SALARY_TOTAL);
            row.createCell(11, CellType.FORMULA).setCellFormula("SUM(E1:E20)");
        }
    }

    private static void addEmployeeHeader(Workbook workbook, Sheet sheet) {
        CellStyle cellStyle = getStyleForTitle(workbook);
        int cellNo = 0;
        Cell cell;
        Row header = sheet.createRow(0);

        cell = header.createCell(cellNo++, CellType.STRING);
        cell.setCellValue(EMPLOYEE_ID);
        cell.setCellStyle(cellStyle);
        cell = header.createCell(cellNo++, CellType.STRING);
        cell.setCellValue(EMPLOYEE_LAST_NAME);
        cell.setCellStyle(cellStyle);
        cell = header.createCell(cellNo++, CellType.STRING);
        cell.setCellValue(EMPLOYEE_FIRST_NAME);
        cell.setCellStyle(cellStyle);
        cell = header.createCell(cellNo++, CellType.STRING);
        cell.setCellValue(EMPLOYEE_COUNTRY);
        cell.setCellStyle(cellStyle);
        cell = header.createCell(cellNo, CellType.STRING);
        cell.setCellValue(EMPLOYEE_SALARY);
        cell.setCellStyle(cellStyle);
    }

    private static CellStyle getStyleForTitle(Workbook workbook) {
        Font font = workbook.createFont();
        font.setBold(true);
        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }

    public static byte[] createDocumentWithDates(int year) throws IOException {
        final String DATE_FORMAT = "dd/MM/yyyy";
        XSSFWorkbook workbook = new XSSFWorkbook();
        for (int month=1; month<=12; month++) {
            LocalDate date = TimeUtil.getFirstDayOfMonth(month, year);
            LocalDate lastDayOfMonth = TimeUtil.getLastDayOfMonth(month, year);

            XSSFSheet sheet = workbook.createSheet(date.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()));
            Row row;
            int cellNo;
            int rowNo = 0;

            for (int i = 1; i <= lastDayOfMonth.getDayOfMonth(); i++) {
                row = sheet.createRow(rowNo);
                cellNo = 0;
                row.createCell(cellNo++, CellType.STRING).setCellValue(date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault()));
                row.createCell(cellNo, CellType.STRING).setCellValue(date.format(DateTimeFormatter.ofPattern(DATE_FORMAT)));
                date = date.plusDays(1);
                rowNo++;
            }
        }
        return getResult(workbook);
    }

}