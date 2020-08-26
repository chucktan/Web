package com.imooc.controller.center;

import com.imooc.controller.BaseController;
import com.imooc.controller.CookieUtils;

import com.imooc.controller.JsonUtils;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.center.CenterUserBo;
import com.imooc.resource.FileUpload;
import com.imooc.service.center.CenterUserService;
import com.imooc.utils.DateUtil;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @create 2020-08-25-16:09
 */
@Api(value = "用户信息接口",tags = {"用户信息相关接口"})
@RestController
@RequestMapping("userInfo")
public class CenterUserController extends BaseController {

    @Autowired
    private CenterUserService centerUserService;

    @Autowired
    private FileUpload fileUpload;

    @ApiOperation(value = "修改用户信息",notes = "修改用户信息",httpMethod = "POST")
    @PostMapping("/update")
    public IMOOCJSONResult update(
            @ApiParam(name = "userId",value = "用户Id",required = true)
            @RequestParam String userId,
            @RequestBody @Valid CenterUserBo centerUserBo,
            BindingResult result,
            HttpServletRequest request, HttpServletResponse response){

        //判断BingdingResult是否保存错误的验证信息，如果有，则直接return
        if (result.hasErrors()){
            Map<String,String> errorMap = getErrors(result);
            return IMOOCJSONResult.errorMap(errorMap);
        }
        Users userResult = centerUserService.updateUserInfo(userId,centerUserBo);

        userResult = setNullProperty(userResult);
        CookieUtils.setCookie(request,response,"user", JsonUtils.objectToJson(userResult),true);

        //TODO 后续要改，增加令牌token,会整合进redis,分布式会话
        return IMOOCJSONResult.ok(userResult);

    }

    @ApiOperation(value = "用户头像修改",notes = "用户头像修改",httpMethod = "POST")
    @PostMapping("/uploadFace")
    public IMOOCJSONResult uploadFace(
            @ApiParam(name = "userId",value = "用户Id",required = true)
            @RequestParam String userId,
            @ApiParam(name = "file",value = "用户头像",required = true)
            MultipartFile file,
            HttpServletRequest request,HttpServletResponse response){

        //定义头像保存的地址
//        String filespace = IMAGE_USER_FACE_LOCATION;
        String filespace = fileUpload.getImageUserFaceLocation();
        //在路径上为每一个用户增加一个userid,用于区分不同用户上传
        String uploadPathPrefix = File.separator + userId;
        //开始上传文件
        if (file != null){
            //获取文件上传的文件名称
            String fileName = file.getOriginalFilename();
            FileOutputStream fileOutputStream = null;
            if (!StringUtils.isBlank(fileName)){
                //文件重命名 imooc-face.png -> ["imooc-face","png"]
                String fileNameArr[] = fileName.split("\\.");

                //获取文件后缀名
                String suffix = fileNameArr[fileNameArr.length-1];

                //face-{userid}.png
                //文件名称重组 覆盖式上传，增量式：额外拼接当前时间
                String newFileName = "face-"+userId+"."+suffix;

                //上传的头像最终保存的位置
                String finalFacePath = filespace+uploadPathPrefix+File.separator+newFileName;

                //用于提供给Web服务访问的地址
                uploadPathPrefix +=("/"+newFileName);

                File outFile = new File(finalFacePath);
                if (outFile.getParentFile() != null){
                    //创建文件夹
                    outFile.getParentFile().mkdirs();
                }
                //文件输出保存到目录
                try {
                    fileOutputStream = new FileOutputStream(outFile);
                    InputStream inputStream = file.getInputStream();
                    IOUtils.copy(inputStream,fileOutputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        if (fileOutputStream !=null){
                            fileOutputStream.flush();
                            fileOutputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else {
            return  IMOOCJSONResult.errorMsg("上传文件不能为空!");
        }


        //获取图片服务地址
        String imageServerUrl = fileUpload.getImageServerUrl();

        //由于浏览器可能存在缓存的情况，所以在这里，我们需要加上时间戳来保证更新后的图片可以及时更新
        String finalUserFaceUrl = imageServerUrl + uploadPathPrefix+"?t="+ DateUtil.getCurrentDateString(DateUtil.DATE_PATTERN);
        //更新用户头像到数据库
        Users userResult = centerUserService.updateUserFace(userId,finalUserFaceUrl);


        userResult = setNullProperty(userResult);
        CookieUtils.setCookie(request,response,"user", JsonUtils.objectToJson(userResult),true);

        //TODO 后续要改，增加令牌token,会整合进redis,分布式会话

        return  IMOOCJSONResult.ok(userResult);
    }

    private Map<String, String> getErrors(BindingResult result){
        Map<String,String> errorMap = new HashMap<>();

        List<FieldError> errorList = result.getFieldErrors();
        for (FieldError error:errorList){
            //发生验证错误所对应的某一个属性
            String errorField = error.getField();
            //验证错误的信息
            String errorMsg = error.getDefaultMessage();

            errorMap.put(errorField,errorMsg);
        }

        return  errorMap;
    }

    private Users setNullProperty(Users userResult) {
        userResult.setPassword(null);
        userResult.setEmail(null);
        userResult.setMobile(null);
        userResult.setRealname(null);
        userResult.setBirthday(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);

        return userResult;

    }
}
