package com.springboottest.demo.provider;

import cn.ucloud.ufile.UfileClient;
import cn.ucloud.ufile.api.object.ObjectConfig;
import cn.ucloud.ufile.auth.ObjectAuthorization;
import cn.ucloud.ufile.auth.UfileObjectLocalAuthorization;
import cn.ucloud.ufile.bean.PutObjectResultBean;
import cn.ucloud.ufile.exception.UfileClientException;
import cn.ucloud.ufile.exception.UfileServerException;
import com.springboottest.demo.exception.CustomizeErrorCode;
import com.springboottest.demo.exception.CustomizeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

@Service
@Slf4j
public class UCloudProvider {
    @Value("${ucloud.ufile.public-key}")
    private String publicKey;
    @Value("${ucloud.ufile.private-key}")
    private String privateKey;
    @Value("${ucloud.ufile.bucketName}")
    private String bucketName;


    public String upload(InputStream fileStream,String mimeType,String fileName){
        String generateFileName;
        String[] fileNameSplit = fileName.split("\\.");
        if (fileNameSplit.length>1){
            generateFileName= UUID.randomUUID().toString()+"."+fileNameSplit[fileNameSplit.length-1];
        }else {
            return null;
        }

        try {
            // Bucket相关API的授权器
            ObjectAuthorization objectAuthorizer = new UfileObjectLocalAuthorization(publicKey, privateKey);
            // 对象操作需要ObjectConfig来配置您的地区和域名后缀
            ObjectConfig config = new ObjectConfig("cn-bj", "ufileos.com");
            PutObjectResultBean response = UfileClient.object(objectAuthorizer, config)
                    .putObject(fileStream, mimeType)
                    .nameAs(generateFileName)
                    .toBucket(bucketName)
                    /**
                     * 是否上传校验MD5, Default = true
                     */
                    //  .withVerifyMd5(false)
                    /**
                     * 指定progress callback的间隔, Default = 每秒回调
                     */
                    //  .withProgressConfig(ProgressConfig.callbackWithPercent(10))
                    /**
                     * 配置进度监听
                     */
                    .setOnProgressListener((bytesWritten,contentLength)->{
                    })
                    .execute();
                    if (response!=null&&response.getRetCode()==0){//如果上传成功
                        String url=UfileClient.object(objectAuthorizer,config)
                                .getDownloadUrlFromPrivateBucket(generateFileName,bucketName,24*60*60*365*10)
                                .createUrl();
                        return url;
                    }else {
                        throw new CustomizeException(CustomizeErrorCode.FILE_UPLOAD_FAIL);
                    }
        } catch (UfileClientException e) {
            log.error("上传图片出错");
            e.printStackTrace();
            return null;
        } catch (UfileServerException e) {
            log.error("上传图片出错");
            e.printStackTrace();
        }
        return generateFileName;
    }
}
