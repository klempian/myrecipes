package pl.klemp.ian.myrecipes.service;

import pl.klemp.ian.myrecipes.model.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryService {

    Category save(Category category);

    Category findById(UUID id);

    List<Category> findAll();

    Optional<Category> findByName(String name);

    Category findByNameOrCreate(String name);

    void delete(Category category);
}
