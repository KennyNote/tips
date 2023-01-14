package com.learning.notebook.tips.excel.BO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SettleBillExportBO {

    private String settleNo;

    private String merchantName;

    private String supplierMerchantName;

    private String settleBillShowName;

    private String orderCount;

    private String orderAmount;

    private String commissionFee;

    private String settleAmount;

    private String settleRuleTypeStr;

    private String settleMode;

    private String otherFundItemAmount;

    private String otherFundItemDesc;

    private String createTime;

    private String exportTime;
}
