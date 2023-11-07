package com.nhuttran.shopapp_ecom.service.Impl;

import com.nhuttran.shopapp_ecom.dto.ProductDTO;
import com.nhuttran.shopapp_ecom.dto.ProductImageDTO;
import com.nhuttran.shopapp_ecom.exception.DataNotFoundExeption;
import com.nhuttran.shopapp_ecom.exception.InvalidParamException;
import com.nhuttran.shopapp_ecom.model.ProductEntity;
import com.nhuttran.shopapp_ecom.model.ProductImageEntity;
import com.nhuttran.shopapp_ecom.respone.ProductRespone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ProductServiceImpl {
    ProductEntity createProduct(ProductDTO productDTO) throws Exception;
    ProductEntity getProductById(long id) throws Exception;
    Page<ProductRespone> getAllProduct(PageRequest pageRequest);
    ProductEntity updateProduct(long id, ProductDTO productDTO) throws Exception;
    void deleteProduct(long id);
    boolean existByName(String name);

    ProductImageEntity createProductImage(Long productId, ProductImageDTO productImageDTO) throws DataNotFoundExeption, InvalidParamException;
}
