package pl.klemp.ian.myrecipes.service;

import pl.klemp.ian.myrecipes.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Category save(Category category);

    Optional<Category> findById(Long id);

    List<Category> findAll();

    Optional<Category> findByName(String name);

    Category findByNameOrCreate(String name);

    void delete(Category category);
}
