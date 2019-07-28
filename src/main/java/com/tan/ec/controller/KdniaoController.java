package com.tan.ec.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.luhuiguo.fastdfs.domain.StorePath;
import com.luhuiguo.fastdfs.service.FastFileStorageClient;
import com.tan.ec.bean.config.FdfsPropertiesConfig;
import com.tan.ec.service.IKdniaoService;
import com.tan.ec.utils.R;
import com.xiaoleilu.hutool.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tan
 * @since 2019-04-23
 */
@RestController
@Slf4j
@RequestMapping("/kdniao")
public class KdniaoController extends BaseController {

    @Autowired
    private IKdniaoService kdniaoService;


    /**
     * 查询物流信息
     * @return
     */
    @GetMapping("/expInfo")
    public R<JSONObject> upload(@RequestParam Map<String,String> param) {

        Object shipperObj = param.get("ShipperCode");
        Object logisticObj = param.get("LogisticCode");

        log.debug(shipperObj.toString());
        log.debug(logisticObj.toString());

        if(shipperObj==null||logisticObj==null){
            return new R<>(null,false,"参数有误");
        }

        try {
            String ret = kdniaoService.getOrderTracesByJson(shipperObj.toString(),logisticObj.toString());
            JSONObject jsonObject = JSONObject.parseObject(ret);

//            if(jsonObject.containsKey("ShipperCode")){
//                String ShipperCode = jsonObject.getString("ShipperCode");
//                String LogisticCode = jsonObject.getString("LogisticCode");
//                JSONArray Traces = jsonObject.getJSONArray("Traces");
//
//                int count = 1;
//                for(int i = 0; i < Traces.size(); i++) {
//                    JSONObject object = (JSONObject) Traces.get(i);
//                    String AcceptTime = object.getString("AcceptTime");
//                    String AcceptStation = object.getString("AcceptStation");
//                    System.out.println("时间："+AcceptTime+"\t"+AcceptStation);
//                    map.put("时间"+count,AcceptTime+AcceptStation);
//                    count++;
//                }
//
//            }

            return new R<>(jsonObject);
        } catch (Exception e) {
            return new R<>(null,false,e.getMessage());
        }

    }

}

