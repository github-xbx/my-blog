//package com.xingbingxuan.blog.gateway.config;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.nacos.api.NacosFactory;
//import com.alibaba.nacos.api.config.ConfigService;
//import com.alibaba.nacos.api.config.listener.Listener;
//import com.alibaba.nacos.api.exception.NacosException;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.route.RouteDefinition;
//import org.springframework.context.annotation.DependsOn;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.util.List;
//import java.util.Properties;
//import java.util.concurrent.Executor;
//
///**
// * 通过nacos下发动态路由配置,监听Nacos中gateway-route配置
// * @author : xbx
// * @date : 2022/7/24 16:17
// */
////@Component
////@Slf4j
////@DependsOn({"gatewayNacosConfig"}) // 依赖于gatewayConfig bean
//public class DynamicRouteServiceImplByNacos {
//
//    @Autowired
//    private DynamicRouteServiceImpl dynamicRouteService;
//
//
//    private ConfigService configService;
//
//    @PostConstruct
//    public void init() {
//        log.info("gateway route init...");
//        try{
//            configService = initConfigService();
//            if(configService == null){
//                log.warn("initConfigService fail");
//                return;
//            }
//            String configInfo = configService.getConfig(GatewayNacosConfig.NACOS_ROUTE_DATA_ID, GatewayNacosConfig.NACOS_ROUTE_GROUP, GatewayNacosConfig.DEFAULT_TIMEOUT);
//            log.info("获取网关当前配置:\r\n{}",configInfo);
//
//            List<RouteDefinition> definitionList = JSON.parseArray(configInfo, RouteDefinition.class);
//            for(RouteDefinition definition : definitionList){
//                log.info("update route : {}",definition.toString());
//                dynamicRouteService.add(definition);
//            }
//        } catch (Exception e) {
//            log.error("初始化网关路由时发生错误",e);
//        }
//        dynamicRouteByNacosListener(GatewayNacosConfig.NACOS_ROUTE_DATA_ID,GatewayNacosConfig.NACOS_ROUTE_GROUP);
//    }
//
//    /**
//     * 监听Nacos下发的动态路由配置
//     * @param dataId
//     * @param group
//     */
//    public void dynamicRouteByNacosListener (String dataId, String group){
//        try {
//            configService.addListener(dataId, group, new Listener()  {
//                @Override
//                public void receiveConfigInfo(String configInfo) {
//                    log.info("进行网关更新:\n\r{}",configInfo);
//                    List<RouteDefinition> definitionList = JSON.parseArray(configInfo, RouteDefinition.class);
//                    log.info("update route : {}",definitionList.toString());
//                    dynamicRouteService.updateList(definitionList);
//                }
//                @Override
//                public Executor getExecutor() {
//                    log.info("getExecutor\n\r");
//                    return null;
//                }
//            });
//        } catch (NacosException e) {
//            log.error("从nacos接收动态路由配置出错!!!",e);
//        }
//    }
//
//
//    /**
//     * 初始化网关路由 nacos config
//     * @return
//     */
//    private ConfigService initConfigService(){
//        try{
//            Properties properties = new Properties();
//            properties.setProperty("serverAddr",GatewayNacosConfig.NACOS_SERVER_ADDR);
//            properties.setProperty("namespace",GatewayNacosConfig.NACOS_NAMESPACE);
//            return configService= NacosFactory.createConfigService(properties);
//        } catch (Exception e) {
//            log.error("初始化网关路由时发生错误",e);
//            return null;
//        }
//    }
//}
