package com.starcpdk.edu.service_base.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        //属性名称不是字段名称
        this.setFieldValByName("uniCreate", new Date(), metaObject);
        this.setFieldValByName("uniModified", new Date(), metaObject);
        this.setFieldValByName("isDeleted", false, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("uniModified", new Date(), metaObject);
    }
}
