package com.zjq.library_01.service;

import com.zjq.library_01.domain.Log;
import com.zjq.library_01.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LogService {
    @Autowired
    private LogRepository logRepository;
    public Map<String,Object> getAll(Pageable pageable){
        Map<String,Object> result=new HashMap<>();
        result.put("logs",logRepository.findAllByOrderByActiontimeDesc(pageable));
        result.put("count",logRepository.countAllBy());
        return result;
    }
    public int addLog(Log log){
        return logRepository.addLog(log.getLibrarian_num(),log.getAction(),log.getAction_time(),log.getBook_id());
    }
}
