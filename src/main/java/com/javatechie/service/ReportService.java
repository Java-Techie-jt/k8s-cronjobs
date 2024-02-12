package com.javatechie.service;

import com.javatechie.dto.User;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class ReportService {


    public byte[] generateReport() throws IOException {
        List<User> userList = readUsersFromCsv();
        System.out.println("Data loaded from server "+userList.size());
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        writeHeaderLine(sheet);
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = (XSSFFont) workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (User user : userList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, user.getId(), style);
            createCell(row, columnCount++, user.getFirstName(), style);
            createCell(row, columnCount++, user.getLastName(), style);
            createCell(row, columnCount++, user.getEmail(), style);
            createCell(row, columnCount++, user.getGender(), style);
            createCell(row, columnCount++, user.getIpAddress(), style);

        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            workbook.write(bos);
        } finally {
            bos.close();
        }
        return bos.toByteArray();

    }

    public List<User> readUsersFromCsv() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new ClassPathResource("users.csv").getInputStream()))) {
            CsvToBean<User> csvToBean = new CsvToBeanBuilder<User>(reader)
                    .withType(User.class)
                    .build();

            return csvToBean.parse();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as needed
            return null;
        }
    }




    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeHeaderLine(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("USER_ID");

        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("FIRST_NAME");

        headerCell = headerRow.createCell(2);
        headerCell.setCellValue("LAST_NAME");

        headerCell = headerRow.createCell(3);
        headerCell.setCellValue("EMAIL");

        headerCell = headerRow.createCell(4);
        headerCell.setCellValue("GENDER");

        headerCell = headerRow.createCell(5);
        headerCell.setCellValue("IP_ADDRESS");

    }


}
