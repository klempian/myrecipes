package pl.klemp.ian.myrecipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.klemp.ian.myrecipes.model.Keyword;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    Keyword findByName(String name);
}
