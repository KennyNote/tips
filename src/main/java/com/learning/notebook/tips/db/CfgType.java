package com.learning.notebook.tips.db;

/**
 * CfgType
 *
 * @author chenguijin
 * @since 2021/6/3 20:58
 */
public enum CfgType {

    PO("%sDao", "dao", "com.mtl.mtc.order.core.dao", "%sPO", "po", "com.mtl.mtc.order.core.po.BasePO", new String[]{"id", "create_time", "update_time", "is_deleted"}),
    
    /*
    PO("%sService", "service", "com.mtl.common.service.base.BaseService", "%sPO", "domain.po", "com.juqitech.entity.BasePO", new String[]{"id", "create_time", "update_time", "is_deleted"}),
    PARAM("%sDao", "dao", "com.mtl.common.service.base.BaseDao", "%sParam", "param", "com.juqitech.request.BaseParam", new String[]{}),
    INFO("%sDao", "dao", "com.mtl.common.service.base.BaseDao", "%sInfo", "info", "com.juqitech.response.BaseEntityInfo", new String[]{"id", "create_time", "update_time"})
    */
    ;

    CfgType(String serviceName, String servicePkg, String superServiceClass, String entityName, String entityPkg, String superEntityClass, String[] superEntityColumns) {
        this.serviceName = serviceName;
        this.servicePkg = servicePkg;
        this.superServiceClass = superServiceClass;

        this.entityName = entityName;
        this.entityPkg = entityPkg;
        this.superEntityClass = superEntityClass;
        this.superEntityColumns = superEntityColumns;
    }

    private String serviceName;
    private String servicePkg;
    private String superServiceClass;

    private String entityName;
    private String entityPkg;
    private String superEntityClass;
    private String[] superEntityColumns;

    public String getServiceName() {
        return serviceName;
    }

    public String getServicePkg() {
        return servicePkg;
    }

    public String getSuperServiceClass() {
        return superServiceClass;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getEntityPkg() {
        return entityPkg;
    }

    public String getSuperEntityClass() {
        return superEntityClass;
    }

    public String[] getSuperEntityColumns() {
        return superEntityColumns;
    }
}
