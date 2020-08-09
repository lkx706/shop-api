package com.fh.service;

import com.fh.model.Brand;

import java.util.List;

public interface BrandService {
  List<Brand> queryBrandList();

  void addBrand(Brand brand);

  void updateBrand(Brand brand);

  void deleteBrand(Integer id);

  List<Brand> queryBrandStock();
}
