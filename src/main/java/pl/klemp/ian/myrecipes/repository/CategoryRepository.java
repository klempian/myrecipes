package pl.klemp.ian.myrecipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.klemp.ian.myrecipes.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);
}
