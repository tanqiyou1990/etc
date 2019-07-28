package com.tan.ec.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.luhuiguo.fastdfs.domain.StorePath;
import com.luhuiguo.fastdfs.service.FastFileStorageClient;
import com.tan.ec.bean.config.FdfsPropertiesConfig;
import com.tan.ec.constants.CommonConstant;
import com.tan.ec.entity.SysUser;
import com.tan.ec.service.ISysUserService;
import com.tan.ec.utils.R;
import com.tan.ec.vo.UserLoginVo;
import com.xiaoleilu.hutool.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

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
@RequestMapping("/admin/common")
public class SysCommonController extends BaseController {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Autowired
    private FdfsPropertiesConfig fdfsPropertiesConfig;

    /**
     * 上传文件（新闻图片）
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(@RequestParam("myFile") MultipartFile file) {
        String fileExt = FileUtil.extName(file.getOriginalFilename());
        try {
            StorePath storePath = fastFileStorageClient.uploadFile(file.getBytes(), fileExt);
            return new R<>(fdfsPropertiesConfig.getFileHost() + storePath.getFullPath());
        } catch (IOException e) {
            log.error("文件上传异常", e);
            return new R<>(null,false,e.getMessage());
        }
    }

}

