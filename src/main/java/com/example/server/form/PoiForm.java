package com.example.server.form;

import com.example.server.pojo.Pic;
import lombok.Data;

import java.util.List;
@Data
public class PoiForm {//add
    private Integer id;
    private String name;
    private String description;
    private float lng;
    private float lat;
    private String cover;
    private List<Pic> pics;
}
