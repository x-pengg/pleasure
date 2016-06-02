package me.ridog.pleasure.service;

import me.ridog.pleasure.util.qiniu.QiNiuUtil;
import me.ridog.pleasure.util.qiniu.QiNiuPicResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author: Tate
 * @date: 2016/5/26 15:12
 */

@Service
public class DataService {

    @Autowired
    private QiNiuUtil qiNiuUtil;

    private static final Logger logger = LoggerFactory.getLogger(DataService.class);

    public List<QiNiuPicResp> uploadPicToQiNiu(MultipartFile[] files) {
        List<QiNiuPicResp> resList = null;
        try {
            List<FutureTask<QiNiuPicResp>> fList = new ArrayList<FutureTask<QiNiuPicResp>>();
            for (MultipartFile file : files) {
                FutureTask<QiNiuPicResp> futureTask = new FutureTask<QiNiuPicResp>(new Callable<QiNiuPicResp>() {
                    @Override
                    public QiNiuPicResp call() throws Exception {
                        return qiNiuUtil.upload(file.getBytes(), UUID.randomUUID().toString());
                    }
                });
                fList.add(futureTask);
                new Thread(futureTask).start();
            }
            resList = new ArrayList<>();
            for (FutureTask<QiNiuPicResp> futureTask : fList) {
                resList.add(futureTask.get());
            }
        } catch (Exception e) {
            logger.error("上传文件错误", e);
        }
        return resList;
    }

}
