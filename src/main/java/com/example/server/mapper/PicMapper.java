package com.example.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.server.pojo.Pic;
import com.example.server.pojo.Poi;
import org.apache.ibatis.annotations.Param;

public interface PicMapper extends BaseMapper<Pic> {
    int deleteByPoiId(@Param("poiId") int poiId);
}
