package com.fh.controller;
import com.fh.model.Brand;
import com.fh.service.BrandService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/brand")
@Api(tags = "地区接口")
public class BrandController {
  @Autowired
  private BrandService brandService;

  @RequestMapping("stockJob")
  public List<Brand> stockJob(){
   return brandService.queryBrandStock();
  }

  @RequestMapping("queryBrandList")
  public List<Brand> queryBrandList(){
    return brandService.queryBrandList();
  }
  @RequestMapping("addBrand")
  public void addBrand(@RequestBody Brand brand){
    brand.setCreateDate(new Date());
    brandService.addBrand(brand);
  }
  @RequestMapping("updateBrand")
  public void updateBrand(@RequestBody Brand brand){
    brand.setUpdateDate(new Date());
    brandService.updateBrand(brand);
  }
  @RequestMapping("deleteBrand")
  public void deleteBrand(Integer id){
    brandService.deleteBrand(id);
  }

  /*@RequestMapping("upload")
  public Map upload(MultipartFile file, HttpServletRequest request) throws IOException {
    Map map = new HashMap();
    if(file!=null){
      String originalFilename = file.getOriginalFilename();//文件原名称
      InputStream inputStream = file.getInputStream();
      //获取相对路径
      String realPath = request.getServletContext().getRealPath("/");
      String filePath = FileUtil.copyFile(inputStream, originalFilename, realPath+"upload");
      map.put("url","upload/"+filePath);
      map.put("name",filePath);

    }
    return map;
  }*/


}
