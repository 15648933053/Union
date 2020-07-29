package com.starcpdk.edu.eduservice.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.starcpdk.edu.eduservice.entity.Active;
import com.starcpdk.edu.eduservice.entity.excel.ActiveInfo;
import com.starcpdk.edu.eduservice.service.ActiveService;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static List<ActiveInfo> getData(ActiveService activeService) {
        QueryWrapper<Active> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("date");
        List<Active> activeList = activeService.list(wrapper);
        List<ActiveInfo> activeInfoList = new ArrayList<>();
        for (Active active : activeList) {
            ActiveInfo activeInfo = new ActiveInfo();
            BeanUtils.copyProperties(active, activeInfo);
            activeInfoList.add(activeInfo);
        }
        return activeInfoList;
    }
}
