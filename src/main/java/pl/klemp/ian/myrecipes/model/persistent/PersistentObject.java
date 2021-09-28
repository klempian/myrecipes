package pl.klemp.ian.myrecipes.model.persistent;

import java.util.UUID;

public interface PersistentObject {
    public UUID getId();
    public void setId(UUID id);
}
