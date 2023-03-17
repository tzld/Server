package com.example.server.vo;

import lombok.Data;

import java.util.List;

@Data
public class PoiVo {
    private Integer id;
    private String name;
    private String description;
    private float lng;
    private float lat;
    private String cover;
    private List pics;

}
