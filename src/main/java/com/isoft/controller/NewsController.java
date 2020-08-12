package com.isoft.controller;

import com.isoft.bean.Page;
import com.isoft.bean.ResponseData;
import com.isoft.entity.News;
import com.isoft.service.NewsService;
import com.isoft.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/sysnews")
public class NewsController {
    @Autowired
    NewsService newsService;
    String newsPhoto="newsphoto/";

    @GetMapping
    public Map<String,Object> getPage(Integer pageSize,Integer pageNumber,String sortName,String sortOrder, String title,
                                      Integer typeid,String dt){
        Page<News> page=newsService.getData(title,typeid,dt,pageNumber,pageSize,sortName,sortOrder);
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("total",page.getTotal());
        map.put("rows",page.getRecords());
        return map;
    }

    @DeleteMapping("ids")
    public  ResponseData dels(Integer[] id){
        System.out.println(id.length);
        boolean r=newsService.del(Arrays.asList(id));
        return new ResponseData(
                r ? 0:1,
                r ? "删除成功":"删除失败",
                r
        );
    }

    @DeleteMapping
    public  ResponseData delone(Integer id){
        System.out.println(id);
        boolean r=newsService.delone(id);
        return new ResponseData(
                r ? 0:1,
                r ? "删除成功":"删除失败",
                r
        );
    }

    //添加新闻时上传图片
    @PostMapping("newsimg")
    public ResponseData upNewsInmg(MultipartFile file, HttpServletRequest request, HttpServletResponse response){
        if (null==file){
            return new ResponseData(-1,"上传文件为空",null);
        }
        String oriFilename =file.getOriginalFilename();
        String extName=oriFilename.substring(oriFilename.lastIndexOf("."));
        String newFileName= new SimpleDateFormat("yyyyMMddHHmmssSSSS").format(new Date())+"_"+(new Random().nextInt(8999)+1000) + extName;


        boolean isOk= FileUtil.saveUpFile(request,newsPhoto,newFileName,file);

        String photoUrl=null;
        if (isOk) {
            photoUrl = FileUtil.url(request, newsPhoto, newFileName);
        }
        ResponseData responseData=new ResponseData();
        responseData.setErrCode(isOk?0:-1);
        responseData.setMsg(isOk?"上传成功":"上传失败");
        responseData.setData(photoUrl);
        return responseData;
    }

    @PostMapping
    public ResponseData add(News news){
        boolean r=newsService.add(news);
        return new ResponseData(
                r ? 0:1,
                r ? "添加成功":"添加失败",
                r
        );
    }

    @PutMapping
    public ResponseData update(@RequestBody News news){
        boolean r=newsService.update(news);
        return new ResponseData(
                r ? 0:1,
                r ? "修改成功":"修改失败",
                r
        );
    }
}
