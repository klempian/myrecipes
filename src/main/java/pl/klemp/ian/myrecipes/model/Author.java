package pl.klemp.ian.myrecipes.model;

import lombok.Getter;
import lombok.Setter;
import pl.klemp.ian.myrecipes.model.persistent.AbstractPersistentObject;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class Author extends AbstractPersistentObject {

    private String name;

    private String url;

    public Author() {
    }

    public Author(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
