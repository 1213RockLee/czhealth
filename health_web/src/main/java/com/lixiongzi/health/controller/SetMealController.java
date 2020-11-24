package com.lixiongzi.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lixiongzi.health.Exception.MyException;
import com.lixiongzi.health.constant.MessageConstant;
import com.lixiongzi.health.entity.PageResult;
import com.lixiongzi.health.entity.QueryPageBean;
import com.lixiongzi.health.entity.Result;
import com.lixiongzi.health.pojo.Setmeal;
import com.lixiongzi.health.service.SetmealService;
import com.lixiongzi.health.utils.QiNiuUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetMealController {
    @Reference
    private SetmealService setmealService;

    @RequestMapping("/upload")
    public Result upload(MultipartFile imgFile) {
        try {
            //获取文件的名字,截取后缀
            String originalFilename = imgFile.getOriginalFilename();
            String endWith = originalFilename.substring(originalFilename.lastIndexOf("."));
            //生成随机名字
            String FileName = UUID.randomUUID().toString() + endWith;
            //上传
            QiNiuUtils.uploadViaByte(imgFile.getBytes(), FileName);
            //返回,存到map集合
            HashMap<String, String> resultMap = new HashMap<>();
            resultMap.put("address", QiNiuUtils.DOMAIN);
            resultMap.put("imgName", FileName);
            return new Result(true, MessageConstant.UPLOAD_SUCCESS, resultMap);
        } catch (IOException e) {
            e.printStackTrace();
            throw new MyException("上传图片失败");
        }
    }

    @RequestMapping("/addSetmeal")
    public Result addSetmeal(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        setmealService.addSetmeal(setmeal, checkgroupIds);
        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS, null);
    }

    @RequestMapping("/findByPage")
    public Result findByPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult<Setmeal> setmealPageResult = setmealService.findByPage(queryPageBean);
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmealPageResult);
    }

    @RequestMapping("/findSetmealById")
    public Result findSetmealById(int id) {
        Map resultMap = setmealService.findSetmealById(id);
        resultMap.put("address", QiNiuUtils.DOMAIN);
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, resultMap);
    }

    @RequestMapping("/updateSetmeal")
    public Result updateSetmeal(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        setmealService.updateSetmeal(setmeal, checkgroupIds);
        return new Result(true, "更新数据成功", null);
    }

    @RequestMapping("/deleteSetmealById")
    public Result deleteSetmealById(int id) {
        setmealService.deleteSetmealById(id);
        return new Result(true, "删除成功", null);
    }
}
