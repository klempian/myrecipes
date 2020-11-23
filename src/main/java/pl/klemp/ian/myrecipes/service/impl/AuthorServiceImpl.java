package pl.klemp.ian.myrecipes.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.klemp.ian.myrecipes.model.Author;
import pl.klemp.ian.myrecipes.repository.AuthorRepository;
import pl.klemp.ian.myrecipes.service.AuthorService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author findByName(String name) {
        return authorRepository.findByName(name);
    }

    @Override
    public Author findByUrl(String url) {
        return authorRepository.findByUrl(url);
    }

    @Override
    public void delete(Author author) {
        authorRepository.delete(author);
    }
}
