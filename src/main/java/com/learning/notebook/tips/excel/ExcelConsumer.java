package com.learning.notebook.tips.excel;

import java.util.List;

/**
 * Excel文件处理接口
 *
 * @author lixiaolong
 * @since 2020/12/09
 */
@FunctionalInterface
public interface ExcelConsumer<T> {

    void accept(List<T> data);

}
