package com.a360.zhangzhenguo.hack;

/**
 * Created by zhangzhenguo on 2018/4/19.
 */

public enum ResponseType {
    OK("请求成功") , FAIL("请求失败") , TIMEOUT("请求超时") ;

    private String name ;

    private ResponseType(String name ){
        this.name = name ;
    }

    public String getName() {
        return name;
    }
}
