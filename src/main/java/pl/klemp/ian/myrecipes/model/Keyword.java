package pl.klemp.ian.myrecipes.model;

import lombok.Getter;
import lombok.Setter;
import pl.klemp.ian.myrecipes.model.persistent.AbstractPersistentObject;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class Keyword extends AbstractPersistentObject {

    private String name;

    public Keyword() {
    }

    public Keyword(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
