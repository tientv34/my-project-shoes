package com.example.CodeJavaApp.service.impl;

import com.example.CodeJavaApp.entity.Products;
import com.example.CodeJavaApp.service.IStoreService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class ImageStoreService implements IStoreService {

    private final Path storeFile = Paths.get("uploads");
    @Autowired
    private ProductServiceImpl prdService = new ProductServiceImpl();

    public void saveFile(String uploadDir, String fileName,
                                MultipartFile multipartFile) throws IOException {

//        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(storeFile)) {
            Files.createDirectories(storeFile);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = storeFile.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }

    public ImageStoreService() {
        try {
            //tạo ra 1 thư mục nếu thư mục đó không tồn tại.
            Files.createDirectories(storeFile);
        }catch (Exception e){
            throw new RuntimeException("Cannot initialize storage", e);
        }
    }

    //Kiểm tra file ảnh tải lên.
    private boolean isImageFile(MultipartFile file){
           //Let install FileNameUtils
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[]{"png","jpg","jpeg","bmp"}).contains(fileExtension.trim().toLowerCase());
    }

//    @Override
//    public String storeFile(MultipartFile file, Products prd) {
//        try{
//            System.out.println("haha");
//            if (file.isEmpty()){
//                throw  new RuntimeException("Failed to store empty file");
//            }
//            //Check file is image???
//            if(!isImageFile(file)){
//                throw  new RuntimeException("You can only upload image file");
//            }
//            //file must be <= 5Mb
//            float fileSizeInMegabyte =file.getSize() /1_000_000.0f;
//            if(fileSizeInMegabyte > 5.0f){
//                throw  new RuntimeException("File must be <= 5Mb");
//            }
//
//            //File must be remane, why?
//            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
//            prd.setImage(fileExtension);
//            Products SavePrd = prdService.savePrd(prd);
//            String generatedFileName =UUID.randomUUID().toString().replace("-","");
//            System.out.println("generatedFileName"+generatedFileName);
//            generatedFileName = generatedFileName+"."+fileExtension;
//            Path destinationFilePath = this.storeFile.resolve(Paths.get(generatedFileName)).normalize().toAbsolutePath();
//
//            if (!destinationFilePath.getParent().equals(this.storeFile.toAbsolutePath())){
//                throw  new RuntimeException("Cannot store file outside crrent directory");
//
//            }
//            try(InputStream inputStream = file.getInputStream()) {
//                Files.copy(inputStream,destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
//            }
//            return generatedFileName;
//        }
//        catch (Exception e){
//                throw new RuntimeException("Failed to store file "+e);
//        }
//
//    }

    @Override
    public Stream<Path> loadAll() {
        try {
            //list all files in storeFile
            return Files.walk(this.storeFile,1).filter(path -> !path.equals(this.storeFile))
                    .map(this.storeFile::relativize);
        }
        catch (Exception exception){
            throw new RuntimeException("Failed to load stored files " ,exception);
        }
    }

    @Override
    public byte[] readFileContent(String fileName) {
        try {
            Path file = storeFile.resolve(fileName);
            //Muốn dowload thì phải trả về resource;
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()){
                byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
                return  bytes;
            }else{
                throw new RuntimeException("Could not read file: "+ fileName);
            }
        }catch (Exception exception){
            throw new RuntimeException("Could not read file: " +fileName,exception);
        }
    }

    @Override
    public void deleteAllFile() {

    }
}
