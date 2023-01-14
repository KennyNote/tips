package com.learning.notebook.tips.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import java.util.ArrayList;
import java.util.List;

/**
 * excel导入监听器:
 *
 * @author lixiaolong
 * @since 2020/12/09
 */
public class ExcelImportListener<T> extends AnalysisEventListener<T> {

    //    /**
//     * 实际使用中可以3000条，然后清理list ，方便内存回收
//     */
//    private static final int BATCH_COUNT = 3000;
    private static final int MAX_COUNT = 5000;
    private final List<T> importedList = new ArrayList<>();
    private ExcelConsumer<T> fileConsumer;

    private ExcelImportListener() {
    }

    public ExcelImportListener(ExcelConsumer<T> consumer) {
        this.fileConsumer = consumer;
    }

    @Override
    public void invoke(T data, AnalysisContext analysisContext) {
        importedList.add(data);
        if (importedList.size() > MAX_COUNT) {
            throw new RuntimeException("单次导入行数限制:" + MAX_COUNT + "行");
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        fileConsumer.accept(importedList);
    }

}
