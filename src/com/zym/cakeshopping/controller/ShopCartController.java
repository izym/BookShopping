package com.zym.cakeshopping.controller;

import com.google.gson.Gson;
import com.zym.cakeshopping.dao.CakeEntity;
import com.zym.cakeshopping.dao.ShopCartEntity;
import com.zym.cakeshopping.dao.UserEntity;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 12390 on 2017/12/8.
 */
@IocBean
public class ShopCartController {
    @Inject
    Dao dao;


    @Ok("json")
    @Fail("http:500")
    @At("/add_shop_cart")
    @POST
    @GET
    public Object addShopCart(@Param("cakeId")Integer cakeId, @Param("userId") Integer userId){
        NutMap re = new NutMap();
        if(cakeId!=null&&userId!=null){
            ShopCartEntity shopCartEntity = new ShopCartEntity();
            shopCartEntity.setCakeId(cakeId);
            shopCartEntity.setUserId(userId);
            shopCartEntity.setState(0);
            dao.insert(shopCartEntity);
            re.put("status", "1");
            re.put("msg", "加入购物车成功");
        }else{
            re.put("status", "0");
            re.put("msg", "加入购物车失败");
        }
        return re;
    }


    @Ok("json")
    @Fail("http:500")
    @At("/del_shop_cart")
    @POST
    @GET
    public Object delShopCart(@Param("cakeId")Integer cakeId, @Param("userId") Integer userId){
        NutMap re = new NutMap();
        if(cakeId!=null&&userId!=null){
            ShopCartEntity shopCartEntity = dao.fetch(ShopCartEntity.class,
                    Cnd.where("userId","=", userId)
                            .and("cakeId","=", cakeId)
                            .and("state", "=", 0));
            if(shopCartEntity!=null) {
                dao.delete(shopCartEntity);
                re.put("status", "1");
                re.put("msg", "删除成功");
            }else{
                re.put("status", "0");
                re.put("msg", "删除失败");
            }
        }else{
            re.put("status", "0");
            re.put("msg", "删除失败");
        }
        return re;
    }


    @Ok("json")
    @Fail("http:500")
    @At("/buy")
    @POST
    @GET
    public Object buy(@Param("userId") Integer userId){
        NutMap re = new NutMap();
        if(userId!=null){
            List<ShopCartEntity> cartList = dao.query(ShopCartEntity.class, Cnd.where("userId", "=", userId)
                    .and("state", "=", 0));
            if(cartList!=null){
                int sum = 0;
                for (ShopCartEntity cart: cartList) {
                    CakeEntity cake = dao.fetch(CakeEntity.class, Cnd.where("cakeId", "=", cart.getCakeId()));
                    sum+=cake.getPrice();
                }
                UserEntity user = dao.fetch(UserEntity.class, Cnd.where("userId", "=", userId));
                if(sum<=user.getMoney()){
                    user.setMoney(user.getMoney()-sum);
                    dao.update(user, "^money$");
                    for (ShopCartEntity cart: cartList) {
                        cart.setState(1);
                    }
                    dao.update(cartList,"^state$");
                    re.put("status", "1");
                    re.put("msg", "结算成功");
                    re.put("money", sum);
                }else{
                    re.put("status", "0");
                    re.put("msg", "结算失败，余额不足");
                    re.put("money", 0);
                }

            }
        }else{
            re.put("status", "0");
            re.put("msg", "结算失败");
        }
        return re;
    }

    @Ok("json")
    @Fail("http:500")
    @At("/get_orders")
    @GET
    @POST
    public Object getOrders(@Param("userId") Integer userId, @Param("state") Integer state){
        NutMap re = new NutMap();
        if(userId!=null){
            List<ShopCartEntity> cartList = dao.query(ShopCartEntity.class, Cnd.where("userId", "=", userId)
                    .and("state", "=", state));
            if(cartList!=null&&cartList.size()>0){
                List<CakeEntity> cakes = new ArrayList<>();
                for (ShopCartEntity cart: cartList) {
                    cakes.add(dao.fetch(CakeEntity.class, Cnd.where("cakeId", "=", cart.getCakeId())));
                }
                re.put("status", "1");
                re.put("msg", "OK");
                re.put("cakes", cakes);
            }else{
                re.put("status", "0");
                re.put("msg", "没有相关信息");
            }
        }else{
            re.put("status", "0");
            re.put("msg", "没有相关信息");
        }
        return re;
    }

}
