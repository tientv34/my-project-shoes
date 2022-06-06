package com.example.CodeJavaApp.service;

import com.example.CodeJavaApp.entity.Products;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface IStoreService {
//     public String storeFile(MultipartFile file, Products prd);
     public Stream<Path> loadAll(); //load all file  inside a folder
    public byte[] readFileContent(String fileName);
    public void deleteAllFile();
}
