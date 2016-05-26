package me.ridog.pleasure.util.qiniu;



import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

import java.io.File;

/**
 * @author: Tate
 * @date: 2016/5/26 10:55
 */
public class QiNiuUtil {

    private String accessKey;
    private String secretKey;
    private String domain;
    private String bucketName;
    private Auth auth;
    private UploadManager uploadManager;
    private String key;

    public QiNiuUtil(String accessKey, String secretKey, String bucketName, String domain) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.bucketName = bucketName;
        this.domain = domain;
        this.auth = Auth.create(accessKey, secretKey);
    }

    public UploadManager getUploadManager() {
        return uploadManager;
    }

    public void setUploadManager(UploadManager uploadManager) {
        this.uploadManager = uploadManager;
    }

    private String getUpToken() {
        return auth.uploadToken(this.bucketName, (String) null, 3600, new StringMap().putNotEmpty("returnBody",
                "{\"key\": $(key), \"hash\": $(etag), \"width\": $(imageInfo.width), \"height\": $(imageInfo.height)}"));
    }

    public QiNiuPicResp upload(File file, String key) throws QiniuException {
        String token = getUpToken();
        Response res = uploadManager.put(file, key, token);
        QiNiuPicResp qiNiuPicResp = res.jsonToObject(QiNiuPicResp.class);
        return qiNiuPicResp;
    }

    public QiNiuPicResp upload(byte[] bytes, String key) throws QiniuException {
        String token = getUpToken();
        Response res = uploadManager.put(bytes, key, token);
        QiNiuPicResp qiNiuPicResp = res.jsonToObject(QiNiuPicResp.class);
        qiNiuPicResp.setUrl(this.domain + "/" + qiNiuPicResp.getKey());
        return qiNiuPicResp;
    }
}
