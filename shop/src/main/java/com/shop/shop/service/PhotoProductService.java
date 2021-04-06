package com.shop.shop.service;

import org.springframework.web.multipart.MultipartFile;

public interface PhotoProductService {
    String storeFile(MultipartFile file);
}
