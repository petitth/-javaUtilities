package edu.file;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Create a pdf document with a table.
 *
 * links :
 * https://stackoverflow.com/questions/3871879/apache-pdfbox-java-library-is-there-an-api-for-creating-tables
 * https://zetcode.com/java/pdfbox/
 */
public class PdfboxTableDocument {

    private static final Logger LOG = LoggerFactory.getLogger(PdfboxTableDocument.class);

    //------ map constants -----------------------------------------
    private static final String    COLUMN_KEY                  = "key";
    private static final String    COLUMN_TITLE                = "title";
    private static final String    COLUMN_WIDTH                = "width";

    //------ errors--------------------------------------------------
    private static final String    ERROR_TABLE_MAX_WIDTH       = "table width have crossed maximum value '%d'";
    private static final String    ERROR_TABLE_NO_COLUMNS      = "no columns have been defined for the table";
    private static final String    ERROR_TABLE_NO_ROWS         = "no rows have been defined for the table";

    //------ document settings --------------------------------------
    private static final int       COLOR_ODD                   = 1;
    private static final int       COLOR_EVEN                  = 2;
    private static final float     PAGE_TOP_MARGIN             = 20.0f;
    private static final float     PAGE_BOTTOM_MARGIN          = 25.0f;
    private static final PDFont    PAGE_NO_FONT                = PDType1Font.HELVETICA;
    private static final float     PAGE_NO_FONT_SIZE           = 10.0f;
    private static final float     PAGE_NO_POSITION            = 15.0f;
    private static final String    PAGE_NO_TEXT                = "page %d / %d";
    private static final PDFont    TABLE_HEADER_FONT           = PDType1Font.HELVETICA_BOLD;
    private static final float     TABLE_HEADER_FONT_SIZE      = 16.0f;
    private static final PDFont    TABLE_CONTENT_FONT          = PDType1Font.HELVETICA;
    private static final float     TABLE_CONTENT_FONT_SIZE     = 12.0f;
    private static final float     TABLE_ROW_HEIGHT            = 20.0f;
    private static final float     TABLE_CELL_MARGIN           = 5.0f;
    private static final float     TABLE_TEXT_MARGIN           = 15.0f;
    private static final String    TABLE_COUNTER_COLUMN_TITLE  = "";
    private static final String    TABLE_COUNTER_COLUMN_KEY    = "COUNTER";
    private static final float     TABLE_COUNTER_COLUMN_SIZE   = 40.0f;

    //------ document info ------------------------------------------
    private String documentTitle;
    private String documentAuthor;
    private String documentKeywords;

    //------ document table content ---------------------------------
    private boolean tableDisplayCounterInFirstColumn;
    private int tableLastRowIndex;
    private List<Map<String, Object>> tableColumns;
    private List<Map<String, Object>> tableRows;

    //------ document table colors ----------------------------------
    private boolean tableUseColors;
    private int tableRowsBackgroundLastColor;
    private Color tableHeaderBackgroundColor;
    private Color tableRowsOddBackgroundColor;
    private Color tableRowsEvenBackgroundColor;
    private Color tableHeaderTextColor;
    private Color tableRowsTextColor;


    public PdfboxTableDocument() {
        //--- document info ---
        this.documentAuthor = null;
        this.documentTitle = null;
        this.documentKeywords = null;
        //--- document table content ---
        this.tableDisplayCounterInFirstColumn = true;
        this.tableLastRowIndex = 0;
        this.tableColumns = new ArrayList<>();
        this.tableRows = new ArrayList<>();
        //--- document table colors ---
        this.tableRowsBackgroundLastColor = COLOR_ODD;
        this.tableUseColors = false;
        this.tableHeaderBackgroundColor = new Color(103, 113, 121);   // dark gray
        this.tableRowsOddBackgroundColor = new Color(254, 254, 254);  // white special
        this.tableRowsEvenBackgroundColor = new Color(206, 206, 206); // light gray
        this.tableHeaderTextColor = new Color(0, 0, 0);               // black
        this.tableRowsTextColor = new Color(0, 0, 0);                 // black
    }

    public void addTableColumn(String key, String title, float width) {
        Map<String, Object> column = new HashMap<>();
        column.put(COLUMN_KEY, key);
        column.put(COLUMN_TITLE, title);
        column.put(COLUMN_WIDTH, width);
        tableColumns.add(column);
    }

    public void addTableRow(Map<String, Object> row) {
        tableRows.add(row);
    }

    public void addTableRows(List<Map<String, Object>> rows) {
        for (Map<String, Object> row : rows) {
            tableRows.add(row);
        }
    }

