package com.nhuttran.shopapp_ecom.service;

import com.nhuttran.shopapp_ecom.dto.ProductDTO;
import com.nhuttran.shopapp_ecom.dto.ProductImageDTO;
import com.nhuttran.shopapp_ecom.exception.DataNotFoundExeption;
import com.nhuttran.shopapp_ecom.exception.InvalidParamException;
import com.nhuttran.shopapp_ecom.model.CategoryEntity;
import com.nhuttran.shopapp_ecom.model.ProductEntity;
import com.nhuttran.shopapp_ecom.model.ProductImageEntity;
import com.nhuttran.shopapp_ecom.repository.CategoryRepository;
import com.nhuttran.shopapp_ecom.repository.ProductImageRepository;
import com.nhuttran.shopapp_ecom.repository.ProductRepository;
import com.nhuttran.shopapp_ecom.respone.ProductRespone;
import com.nhuttran.shopapp_ecom.service.Impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductServiceImpl {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Override
    public ProductEntity createProduct(ProductDTO productDTO) throws DataNotFoundExeption {
        CategoryEntity existingCategory = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(() -> new DataNotFoundExeption("can not find category with id" + productDTO.getCategoryId()));
        ProductEntity newProduct = ProductEntity.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .thumbnail(productDTO.getThumbnail())
                .description(productDTO.getDescription())
                .category(existingCategory)
                .build();
        return productRepository.save(newProduct);
    }

    @Override
    public ProductEntity getProductById(long productId) throws Exception {
        return productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundExeption("can not find product with id" + productId));
    }

    @Override
    public Page<ProductRespone> getAllProduct(PageRequest pageRequest) {
        // page, limit

        return productRepository.findAll(pageRequest).map(ProductRespone::fromProduct);
    }

    @Override
    public ProductEntity updateProduct(long id, ProductDTO productDTO) throws Exception {
        ProductEntity existingProduct = getProductById(id);
        if(existingProduct != null){
            existingProduct.setName(productDTO.getName());
            CategoryEntity existingCategory = categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(() -> new DataNotFoundExeption("can not find category with id" + productDTO.getCategoryId()));
            existingProduct.setName(productDTO.getName());
            existingProduct.setCategory(existingCategory);
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setThumbnail(productDTO.getThumbnail());
            return productRepository.save(existingProduct);
        }
        return null;

    }

    @Override
    public void deleteProduct(long id) {
        Optional<ProductEntity> optionProduct = productRepository.findById(id);
        optionProduct.ifPresent(productRepository::delete);

    }

    @Override
    public boolean existByName(String name) {
        return productRepository.existsByName(name);
    }
    @Override
    public ProductImageEntity createProductImage(Long productId, ProductImageDTO productImageDTO) throws DataNotFoundExeption, InvalidParamException {
        ProductEntity existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundExeption("can not find product with id " + productImageDTO.getProductId()));
        ProductImageEntity newProductImage = ProductImageEntity
                .builder()
                .product(existingProduct)
                .imageUrl(productImageDTO.getImageUrl())
                .build();

        // insert khong qua 5 images
        int size = productImageRepository.findByProductId(productId).size();
        if(size >= ProductImageEntity.MAXIMUM_IMAGES_PER_PRODUCT){
            throw new InvalidParamException("Number of image must be =< " + ProductImageEntity.MAXIMUM_IMAGES_PER_PRODUCT);
        }
        return productImageRepository.save(newProductImage);
    }
}
