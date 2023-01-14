package com.learning.notebook.tips.excel;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.util.StyleUtil;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import java.util.List;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

public class CellColorSheetWriteHandler implements CellWriteHandler {

    private Integer listSize;

    public Integer getListSize() {
        return listSize;
    }

    public void setListSize(Integer listSize) {
        this.listSize = listSize;
    }

    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
        Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead) {

    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell,
        Head head, Integer relativeRowIndex, Boolean isHead) {
    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {

        System.out.println(cell.getRowIndex() + "-" + cell.getColumnIndex());
        Workbook workbook = cell.getSheet().getWorkbook();
        if (cell.getColumnIndex() == 9) {
            WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
            contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
            CellStyle cellStyle =  StyleUtil.buildCellStyle(workbook, null, contentWriteCellStyle);
            cell.setCellStyle(cellStyle);
        }

    }
}
