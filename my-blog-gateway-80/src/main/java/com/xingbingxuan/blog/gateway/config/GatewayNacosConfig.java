//package com.xingbingxuan.blog.gateway.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//
//import javax.validation.Valid;
//import java.util.List;
//
///**
// * nacos 路由配置
// * @author : xbx
// * @date : 2022/7/24 16:16
// */
//@Configuration
//public class GatewayNacosConfig {
//
//    public static final long DEFAULT_TIMEOUT = 30000;
//
//    public static String NACOS_SERVER_ADDR;
//
//    public static String NACOS_NAMESPACE;
//
//    public static String NACOS_ROUTE_DATA_ID;
//
//    public static String NACOS_ROUTE_GROUP;
//
//    public static List NACOS_ROUTSE;
//    @Value("${spring.gateway.routes")
//    public void setRoutes(List routes){
//        NACOS_ROUTSE = routes;
//    }
//
//    @Value("${spring.cloud.nacos.discovery.server-addr}")
//    public void setNacosServerAddr(String nacosServerAddr){
//        NACOS_SERVER_ADDR = nacosServerAddr;
//    }
//
//    @Value("${spring.cloud.nacos.discovery.namespace}")
//    public void setNacosNamespace(String nacosNamespace){
//        NACOS_NAMESPACE = nacosNamespace;
//    }
//
//    @Value("${nacos.gateway.route.config.data-id}")
//    public void setNacosRouteDataId(String nacosRouteDataId){
//        NACOS_ROUTE_DATA_ID = nacosRouteDataId;
//
//    }
//
//    @Value("${spring.cloud.nacos.config.group}")
//    public void setNacosRouteGroup(String nacosRouteGroup){
//        NACOS_ROUTE_GROUP = nacosRouteGroup;
//    }
//}
