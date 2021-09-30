package pl.klemp.ian.myrecipes.model;

import lombok.Getter;
import lombok.Setter;
import pl.klemp.ian.myrecipes.model.persistent.AbstractPersistentObject;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class Category extends AbstractPersistentObject {

    @Column(unique = true)
    private String name;

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }
}
