package com.naveend3v.readerhub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileUploadService {

    private String fileName;
    private String fileType;
    private String fileExtension;
    @Value("${file.sourceDir}")
    private String sourceDir;

    @Autowired
    private BookslistService booksService;

    public String uploadImageToFileSystem(MultipartFile file) throws IOException {
        String uploadDir = sourceDir ;
        Integer bookSize = booksService.findAllBooks().size()+1;
        fileExtension = file.getOriginalFilename().split("\\.")[1];
        fileName = "book-" +  bookSize + "." + fileExtension;
        Path filePath = Paths.get(uploadDir + fileName);
        Files.createDirectories(filePath.getParent()); // Ensure the directory exists
        Files.write(filePath, file.getBytes());
        return filePath.toString().replace("\\", "/");
    }


    public Resource downloadImageFromFileSystem(String imageName) throws IOException, URISyntaxException {
        File dir = new File(sourceDir+fileName);
        try{
            if(dir.exists()){
                Resource resource = new UrlResource(dir.toURI());
                return resource;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        return null;
    }

}
