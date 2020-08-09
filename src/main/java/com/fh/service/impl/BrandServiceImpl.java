package com.fh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.mapper.BrandMapper;
import com.fh.model.Brand;
import com.fh.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
  @Autowired
  private BrandMapper brandMapper;

  @Override
  public List<Brand> queryBrandList() {
    return brandMapper.selectList(null);
  }

  @Override
  public void addBrand(Brand brand) {
    brandMapper.insert(brand);
  }

  @Override
  public void updateBrand(Brand brand) {
    brandMapper.updateById(brand);
  }

  @Override
  public void deleteBrand(Integer id) {
    brandMapper.deleteById(id);
  }

  @Override
  public List<Brand> queryBrandStock() {
    QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("isHot","是");
    List<Brand> brandList = brandMapper.selectList(queryWrapper);
    return brandList;
  }

}
