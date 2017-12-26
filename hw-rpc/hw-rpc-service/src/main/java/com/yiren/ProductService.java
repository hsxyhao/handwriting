package com.yiren;

import com.yiren.service.IProductService;
import com.yiren.service.bean.Product;

/**
 * @author wanghao
 * create 2017-12-24 17:25
 **/
public class ProductService implements IProductService {
    @Override
    public Product queryById(Long aLong) {
        Product product = new Product();
        product.setId(aLong);
        product.setName("电饭煲");
        product.setPrice(1300);
        return product;
    }
}
