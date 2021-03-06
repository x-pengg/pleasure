package me.ridog.pleasure.controller;

import com.google.gson.Gson;
import me.ridog.pleasure.service.DataService;
import me.ridog.pleasure.util.qiniu.QiNiuPicResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author: Tate
 * @date: 2016/5/26 15:37
 */
@RestController
public class DataController {

    @Autowired
    private DataService dataService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String uploadTest(@RequestParam("files") MultipartFile[] files) {
        List<QiNiuPicResp> qiNiuPicResp = dataService.uploadPicToQiNiu(files);
        return new Gson().toJson(qiNiuPicResp);
    }

}
