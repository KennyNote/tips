package com.learning.notebook.tips.excel.BO;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TicketBatchApplyImportByOrderBO {

    @ExcelProperty(value = "订单编号", index = 0)
    private String orderNo;
}