    public byte[] build() throws IOException {
        validateTableLists();
        try (PDDocument document = new PDDocument()) {
            addDocumentInfo(document);
            while (this.tableLastRowIndex < this.tableRows.size()) {
                PDPage page = new PDPage();
                document.addPage(page);
                LOG.debug("current page : {}", document.getNumberOfPages());
                float pageHeight = page.getMediaBox().getHeight();
                int nbRowsInPage = (int) ((pageHeight - PAGE_TOP_MARGIN - PAGE_BOTTOM_MARGIN) / TABLE_ROW_HEIGHT);
                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    boolean addHeader = (document.getNumberOfPages() == 1);
                    if (addHeader) nbRowsInPage--;
                    List<Map<String, Object>> filteredRows = getFilteredRows(tableRows, nbRowsInPage, tableLastRowIndex);
                    drawTable(page, contentStream, pageHeight - PAGE_TOP_MARGIN, tableColumns, filteredRows, addHeader);
                }
            }

            addPageNumber(document);

            // output
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                document.save(baos);
            } finally {
                baos.close();
            }

            return baos.toByteArray();
        }
    }

    /**
     * Draw a table in the pdf document.
     * @param page current page of document
     * @param contentStream PDPageContentStream
     * @param y the y-coordinate of the first row
     * @throws IOException any exception
     */
    private void drawTable(PDPage page, PDPageContentStream contentStream,
                float y, List<Map<String, Object>> tableColumns,
                List<Map<String, Object>> tableRows, boolean addHeader) throws IOException {

        addCounterColumn();
        long nbRows = addHeader ? tableRows.size() + 1 : tableRows.size();
        float pageWidth = page.getMediaBox().getWidth();
        float pageHeight = page.getMediaBox().getHeight();
        float tableWidth = getTableWidth(tableColumns, pageHeight);
        float tableHeight = TABLE_ROW_HEIGHT * ((float) nbRows);
        float margin = (pageWidth - tableWidth) / 2;

        // set background color
        float nextY = y;
        if (this.tableUseColors) {
            for (int i=0; i < nbRows; i++) {
                Color currentColor;
                if (addHeader && i==0) {
                    currentColor = tableHeaderBackgroundColor;
                } else {
                    if (this.tableRowsBackgroundLastColor == COLOR_ODD) {
                        currentColor = tableRowsOddBackgroundColor;
                        this.tableRowsBackgroundLastColor = COLOR_EVEN;
                    } else {
                        currentColor = tableRowsEvenBackgroundColor;
                        this.tableRowsBackgroundLastColor = COLOR_ODD;
                    }
                }

                nextY -= TABLE_ROW_HEIGHT;
                drawRectangle(contentStream, currentColor, margin, nextY, tableWidth, TABLE_ROW_HEIGHT);
            }
        }

        // draw the rows
        nextY = y;
        for (int i=0; i <= nbRows; i++) {
            drawLine(contentStream, margin, nextY, margin + tableWidth, nextY);
            nextY -= TABLE_ROW_HEIGHT;
        }

        // draw the columns
        float nextX = margin;
        for (int i=0; i < tableColumns.size(); i++) {
            drawLine(contentStream, nextX, y, nextX, y - tableHeight);
            nextX += (float) tableColumns.get(i).get(COLUMN_WIDTH);
        }

        drawLine(contentStream, nextX, y, nextX, y - tableHeight);

        // add text for headers
        float textY = y - TABLE_TEXT_MARGIN;
        float textX = margin + TABLE_CELL_MARGIN;
        if (addHeader) {
            contentStream.setFont(TABLE_HEADER_FONT, TABLE_HEADER_FONT_SIZE);
            contentStream.setNonStrokingColor(this.tableHeaderTextColor);
            for (Map<String, Object> column : tableColumns) {
                contentStream.beginText();
                contentStream.newLineAtOffset(textX, textY);
                contentStream.showText(column.get(COLUMN_TITLE).toString());
                contentStream.endText();
                textX += (float) column.get(COLUMN_WIDTH);
            }
            textY -= TABLE_ROW_HEIGHT;
        }

        // add text for rows
        contentStream.setFont(TABLE_CONTENT_FONT, TABLE_CONTENT_FONT_SIZE);
        for (Map<String, Object> row : tableRows) {
            textX = margin + TABLE_CELL_MARGIN;
            for (Map<String, Object> column : tableColumns) {
                contentStream.beginText();
                contentStream.setNonStrokingColor(this.tableRowsTextColor);
                contentStream.newLineAtOffset(textX, textY);
                if (column.get(COLUMN_KEY).equals(TABLE_COUNTER_COLUMN_KEY)) {
                    contentStream.showText("" + (this.tableLastRowIndex + 1));
                } else {
                    contentStream.showText(row.get(column.get(COLUMN_KEY)).toString());
                }
                contentStream.endText();
                textX += (float) column.get(COLUMN_WIDTH);
            }
            this.tableLastRowIndex++;
            textY -= TABLE_ROW_HEIGHT;
        }

    }

    private void addPageNumber(PDDocument document) throws IOException {
        if (document.getNumberOfPages() > 1) {
            for (int pageNo=0; pageNo < document.getNumberOfPages(); pageNo++) {
                PDPage page = document.getPage(pageNo);
                float pageWidth = page.getMediaBox().getWidth();
                float textX = (pageWidth - PAGE_NO_TEXT.length()) / 2;

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, false)) {
                    contentStream.setFont(PAGE_NO_FONT, PAGE_NO_FONT_SIZE);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(textX, PAGE_NO_POSITION);
                    contentStream.showText(String.format(PAGE_NO_TEXT, pageNo + 1, document.getNumberOfPages()));
                    contentStream.endText();
                }
            }
        }
    }

    private void addDocumentInfo(PDDocument document) {
        PDDocumentInformation pdi = document.getDocumentInformation();
        if (this.documentAuthor != null) {
            pdi.setAuthor(this.documentAuthor);
        }
        if (this.documentTitle != null) {
            pdi.setTitle(this.documentTitle);
        }
        pdi.setCreator("Java code");

        Calendar date = Calendar.getInstance();
        pdi.setCreationDate(date);
        pdi.setModificationDate(date);
        if (this.documentKeywords != null) {
            pdi.setKeywords(this.documentKeywords);
        }
    }

    private void drawLine(PDPageContentStream contentStream, float xStart, float yStart, float xEnd, float yEnd) throws IOException {
        contentStream.moveTo(xStart, yStart);
        contentStream.lineTo(xEnd, yEnd);
        contentStream.stroke();
    }

    private void drawRectangle(PDPageContentStream contentStream, Color color, float x, float y, float width, float height) throws IOException {
        contentStream.setNonStrokingColor(color);
        contentStream.addRect(x, y, width, height);
        contentStream.fill();
    }

    private List<Map<String, Object>> getFilteredRows(List<Map<String, Object>> tableRows, int nbElements, int lastElementIndex) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (int i=lastElementIndex, j=0; i < tableRows.size() && j < nbElements; i++, j++) {
            result.add(tableRows.get(i));
        }

        return result;
    }

    private float getTableWidth(List<Map<String, Object>> tableColumns, float maxWidth) throws IOException {
        float sum = 0;
        for (Map<String, Object> column : tableColumns) {
            sum += (float) column.get(COLUMN_WIDTH);
        }

        if (sum > maxWidth) {
            throw new IOException(String.format(ERROR_TABLE_MAX_WIDTH, (int) maxWidth));
        }

        return sum;
    }

    private void addCounterColumn() {
        if (this.tableDisplayCounterInFirstColumn) {
            if (this.tableColumns.get(0).get(COLUMN_KEY).equals(TABLE_COUNTER_COLUMN_KEY)) {
                return;
            }
            Map<String, Object> counterColumn = new HashMap<>();
            counterColumn.put(COLUMN_KEY, TABLE_COUNTER_COLUMN_KEY );
            counterColumn.put(COLUMN_TITLE, TABLE_COUNTER_COLUMN_TITLE);
            counterColumn.put(COLUMN_WIDTH, TABLE_COUNTER_COLUMN_SIZE);

            this.tableColumns.add(null);
            for (int i=this.tableColumns.size() - 1; i > 0; i--) {
                this.tableColumns.set(i, this.tableColumns.get(i-1));
            }

            this.tableColumns.set(0, counterColumn);
        }
    }

    private void validateTableLists() throws IOException {
        if (this.tableColumns.isEmpty()) {
            throw new IOException(ERROR_TABLE_NO_COLUMNS);
        }

        if (this.tableRows.isEmpty()) {
            throw new IOException(ERROR_TABLE_NO_ROWS);
        }
    }

    //-------------------------------------------------------------------------------------------------
    public void setDocumentAuthor(String documentAuthor) {
        this.documentAuthor = documentAuthor;
    }

    public void setDocumentKeywords(String documentKeywords) {
        this.documentKeywords = documentKeywords;
    }

    public void setDocumentTitle(String documentTitle) {
        this.documentTitle = documentTitle;
    }

    public boolean isTableDisplayCounterInFirstColumn() {
        return tableDisplayCounterInFirstColumn;
    }

    public void setTableDisplayCounterInFirstColumn(boolean tableDisplayCounterInFirstColumn) {
        this.tableDisplayCounterInFirstColumn = tableDisplayCounterInFirstColumn;
    }

    public void setTableUseColors(boolean tableUseColors) {
        this.tableUseColors = tableUseColors;
    }

    public void setTableHeaderBackgroundColor(Color tableHeaderBackgroundColor) {
        this.tableHeaderBackgroundColor = tableHeaderBackgroundColor;
    }

    public void setTableRowsOddBackgroundColor(Color tableRowsOddBackgroundColor) {
        this.tableRowsOddBackgroundColor = tableRowsOddBackgroundColor;
    }

    public void setTableRowsEvenBackgroundColor(Color tableRowsEvenBackgroundColor) {
        this.tableRowsEvenBackgroundColor = tableRowsEvenBackgroundColor;
    }

    public void setTableHeaderTextColor(Color tableHeaderTextColor) {
        this.tableHeaderTextColor = tableHeaderTextColor;
    }

    public void setTableRowsTextColor(Color tableRowsTextColor) {
        this.tableRowsTextColor = tableRowsTextColor;
    }
}