package com.example.server.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.server.exception.PoiException;
import com.example.server.mapper.PicMapper;
import com.example.server.mapper.PoiMapper;
import com.example.server.pojo.Pic;
import com.example.server.pojo.Poi;
import com.example.server.service.IPoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service//与@Autowired配合使用
public class PoiServiceImpl extends ServiceImpl<PoiMapper, Poi> implements IPoiService {

    @Autowired
    private PoiMapper poiMapper;
    @Autowired
    private PicMapper picMapper;
    @Override
    public void saveMain(Poi poi, List<Pic> pics) {
        int row=poiMapper.insert(poi);
        if(row==0){
            throw PoiException.OperateFail();
        }
        if(pics != null){
            for(Pic pic:pics){
                pic.setPoiId(poi.getId());
                row=picMapper.insert(pic);
                if(row==0){
                    throw PoiException.OperateFail();
                }
            }
        }
    }
    public void deleteMain(Integer id){
        int row=poiMapper.deleteById(id);

        if(row==0){
            throw PoiException.OperateFail();
        }
        row=picMapper.deleteByPoiId(id);
        if(row==0){
            throw PoiException.OperateFail();
        }
    }

    @Override
    public void updateMain(Poi poi, List<Pic> pics) {
        int row=poiMapper.updateById(poi);
        if(row==0){
            throw PoiException.OperateFail();
        }
        picMapper.deleteByPoiId(poi.getId());
        if(pics != null){
            for(Pic pic:pics){
                pic.setPoiId(poi.getId());
                row=picMapper.insert(pic);
                if(row==0){
                    throw PoiException.OperateFail();
                }
            }
        }
    }
}
