package com.fh.job;
import com.fh.model.Brand;
import com.fh.service.BrandService;
import com.fh.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StockJob {

  @Autowired
  private BrandService brandService;
  @Autowired
  private MailUtil mailUtil;

  @Scheduled(cron = "0/20 * * * * ?")
  public void stockJob(){
    /*StringBuffer buffer = new StringBuffer();
    List<Brand> brandList = brandService.queryBrandStock();
    buffer.append("<table border=\"1\" cellpadding=\"0\" cellspacing=\"0\" bgcolor=\"aqua\">\n" +
      "    <thead>\n" +
      "        <td>品牌名</td>\n" +
      "        <td>是否热销</td>\n" +
      "        <td>图片</td>\n" +
      "    </thead>");
    for (Brand brand:brandList) {
      buffer.append("<tr>\n" +
        "      <td>"+brand.getBrandName()+"</td>\n" +
        "      <td>"+brand.getIsHot()+"</td>\n" +
        "      <td>"+brand.getFilePath()+"</td>\n" +
        "    </tr>");
    }
    buffer.append("</table>");
    String s = buffer.toString();
    mailUtil.createMail("2642476369@qq.com",s);
*/
  }
}
