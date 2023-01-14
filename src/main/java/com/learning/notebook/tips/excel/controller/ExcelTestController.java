package com.learning.notebook.tips.excel.controller;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.learning.notebook.tips.excel.BO.SettleBillDetailExportBO;
import com.learning.notebook.tips.excel.BO.SettleBillExportBO;
import com.learning.notebook.tips.excel.CellColorSheetWriteHandler;
import jakarta.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.apache.commons.compress.utils.Lists;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExcelTestController {

    //    @GetMapping("/testDownLoad1")
    //    public void testDownLoad1(HttpServletResponse response){
    //        ExecutorService executorService = Executors.newFixedThreadPool(1);
    //        System.out.println("request start");
    //        CompletableFuture.runAsync(() -> {
    //            try {
    //                System.out.println("async 是否为守护线程 " + Thread.currentThread().isDaemon());
    //                System.out.println("async start");
    //                Thread.sleep(3000);
    //                System.out.println("async end");
    //            } catch (InterruptedException e) {
    //                e.printStackTrace();
    //            }
    //        }, executorService);
    //        System.out.println("request end");
    //    }

    @GetMapping("/testDownLoad1")
    public void testDownLoad2(HttpServletResponse response) {
        //            System.out.println("request start");
        //            CompletableFuture.runAsync(() -> {
        //                try {
        //                    System.out.println("async 是否为守护线程 " + Thread.currentThread().isDaemon());
        //                    System.out.println("async start");
        //                    Thread.sleep(3000);
        //                    System.out.println("async end");
        //                } catch (InterruptedException e) {
        //                    e.printStackTrace();
        //                }
        //            });
        //            System.out.println("request end");

        SettleBillExportBO billExportBO = new SettleBillExportBO();
        billExportBO.setExportTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        billExportBO.setOtherFundItemDesc("xxxxxx");

        List<SettleBillDetailExportBO> settleBillDetailExportBOList = Lists.newArrayList();

        SettleBillDetailExportBO settleDetailExportBO1 = SettleBillDetailExportBO.builder()
            .seatPlanName("1")
            .sessionName("1")
            .count("1")
            .amount("1")
            .discount("1")
            .settleAmount("1")
            .commissionFee("1")
            .commissionRate("1")
            .p("")
            .build();
        SettleBillDetailExportBO settleDetailExportBO2 = SettleBillDetailExportBO.builder()
            .seatPlanName("2")
            .sessionName("2")
            .count("2")
            .amount("2")
            .discount("2")
            .settleAmount("2")
            .commissionFee("2")
            .commissionRate("2")
            .p("")
            .build();
        SettleBillDetailExportBO settleDetailExportBO3 = SettleBillDetailExportBO.builder()
            .seatPlanName("3")
            .sessionName("3")
            .count("3")
            .amount("3")
            .discount("3")
            .settleAmount("3")
            .commissionFee("3")
            .commissionRate("3")
            .p("")
            .build();
        settleBillDetailExportBOList.add(settleDetailExportBO1);
        settleBillDetailExportBOList.add(settleDetailExportBO2);
        settleBillDetailExportBOList.add(settleDetailExportBO3);

        try {
            String exportFileName = "settle_bill_export.xlsx";
            String fileName = URLEncoder.encode("结算单导出", "UTF-8");
            InputStream inputStream = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("settle_bill_export_template.xlsx");
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + exportFileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");

            CellColorSheetWriteHandler cellColorSheetWriteHandler = new CellColorSheetWriteHandler();
            cellColorSheetWriteHandler.setListSize(3);

            ExcelWriter excelWriter = EasyExcelFactory
                .write(response.getOutputStream())
                .withTemplate(inputStream)
                .registerWriteHandler(cellColorSheetWriteHandler)
                .build();
            FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();

            WriteSheet writeSheet = EasyExcelFactory.writerSheet().build();
            excelWriter.fill(settleBillDetailExportBOList, fillConfig, writeSheet);
            excelWriter.fill(billExportBO, writeSheet);
            excelWriter.finish();

        } catch (Exception e) {

        }
    }
}
