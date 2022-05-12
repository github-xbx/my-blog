package com.xingbingxuan.blog.gateway.filter;

/**
 * @author : xbx
 * @date : 2022/4/26 20:14
 */
public class FilterFactoryConfig {
    private boolean withParams;

    public boolean isWithParams() {
        return withParams;
    }

    public void setWithParams(boolean withParams) {
        this.withParams = withParams;
    }

    @Override
    public String toString() {
        return "FilterFactoryConfig{" +
                "withParams=" + withParams +
                '}';
    }
}
