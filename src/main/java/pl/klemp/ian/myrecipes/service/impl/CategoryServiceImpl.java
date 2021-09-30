package pl.klemp.ian.myrecipes.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.klemp.ian.myrecipes.exception.EntityNotFoundException;
import pl.klemp.ian.myrecipes.model.Category;
import pl.klemp.ian.myrecipes.repository.CategoryRepository;
import pl.klemp.ian.myrecipes.service.CategoryService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findByName(String name) {
        return categoryRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException(Category.class, "name", name));
    }

    @Override
    public Category findByNameOrCreate(String name) { return categoryRepository.findByName(name)
            .orElseGet(() -> save(new Category(name))); }

    @Override
    public void delete(Category category) {
        categoryRepository.delete(category);
    }
}
