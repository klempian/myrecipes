package pl.klemp.ian.myrecipes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity
public class Keyword {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    @JsonIgnore
    private UUID id;

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
