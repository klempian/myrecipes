package pl.klemp.ian.myrecipes.service;

import pl.klemp.ian.myrecipes.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    Author save(Author author);

    Optional<Author> findById(Long id);

    List<Author> findAll();

    Optional<Author> findByName(String name);

    Optional<Author> findByUrl(String url);

    Author findByUrlAndNameOrCreate(String url, String name);

    void delete(Author author);
}
