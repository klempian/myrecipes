package pl.klemp.ian.myrecipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.klemp.ian.myrecipes.model.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);
}
