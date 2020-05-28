package com.he.servicce;

import com.he.domain.SysLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface SysLogService {

    void save(SysLog sysLog) throws Exception;

    List<SysLog> findAll() throws Exception;
}
