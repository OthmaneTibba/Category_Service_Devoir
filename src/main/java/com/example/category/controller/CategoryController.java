package com.example.category.controller;

import com.example.category.entities.Category;
import com.example.category.exception.CategoryNotFound;
import com.example.category.service.CategoryService;
import com.example.category.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private CategoryService categoryService;



    @GetMapping("/test")
    public  String test(){
        return  "test2";
    }

    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<Category> upload(
            @RequestParam("file") MultipartFile multipartFile,
            @RequestParam("name") String name
    ){
      try{
          // Upload de l'image vers Cloudinary
          Map<String, String> result = cloudinaryService.upload(multipartFile);

          Category category = Category.builder()
                  .name(name)
                  .imageUrl(result.get("url"))
                  .date_created(Instant.now())
                  .build();
          categoryService.save(category);

          return new ResponseEntity<>(category, HttpStatus.OK);

      }catch (IOException e) {
          return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        Optional<Category> category = categoryService.getOne(id);
        if(category.isEmpty()){
            throw new CategoryNotFound("category n'exist pas   ", HttpStatus.BAD_REQUEST.toString());
        }
        Category category1 = category.get();
        String cloudinaryImageId = category1.getImageUrl();
        try {
            cloudinaryService.delete(cloudinaryImageId);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to delete blog from Cloudinary", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        categoryService.delete(id);

        return new ResponseEntity<>("category supprimée !", HttpStatus.OK);
    }

    @GetMapping
    public List<Category> getAll() {
        return categoryService.findAll();
    }
    @GetMapping("/{name}")
    @ResponseBody
    public ResponseEntity<Category> getByName(@PathVariable("name") String name) {
       Category category = categoryService.findByName(name);
        if(category == null){
            throw new CategoryNotFound("category n'exist pas   ", HttpStatus.BAD_REQUEST.toString());
        }
        return new ResponseEntity<>(category , HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Category> getById(@PathVariable("id") int id) {
        Optional<Category> category = categoryService.getOne(id);
        if(category == null){
            throw new CategoryNotFound("category n'exist pas   ", HttpStatus.BAD_REQUEST.toString());
        }
        Category category1 = category.get();

        return new ResponseEntity<>(category1 , HttpStatus.OK);
    }


}
