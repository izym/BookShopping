package com.zym.cakeshopping.controller;

import com.zym.cakeshopping.dao.CakeEntity;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;

import java.util.List;

/**
 * Created by zym on 2017/12/14.
 */
@IocBean
public class CakeController {
    @Inject
    Dao dao;

    @Ok("json")
    @Fail("http:500")
    @At("/get_cakes")
    @POST
    @GET
    public Object getCakes(){
        NutMap re = new NutMap();
        List<CakeEntity> cakes = dao.query(CakeEntity.class, null);
        if(cakes!=null&&cakes.size()>0){
            re.put("status", "1");
            re.put("msg", "ok");
            re.put("cakes", cakes);
        }else{
            re.put("status", "0");
            re.put("msg", "没有商品");
        }
        return re;
    }
}
