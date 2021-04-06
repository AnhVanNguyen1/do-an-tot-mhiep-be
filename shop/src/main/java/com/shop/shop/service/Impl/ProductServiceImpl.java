package com.shop.shop.service.Impl;

import com.shop.shop.common.ModelMapperUtils;
import com.shop.shop.entity.PhotoProduct;
import com.shop.shop.entity.Product;
import com.shop.shop.repository.CategoryRepository;
import com.shop.shop.repository.PhotoProductRepository;
import com.shop.shop.repository.ProductRepository;
import com.shop.shop.request.ProductRequest;
import com.shop.shop.service.PhotoProductService;
import com.shop.shop.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import response.ProductDTO;

import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    private final PhotoProductRepository photoProductRepository;

    private final CategoryRepository categoryRepository;

    private final PhotoProductService photoProductService;

    public ProductServiceImpl(ProductRepository productRepository, PhotoProductRepository photoProductRepository, CategoryRepository categoryRepository, PhotoProductService photoProductService) {
        this.productRepository = productRepository;
        this.photoProductRepository = photoProductRepository;
        this.categoryRepository = categoryRepository;
        this.photoProductService = photoProductService;
    }

    private String getFileURL(String fileName) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/download/")
                .path(fileName)
                .toUriString();
    }

    @Override
    public Page<ProductDTO> search(String search, Pageable pageable) {
        try {
            Page<Product> productPage;
            if (search != null) {
                productPage=productRepository.search(search,pageable);
            }else {
                productPage=productRepository.findAllBy(pageable);
            }
            Page<ProductDTO> productDTOS = productPage.map(product -> {
                ProductDTO productDTO = ModelMapperUtils.map(product, ProductDTO.class);
                PhotoProduct photoProduct = photoProductRepository.findAllByProductId(productDTO.getId());
                if(photoProduct==null){
                    productDTO.setPhoto(getFileURL("default.jpg"));
                }else {
                    productDTO.setPhoto(getFileURL(photoProduct.getFileName()));
                }
                String giaGiam=productDTO.getPrice()*(1-productDTO.getDiscount()/100)+ "VNĐ";
                productDTO.setGiaGiam(giaGiam);
                return productDTO;
            });
            log.info("getList product success");
            return productDTOS;
        } catch (Exception e) {
            log.error("getList product fail", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductDTO create(ProductRequest productRequest) {
        try {
            Product product = ModelMapperUtils.map(productRequest, Product.class);
            product.setCreateAt(new Date());
            product.setId(null);
            Product save = productRepository.save(product);
            PhotoProduct photoProduct1 = new PhotoProduct();
            if (productRequest.getPhotos() != null) {
                PhotoProduct photoProduct = PhotoProduct.builder()
                        .fileName(photoProductService.storeFile(productRequest.getPhotos()))
                        .fileNameBlank("default.jpg")
                        .product(save)
                        .build();
                photoProduct1 = photoProductRepository.save(photoProduct);
            }
            ProductDTO productDTO = ModelMapperUtils.map(save, ProductDTO.class);
            productDTO.setPhoto(getFileURL(photoProduct1.getFileName()));
            log.info("function : create product success");
            return productDTO;
        } catch (Exception e) {
            log.info("Create product fail");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Create product fail");
        }
    }

    @Override
    public ProductDTO edit(ProductRequest productRequest, Integer productId) {
        try {
            //update product
            Product product = productRepository.findById(productId).get();
            product.setUpdateAt(new Date());
            product.setId(productId);
            product=setEdit(product, productRequest);
            Product update = productRepository.save(product);
            System.out.println(productRequest.getPhotos());
            PhotoProduct photoProduct1=photoProductRepository.findAllByProductId(productId);
            if (productRequest.getPhotos() != null && photoProduct1==null ) {
                photoProduct1 = PhotoProduct.builder()
                        .fileName(photoProductService.storeFile(productRequest.getPhotos()))
                        .fileNameBlank("default.jpg")
                        .product(product)
                        .build();
            }else if (productRequest.getPhotos() != null){
                photoProduct1.setFileName(photoProductService.storeFile(productRequest.getPhotos()));
            }
            photoProduct1 = photoProductRepository.save(photoProduct1);
            ProductDTO productDTO = ModelMapperUtils.map(update, ProductDTO.class);
            productDTO.setPhoto(getFileURL(photoProduct1.getFileName()));
            log.info("function : update product success");
            return productDTO;
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "not logged in");
        }
    }
    private Product setEdit(Product product, ProductRequest productRequest) {
        if (!product.getName().equals(productRequest.getName())) {
            product.setName(productRequest.getName());
        }
        if (!product.getMetaTitle().equals(productRequest.getMetaTitle())) {
            product.setMetaTitle(productRequest.getMetaTitle());
        }
        if (!product.getSummary().equals(productRequest.getSummary())) {
            product.setSummary(productRequest.getSummary());
        }
        if (!product.getType().equals(productRequest.getType())) {
            product.setType(productRequest.getType());
        }
        if (!product.getPrice().equals(productRequest.getPrice())) {
            product.setPrice(productRequest.getPrice());
        }
        if (!product.getDiscount().equals(productRequest.getDiscount())) {
            product.setDiscount(productRequest.getDiscount());
        }
        if (!product.getQuantity().equals(productRequest.getQuantity())) {
            product.setQuantity(productRequest.getQuantity());
        }
        return product;
    }
    @Override
    public Page<ProductDTO> searchByCategory(Pageable pageable, Integer categoryId) {
        try {
            Page<Product> productPage;
            if (categoryId != null) {
                productPage=productRepository.findAllBy(pageable);
            }else {
                productPage=productRepository.findAllByCategoryId(categoryId,pageable);
            }
            Page<ProductDTO> productDTOS = productPage.map(product -> {
                ProductDTO productDTO = ModelMapperUtils.map(product, ProductDTO.class);
                for (Product item : productPage) {
                    PhotoProduct photoProduct1 = photoProductRepository.findAllByProductId(item.getId());
                    if(photoProduct1==null){
                        productDTO.setPhoto(getFileURL("default.jpg"));
                    }else {
                        productDTO.setPhoto(getFileURL(photoProduct1.getFileName()));
                    }
                }
                return productDTO;
            });
            log.info("getList product success");
            return productDTOS;
        } catch (Exception e) {
            log.error("getList product fail", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ProductDTO detail(Integer productId) {
        Optional<Product> product=productRepository.findById(productId);
        ProductDTO productDTO = ModelMapperUtils.map(product, ProductDTO.class);
        if(!product.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product does not exits");
        }
        PhotoProduct photoProduct = photoProductRepository.findAllByProductId(product.get().getId());
        if(photoProduct==null){
            productDTO.setPhoto(getFileURL("default.jpg"));
        }else {
            productDTO.setPhoto(getFileURL(photoProduct.getFileName()));
        }
        String giaGiam=product.get().getPrice()*(1-product.get().getDiscount())/100+ "VNĐ";
        productDTO.setGiaGiam(giaGiam);
        return productDTO;
    }

    @Override
    public Page<ProductDTO> search2(String search,Integer categoryId, Pageable pageable) {
        try {
            Page<Product> productPage;
            if (search != null && categoryId != null) {
                productPage = productRepository.search2(search, categoryId, pageable);
            }else if(search == null && categoryId == null){
                productPage = productRepository.findAllBy(pageable);
            }
            else {
                productPage = productRepository.findAllByCategoryId(categoryId,pageable);
            }
            Page<ProductDTO> productDTOS = productPage.map(product -> {
                ProductDTO productDTO = ModelMapperUtils.map(product, ProductDTO.class);
                PhotoProduct photoProduct = photoProductRepository.findAllByProductId(productDTO.getId());
                if (photoProduct == null) {
                    productDTO.setPhoto(getFileURL("default.jpg"));
                } else {
                    productDTO.setPhoto(getFileURL(photoProduct.getFileName()));
                }
                String giaGiam = productDTO.getPrice() * (1 - productDTO.getDiscount()/ 100)  + "VNĐ";
                productDTO.setGiaGiam(giaGiam);
                return productDTO;
            });
            log.info("getList product success");
            return productDTOS;
        } catch (Exception e) {
            log.error("getList product fail", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
