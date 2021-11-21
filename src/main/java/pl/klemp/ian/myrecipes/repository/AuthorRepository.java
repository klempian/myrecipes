package pl.klemp.ian.myrecipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.klemp.ian.myrecipes.model.Author;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByName(String name);

    Optional<Author> findByUrl(String url);

    Optional<Author> findByUrlOrName(String url, String name);

    Optional<Author> findByUrlAndName(String url, String name);
}
