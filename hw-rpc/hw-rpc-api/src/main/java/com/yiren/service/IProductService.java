package com.yiren.service;

import com.yiren.service.bean.Product;

/**
 * @author wanghao
 * create 2017-12-24 16:49
 **/
public interface IProductService {
    Product queryById(Long id);
}
