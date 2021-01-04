package pl.klemp.ian.myrecipes.service;

import pl.klemp.ian.myrecipes.model.Keyword;

import java.util.List;
import java.util.Optional;

public interface KeywordService {

    Keyword save(Keyword keyword);

    Optional<Keyword> findById(Long id);

    List<Keyword> findAll();

    Optional<Keyword> findByName(String name);

    Keyword findByNameOrCreate(String name);

    void delete(Keyword keyword);
}
