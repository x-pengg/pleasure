package me.ridog.pleasure.service;

import me.ridog.pleasure.util.qiniu.QiNiuUtil;
import me.ridog.pleasure.util.qiniu.QiNiuPicResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @author: Tate
 * @date: 2016/5/26 15:12
 */

@Service
public class DataService {

    @Autowired
    private QiNiuUtil qiNiuUtil;

    private static final Logger logger = LoggerFactory.getLogger(DataService.class);

    public QiNiuPicResp uploadFile(MultipartFile multipartFile) {
        String key = UUID.randomUUID().toString();
        QiNiuPicResp upload = null;
        try {
            upload = qiNiuUtil.upload(multipartFile.getBytes(), key);
        } catch (IOException e) {
            logger.error("上传文件错误", e);
        }
        return upload;
    }

}
