package com.tan.ec.service.impl;

import com.tan.ec.constants.KdniaoConstant;
import com.tan.ec.service.IKdniaoService;
import com.tan.ec.utils.EncryptUtil;
import com.tan.ec.utils.HttpUtil;
import com.tan.ec.utils.KdniaoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2019/5/17/017.
 */
@Service
@Slf4j
public class KdniaoServiceImpl implements IKdniaoService {

    /**
     * Json方式 查询订单物流轨迹
     * @throws Exception
     */
    @Override
    public String getOrderTracesByJson(String expCode, String expNo) throws Exception{
        KdniaoUtil api = new KdniaoUtil();
        return  api.getOrderTracesByJson(expCode,expNo);
    }
}
