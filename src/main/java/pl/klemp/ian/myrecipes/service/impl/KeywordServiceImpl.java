package pl.klemp.ian.myrecipes.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.klemp.ian.myrecipes.model.Keyword;
import pl.klemp.ian.myrecipes.repository.KeywordRepository;
import pl.klemp.ian.myrecipes.service.KeywordService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class KeywordServiceImpl implements KeywordService {

    private final KeywordRepository keywordRepository;

    @Override
    public Keyword save(Keyword keyword) {
        return keywordRepository.save(keyword);
    }

    @Override
    public Optional<Keyword> findById(Long id) {
        return keywordRepository.findById(id);
    }

    @Override
    public List<Keyword> findAll() {
        return keywordRepository.findAll();
    }

    @Override
    public Optional<Keyword> findByName(String name) {
        return keywordRepository.findByName(name);
    }

    @Override
    public Keyword findByNameOrCreate(String name) { return keywordRepository.findByName(name)
            .orElseGet(() -> save(new Keyword(name))); }

    @Override
    public void delete(Keyword keyword) {
        keywordRepository.delete(keyword);
    }
}
