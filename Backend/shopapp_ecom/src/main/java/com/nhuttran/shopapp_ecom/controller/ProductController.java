package com.nhuttran.shopapp_ecom.controller;

import com.nhuttran.shopapp_ecom.dto.CategoryDTO;
import com.nhuttran.shopapp_ecom.dto.ProductDTO;
import com.nhuttran.shopapp_ecom.dto.ProductImageDTO;
import com.nhuttran.shopapp_ecom.model.ProductEntity;
import com.nhuttran.shopapp_ecom.model.ProductImageEntity;
import com.nhuttran.shopapp_ecom.respone.ProductListRespone;
import com.nhuttran.shopapp_ecom.respone.ProductRespone;
import com.nhuttran.shopapp_ecom.service.Impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("${v1API}/products")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;
    @GetMapping("")
    public ResponseEntity<?> getAllProduct(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        //tao pageable trang va limit
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by("createdAt").descending());
        Page<ProductRespone> productPage = productService.getAllProduct(pageRequest);

        // Lay tong so trang
        int totalPages = productPage.getTotalPages();
        List<ProductRespone> products = productPage.getContent();
        return new ResponseEntity<>(ProductListRespone.builder()
                .products(products)
                .totalPages(totalPages)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable long id) {

        return new ResponseEntity<>("product id" + id, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long id) {

        return new ResponseEntity<>("product delete successfully with id" + id, HttpStatus.OK);

    }

    @PostMapping("")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO productDTO,
//                                           @ModelAttribute("files") List<MultipartFile> files,
                                           BindingResult result
//                                ,@RequestPart("file") MultipartFile file
    ) {
        try {
            if (result.hasErrors()) {
                String errorMessage = result.getFieldError().getDefaultMessage();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            //Luu
            ProductEntity newProduct = productService.createProduct(productDTO);

            return ResponseEntity.ok(newProduct);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
//    {
//        "name" : "Iphone 15",
//            "price" : 2000000,
//            "description" : "",
//            "category_id" : 1
//    }

    @PostMapping(value = "uploads/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImages(
            @PathVariable("id") Long productId,
            @ModelAttribute("files") List<MultipartFile> files){

        try {
            ProductEntity existingProduct = productService.getProductById(productId);
            // check null
            files = files == null ? new ArrayList<MultipartFile>() : files;
            if(files.size() > ProductImageEntity.MAXIMUM_IMAGES_PER_PRODUCT)
                return ResponseEntity.badRequest().body("You can only upload maximum 5 images");
            List<ProductImageEntity> productImages = new ArrayList<>();
            for (MultipartFile file: files) {
                // Kiem tra nguoi dung khong upload file nao
                if(file.getSize() == 0)
                    return ResponseEntity.badRequest().body("You do not upload anyfiles");
//                    continue;
                if (file.getSize() > 10 * 1024 * 1024) {// kich thuoc > 10mb
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("File too large! max size is 10MB");
                }


                String contentType = file.getContentType(); // Lay dinh dang file
                if (contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("must be an image file");
                }

                String fileName = storeFile(file);
                ProductImageEntity productImage = productService.createProductImage(
                        existingProduct.getId(),
                        ProductImageDTO.builder()
                                .imageUrl(fileName)
                                .build());
                productImages.add(productImage);
            }
            return ResponseEntity.ok().body(productImages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }
    // luu file anh
    private String storeFile(MultipartFile file) throws IOException {
        // kiem tra null va image file
        if (!isImageFile(file) || file.getOriginalFilename() == null) {
            throw new IOException("Invalid image format");
        }

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        // UUID xu ly luu trung ten file
        String uniqueFilename = UUID.randomUUID().toString() + "_" + fileName;
        // duong dan den thu muc luu
        Path uploadDir = Paths.get("uploads");

        if (!Files.exists(uploadDir))
            Files.createDirectories(uploadDir);
        // url day du file
        Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        //sao chep file vao thu muc
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFilename;

    }
    // kiem tra co phai file anh
    private boolean isImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }
}
