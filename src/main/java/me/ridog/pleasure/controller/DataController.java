package me.ridog.pleasure.controller;

import me.ridog.pleasure.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: Tate
 * @date: 2016/5/26 15:37
 */
@RestController
public class DataController {

    @Autowired
    private DataService dataService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void uploadTest(MultipartFile ... file) {
        for (MultipartFile multipartFile : file) {
            dataService.uploadFile(multipartFile);
        }
    }
}
