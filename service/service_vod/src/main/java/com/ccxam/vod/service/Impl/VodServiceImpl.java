package com.ccxam.vod.service.Impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.ccxam.vod.service.VodService;
import com.ccxam.vod.utils.ConstantVodUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static com.ccxam.vod.entity.InitObject.initVodClient;

@Service
public class VodServiceImpl  implements VodService {
    @Override
    public String uploadVideoAly(MultipartFile file) {
        try {
            String accessKeyId= ConstantVodUtils.ACESSKEY_ID;
            String accessKeySecret=ConstantVodUtils.ACESSKEY_SECRET;
            String fileName=file.getOriginalFilename();
            String title=fileName.substring(0,fileName.lastIndexOf("."));
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);
            /* 是否使用默认水印(可选)，指定模板组ID时，根据模板组配置确定是否使用默认水印*/
            //request.setShowWaterMark(true);
            /* 设置上传完成后的回调URL(可选)，建议通过点播控制台配置消息监听事件，参见文档 https://help.aliyun.com/document_detail/57029.html */
            //request.setCallback("http://callback.sample.com");
            /* 自定义消息回调设置，参数说明参考文档 https://help.aliyun.com/document_detail/86952.html#UserData */
            //request.setUserData(""{\"Extend\":{\"test\":\"www\",\"localId\":\"xxxx\"},\"MessageCallback\":{\"CallbackURL\":\"http://test.test.com\"}}"");
            /* 视频分类ID(可选) */
            //request.setCateId(0);
            /* 视频标签,多个用逗号分隔(可选) */
            //request.setTags("标签1,标签2");
            /* 视频描述(可选) */
            //request.setDescription("视频描述");
            /* 封面图片(可选) */
            //request.setCoverURL("http://cover.sample.com/sample.jpg");
            /* 模板组ID(可选) */
            //request.setTemplateGroupId("8c4792cbc8694e7084fd5330e56a33d");
            /* 工作流ID(可选) */
            //request.setWorkflowId("d4430d07361f0*be1339577859b0177b");
            /* 存储区域(可选) */
            //request.setStorageLocation("in-201703232118266-5sejdln9o.oss-cn-shanghai.aliyuncs.com");
            /* 开启默认上传进度回调 */
            // request.setPrintProgress(true);
            /* 设置自定义上传进度回调 (必须继承 VoDProgressListener) */
            // request.setProgressListener(new PutObjectProgressListener());
            /* 设置应用ID*/
            //request.setAppId("app-1000000");
            /* 点播服务接入点 */
            //request.setApiRegionId("cn-shanghai");
            /* ECS部署区域*/
            // request.setEcsRegionId("cn-shanghai");
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            String videoId=null;
            if (response.isSuccess()) {
                videoId= response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId= response.getVideoId();
            }
            return videoId;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }

    @Override
    public void deleteAlById(String videoId) {
        try {
            String accessKeyId=ConstantVodUtils.ACESSKEY_ID;
            String accessKeySecret=ConstantVodUtils.ACESSKEY_SECRET;
            DefaultAcsClient client = initVodClient(accessKeyId, accessKeySecret);
            DeleteVideoResponse response = new DeleteVideoResponse();
            response = deleteVideo(client,videoId);
            System.out.print("RequestId = " + response.getRequestId() + "\n");
        } catch (ClientException e) {
            e.printStackTrace();
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //删除多个阿里云视频
    @Override
    public void deleteBatchAlById(List videoIdList) {
        try {
            String accessKeyId=ConstantVodUtils.ACESSKEY_ID;
            String accessKeySecret=ConstantVodUtils.ACESSKEY_SECRET;
            DefaultAcsClient client = initVodClient(accessKeyId, accessKeySecret);
            DeleteVideoResponse response = new DeleteVideoResponse();
            String str = StringUtils.join(videoIdList.toArray(),",");
            response = deleteVideo(client,str);
            System.out.print("RequestId = " + response.getRequestId() + "\n");
        } catch (ClientException e) {
            e.printStackTrace();
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }




    }


    public static DeleteVideoResponse deleteVideo(DefaultAcsClient client, String id) throws Exception {
        DeleteVideoRequest request = new DeleteVideoRequest();
        //支持传入多个视频ID，多个用逗号分隔
        request.setVideoIds(id);
        return client.getAcsResponse(request);
    }
}
