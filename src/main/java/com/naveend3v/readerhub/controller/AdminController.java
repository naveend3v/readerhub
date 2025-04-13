package com.naveend3v.readerhub.controller;

import com.naveend3v.readerhub.dto.request.JwtAuthRequest;
import com.naveend3v.readerhub.dto.response.AuthResponse;
import com.naveend3v.readerhub.dto.response.BookResponse;
import com.naveend3v.readerhub.dto.response.ErrorResponse;
import com.naveend3v.readerhub.dto.response.SuccessResponse;
import com.naveend3v.readerhub.jwt.JwtService;
import com.naveend3v.readerhub.model.*;
import com.naveend3v.readerhub.service.BookslistService;
import com.naveend3v.readerhub.service.FileUploadService;
import com.naveend3v.readerhub.service.S3StorageService;
import com.naveend3v.readerhub.service.UserInfoService;
import com.naveend3v.readerhub.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BookslistService booksService;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private S3StorageService s3StorageService;

    public AdminController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @PostMapping("/login")
    public ResponseEntity adminLogin(@RequestBody JwtAuthRequest jwtAuthRequest) {

        if (jwtAuthRequest.getUsername() == null || jwtAuthRequest.getUsername().length() <= 0 || jwtAuthRequest.getPassword() == null || jwtAuthRequest.getPassword().length() <= 0) {
            return ErrorResponse.generateResp("Username or password cannot be empty!", HttpStatus.BAD_REQUEST);
        }

        Optional<UserInfo> user = userInfoService.findByName(jwtAuthRequest.getUsername());

        if(user.isEmpty()){
            return ErrorResponse.generateResp("Invalid username or password", HttpStatus.NOT_FOUND);
        }

        if(user.isPresent() && user.get().getRoles().contains("ROLE_ADMIN")){
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtAuthRequest.getUsername(), jwtAuthRequest.getPassword()));
            if (auth.isAuthenticated()) {
                return AuthResponse.generateResp(jwtService.generateToken(jwtAuthRequest.getUsername()),HttpStatus.OK);
            }
        } else {
            return ErrorResponse.generateResp("User is not an Admin!", HttpStatus.UNAUTHORIZED);
        }

        return ErrorResponse.generateResp("Invalid username or password", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("logout")
    public ResponseEntity adminLogout() {
        return AuthResponse.generateResp("Logged out successfully!", HttpStatus.OK);
    }

    @GetMapping("/books")
    public ResponseEntity getAllBooks() {
        return SuccessResponse.generateResp(booksService.findAllBooks(), HttpStatus.OK);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity getBooksByID(@PathVariable Integer id) {
        Optional<Bookslist> book = booksService.findByBook(id);
        if (book.isPresent()) {
            return BookResponse.generateResp(book.get(), HttpStatus.OK);
        } else {
            return ErrorResponse.generateResp("Book with ID " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/books")
    public ResponseEntity addBook(@RequestBody Bookslist newBook) {
        return BookResponse.generateResp(booksService.saveBook(newBook), HttpStatus.OK);
    }

    @PostMapping("/upload/image")
    public ResponseEntity uploadImage(@RequestPart("file") MultipartFile file) throws IOException {
        return SuccessResponse.generateResp(s3StorageService.uploadFileToS3(file),HttpStatus.OK);
        /*        String path = fileUploadService.uploadImageToFileSystem(file);
        return SuccessResponse.generateResp("Image uploaded successfully! - " + path, HttpStatus.OK);*/
    }

    @GetMapping(value = "/download/{imageName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> getImage(@PathVariable String imageName) throws IOException, URISyntaxException {

        byte[] data = s3StorageService.downloadFileFromS3(imageName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + imageName + "\"")
                .body(resource);
/*        try {
            Resource resource = fileUploadService.downloadImageFromFileSystem(imageName);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (Exception e) {
            return ErrorResponse.generateResp(e.getMessage(), HttpStatus.NOT_FOUND);
        }*/
    }

    @DeleteMapping("/delete/{imageName}")
    public ResponseEntity deleteImage(@PathVariable String imageName){
        return SuccessResponse.generateResp(s3StorageService.deleteFileFromS3(imageName),HttpStatus.OK);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<?> updateBooks(@RequestBody Bookslist book, @PathVariable Integer id) {
        if (book != null) {
            return BookResponse.generateResp(booksService.updateBook(book, id), HttpStatus.OK);
        } else {
            return ErrorResponse.generateResp("Book not found with ID " + id, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity getAllBooks(@PathVariable Integer id) {
        Optional<Bookslist> book = booksService.findByBook(id);
        if (book.isPresent()) {
            String bookName = book.get().getBookName();
            booksService.removeBook(id);
            return SuccessResponse.generateResp("Book " + bookName + " deleted successfully!", HttpStatus.OK);
        } else {
            return ErrorResponse.generateResp("Book not found with ID " + id, HttpStatus.NOT_FOUND);
        }
    }
}
