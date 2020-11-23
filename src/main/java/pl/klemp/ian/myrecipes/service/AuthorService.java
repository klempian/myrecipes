package pl.klemp.ian.myrecipes.service;

import pl.klemp.ian.myrecipes.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    Author save(Author author);

    Optional<Author> findById(Long id);

    List<Author> findAll();

    Author findByName(String name);

    Author findByUrl(String url);

    void delete(Author author);
}
