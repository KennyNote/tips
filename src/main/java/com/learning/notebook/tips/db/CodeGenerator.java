package com.learning.notebook.tips.db;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import io.micrometer.core.instrument.util.StringUtils;
import java.util.Scanner;

public class CodeGenerator {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入" + tip + "：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new RuntimeException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mybatisPlusCodeGenerator = new AutoGenerator();

        Cfgs cfgs;
        cfgs = getCfgs(CfgType.PO);
        /*cfgs = getCfgs(CfgType.PARAM);
        cfgs = getCfgs(CfgType.INFO);*/
        GlobalConfig gc = cfgs.getGc();

        // 将上述的全局配置注入
        mybatisPlusCodeGenerator.setGlobalConfig(gc);
        DataSourceConfig dataSourceConfiguration = getDataSourceConfig();

        mybatisPlusCodeGenerator.setDataSource(dataSourceConfiguration);
        PackageConfig pc = cfgs.getPc();

        // 装填包信息对象
        mybatisPlusCodeGenerator.setPackageInfo(pc);
        StrategyConfig strategy = cfgs.getStrategy();

        mybatisPlusCodeGenerator.setStrategy(strategy);

        mybatisPlusCodeGenerator.execute();
    }

    private static Cfgs getCfgs(CfgType cfgType) {
        return Cfgs.builder()
                .gc(getGlobalConfig(cfgType))
                .pc(getPackageConfig(cfgType))
                .strategy(getStrategyConfig(cfgType))
                .build();
    }

    private static GlobalConfig getGlobalConfig(CfgType cfgType) {
        // 全局配置
        GlobalConfig gc = new GlobalConfig();

        // 先得到当前工程目录
        // String projectPath = System.getProperty("user.dir");
        // 是maven项目的结构，就是工程目录 + /src/main/java
        // gc.setOutputDir(projectPath + "/src/main/java");

        gc.setOutputDir("mybatis-code-gen/src/main/java");

        // 设置生成文件的作者信息
        gc.setAuthor("chenguijin");

        //当代码生成完成之后是否打开代码所在的文件夹
        gc.setOpen(false);

        gc.setSwagger2(CfgType.PO != cfgType); //实体属性 Swagger2 注解
        gc.setEntityName(cfgType.getEntityName());

        gc.setServiceName(cfgType.getServiceName());
        gc.setServiceImplName(cfgType.getServiceName() + "Impl");

        return gc;
    }

    private static PackageConfig getPackageConfig(CfgType cfgType) {
        // 包配置
        PackageConfig pc = new PackageConfig();

        // 设置父级包名
        pc.setParent("com.mtl.mtc.order");//controller entity service service.impl

        //pc.setModuleName(scanner("模块名"));
        pc.setModuleName("core");

        // 实体类名称
        pc.setEntity(cfgType.getEntityPkg());

        // mapper包名称
        pc.setMapper("mapper");

        // mapper对应的映射器xml
        pc.setXml("mapper.xml");

        // 业务包层名称
        pc.setService(cfgType.getServicePkg());

        // 业务接口的实现类包
        pc.setServiceImpl(cfgType.getServicePkg() + ".impl");

        // 控制器包名称
        pc.setController("controller");
        return pc;
    }

    private static StrategyConfig getStrategyConfig(CfgType cfgType) {
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();

        //设置字段和表名的是否把下划线完成驼峰命名规则
        strategy.setNaming(NamingStrategy.underline_to_camel);
        //strategy.setColumnNaming(CfgType.PO == cfgType ? NamingStrategy.no_change : NamingStrategy.underline_to_camel);

        //设置生成的实体类继承的父类
        strategy.setSuperEntityClass(cfgType.getSuperEntityClass());
        // 写于父类中的公共字段
        strategy.setSuperEntityColumns(cfgType.getSuperEntityColumns());

        //是否启动lombok
        strategy.setEntityLombokModel(true);

        strategy.setSuperMapperClass("com.mtl.mtc.order.core.dao.BaseMapper");

        // com.mtl.mtc.order.core.service.BaseService
        String superServiceClassName = ".BaseDao";
        String superServiceClassPkg = cfgType.getSuperServiceClass();
        String superServiceClass = superServiceClassPkg + superServiceClassName;

        strategy.setSuperServiceClass(superServiceClass);
        strategy.setSuperServiceImplClass(superServiceClassPkg + ".impl" + superServiceClassName + "Impl");

        //是否生成resetController
        strategy.setRestControllerStyle(true);
        // 公共父类
        //strategy.setSuperControllerClass("com.sxt.BaseController");


        //要设置生成哪些表 如果不设置就是生成所有的表
        //strategy.setInclude(scanner("请输入表名(多个英文逗号分割): ").split(","));
        strategy.setInclude(
                /*"ap_apply",
                "ap_apply_audit",
                "ap_apply_item",
                "ap_refund_apply_item",*/
                "ap_service_log"

                /*"schedule_session",
                "schedule_session_item"
                "tc_order_log",
                "tc_order",
                "tc_order_item",
                "tc_ticket_item",
                "tc_order_price_item",
                "tc_order_price_item_detail",
                "tc_supply_order",
                "tc_transaction"*/
        );

        strategy.setControllerMappingHyphenStyle(true);

        //strategy.setTablePrefix(pc.getModuleName() + "_");
        //strategy.setTablePrefix("im_");
        return strategy;
    }

    private static DataSourceConfig getDataSourceConfig() {
        // 数据源配置
        DataSourceConfig dataSourceConfiguration = new DataSourceConfig();
        dataSourceConfiguration.setDriverName("com.mysql.cj.jdbc.Driver");

        /* local.order_center
        dataSourceConfiguration.setUrl("jdbc:mysql://localhost:3306/order_center?characterEncoding=utf8&serverTimezone=UTC&useSSL=false");
        // dataSourceConfiguration.setSchemaName("public");
        dataSourceConfiguration.setUsername("root");
        dataSourceConfiguration.setPassword("Root*123");
        */

        /* dev.order_center */
        dataSourceConfiguration.setUrl("jdbc:mysql://10.1.2.13:3306/order_center?characterEncoding=utf8&serverTimezone=UTC");
        // dataSourceConfiguration.setSchemaName("public");
        dataSourceConfiguration.setUsername("dev_niumowang");
        dataSourceConfiguration.setPassword("1QAZ2wsx");

        /* dev.operation_center
        dataSourceConfiguration.setUrl("jdbc:mysql://10.1.2.13:3306/operation_center?characterEncoding=utf8&serverTimezone=UTC");
        // dataSourceConfiguration.setSchemaName("public");
        dataSourceConfiguration.setUsername("dev_niumowang");
        dataSourceConfiguration.setPassword("1QAZ2wsx");
        */
        return dataSourceConfiguration;
    }
}
