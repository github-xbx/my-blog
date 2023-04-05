package com.xingbingxuan.blog.gateway.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author : xbx
 * @date : 2023/4/5 15:08
 */
@Table(name = "role")
@Entity
@Data
public class Role {

    @Id
    @Column(name = "role_id")
    private Integer roleId;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "role_code")
    private String roleCode;

    @Column(name = "role_description")
    private String roleDescription;


    @ManyToMany(mappedBy = "roles")
    private Set<RequestUrl> requestUrls = new HashSet<RequestUrl>();

}
