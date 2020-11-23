package pl.klemp.ian.myrecipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.klemp.ian.myrecipes.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Author findByName(String name);

    Author findByUrl(String url);
}
