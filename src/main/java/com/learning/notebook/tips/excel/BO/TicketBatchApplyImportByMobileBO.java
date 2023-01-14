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
public class TicketBatchApplyImportByMobileBO {

    @ExcelProperty(value = "下单人手机号", index = 0)
    private String cellphone;
}
