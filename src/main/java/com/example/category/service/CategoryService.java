package com.example.category.service;

import com.example.category.entities.Category;
import com.example.category.exception.CategoryNotFound;
import com.example.category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public  void save(Category category){
        categoryRepository.save(category);
    }

    public Category findByName(String name){

        return categoryRepository.findByName(name);
    }


    public void delete(int id){
        categoryRepository.deleteById(id);
    }
    public Optional<Category> getOne(int id){
        return categoryRepository.findById(id);
    }
    public boolean exists(int id){
        return categoryRepository.existsById(id);
    }

    public List<Category> findAll(){
        return  categoryRepository.findAll();
    }
}
