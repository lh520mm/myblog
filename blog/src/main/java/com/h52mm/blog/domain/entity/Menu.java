package com.h52mm.blog.domain.entity;

import java.io.Serializable;

public class Menu implements Serializable {
    private static final long serialVersionUID = -1L;
    private Integer id;
    private String name;
    private Integer lev;
    private String uri;
    private Integer parentId;
    private String icon;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLev() {
        return lev;
    }

    public void setLev(Integer lev) {
        this.lev = lev;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

}
