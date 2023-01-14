package com.learning.notebook.tips.excel.BO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SettleBillDetailExportBO {

    private String seatPlanName;

    private String sessionName;

    private String count;

    private String amount;

    private String discount;

    private String settleAmount;

    private String commissionFee;

    private String commissionRate;

    private String p = "";
}
