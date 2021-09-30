package pl.klemp.ian.myrecipes.model.persistent;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.UUID;

@MappedSuperclass
public abstract class AbstractPersistentObject implements PersistentObject {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @JsonIgnore
    private Long id;

    @JsonIgnore
    @Column(unique = true, updatable = false, columnDefinition = "BINARY(16)")
    private UUID uuid = UUID.randomUUID();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersistentObject)) return false;

        PersistentObject that = (PersistentObject) o;

        if (uuid == null) return false;

        return uuid.equals(that.getUuid());
    }

    @Override
    public int hashCode() {
        if (uuid != null) {
            return uuid.hashCode();
        } else {
            return super.hashCode();
        }
    }

    @Override
    public String toString() {
        return this.getClass().getName()
                + "[uuid=" + uuid + "]";
    }
}
