package com.example.server.controllers;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.server.exception.PoiException;
import com.example.server.form.PoiForm;
import com.example.server.mapper.PoiMapper;
import com.example.server.pojo.Pic;
import com.example.server.pojo.Poi;
import com.example.server.service.IPicService;
import com.example.server.service.IPoiService;
import com.example.server.vo.PoiVo;
import com.example.server.vo.Result;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j//控制台打印注解
@RequestMapping("/poi")
public class PoiController {
    @Autowired
    private IPoiService poiService;
    @Autowired
    private IPicService picService;
    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue ="1" ) int pageNum, @RequestParam(defaultValue = "30") int pageSize){
       log.info("poi list,pageNum={},pageSize={}",pageNum,pageSize);
        Page<Poi> page=new Page<Poi>(pageNum,pageSize);
        IPage<Poi> pageResult= poiService.page(page);

//        List<Poi> poiList=pageResult.getRecords();
//        List voList=new ArrayList();
//        for(Poi poi:poiList){
//            PoiVo poiVo=new PoiVo();
//            BeanUtils.copyProperties(poi,poiVo);//将poi中的数据匹配给poiVo
//            voList.add(poiVo);
//        }
        List voList=pageResult.getRecords().stream().map( poi->{
            PoiVo poiVo=new PoiVo();
            QueryWrapper query=new QueryWrapper();
            query.eq("poi_id",poi.getId());
            List<Pic> pics =picService.list(query);
            BeanUtils.copyProperties(poi,poiVo);//将一个数据表数据给poiVo
            poiVo.setPics(pics);//设置将另一个数据表的数据给poiVo
            log.info("poiVo={}",poiVo);
            return poiVo;//打包成poi的形式
        }).collect(Collectors.toList());
        pageResult.setRecords(voList);
        log.info("poi list,voList={}",voList);
        log.info("pageResult={}",pageResult);
        return Result.success(voList);//服务器发往前端
    }
    @GetMapping("/detail/{id}")
    public Result detail(@PathVariable int id){
        log.info("poi detail,id={}",id);//client-->server

        PoiVo poiVo=new PoiVo();
        Poi poi=poiService.getById(id);//从数据库中查找数据
        //是否存在异常
        if(poi==null){
            throw PoiException.NotFound();
        }
        QueryWrapper query=new QueryWrapper();
        query.eq("poi_id",poi.getId());//查找另一个数据表的数据
        List<Pic> pics=picService.list(query);//获得另一个表的数据
        //server-->client
        BeanUtils.copyProperties(poi,poiVo);
        poiVo.setPics(pics);//整合数据
        return Result.success(poiVo);
    }
    @PostMapping("/add")
    public Result add(@RequestBody PoiForm poiForm){
        log.info("poiForm add",poiForm);//client-->server
        Poi poi=new Poi();
        BeanUtils.copyProperties(poiForm,poi);
        poiService.saveMain(poi,poiForm.getPics());//保存到数据库

//        PoiVo poiVo=new PoiVo();
//        BeanUtils.copyProperties(poi,poiVo);
//        return Result.success(poiVo);
        return detail(poi.getId());
    }
    @PutMapping("/edit/{id}")
    public Result edit(@RequestBody PoiForm poiForm,@PathVariable int id){
        log.info("poiForm edit,poiForm={}",poiForm);
        Poi poi=new Poi();
        BeanUtils.copyProperties(poiForm,poi);//将body数据部分给poi中
        poi.setId(id);
        poiService.updateMain(poi,poiForm.getPics());
        return Result.success();
    }
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable int id){
        log.info("poi delete,id={}",id);
        poiService.deleteMain(id);
        return Result.success();
    }
}
