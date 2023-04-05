package com.xingbingxuan.blog.gateway.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/** 
 * 请求信息表
 * @author : xbx
 * @date : 2023/4/5 14:55
 */
@Table(name = "request_url")
@Data
@Entity
public class RequestUrl {

    @Id
    @Column(name = "request_url_id")
    private Integer requestUrlId;

    @Column(name = "request_url",nullable = false)
    private String requestUrl;

    //是否需要验证权限
    @Column(name = "request_is_auth",nullable = false)
    private Boolean requestIsAuth;

    @Column(name = "request_url_describe",nullable = false)
    private String requestUrlDescribe;

    /**
     * 配置用户到角色的多对多关系
     *      配置多对多的映射关系
     *          1.声明表关系的配置
     *              @ManyToMany(targetEntity = Role.class)  //多对多
     *                  targetEntity：代表对方的实体类字节码
     *          2.配置中间表（包含两个外键）
     *                @JoinTable
     *                  name : 中间表的名称
     *                  joinColumns：配置当前对象在中间表的外键
     *                      @JoinColumn的数组
     *                          name：外键名
     *                          referencedColumnName：参照的主表的主键名
     *                  inverseJoinColumns：配置对方对象在中间表的外键
     */
    @ManyToMany(targetEntity = Role.class)
    @JoinTable(name = "request_url_role_relation",
            //joinColumns,当前对象在中间表中的外键
            joinColumns = {@JoinColumn(name = "request_url_id",referencedColumnName = "request_url_id")},
            //inverseJoinColumns，对方对象在中间表的外键
            inverseJoinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "role_id")})
    public Set<Role> roles = new HashSet<Role>();


}
