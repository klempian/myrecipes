package pl.klemp.ian.myrecipes.model.persistent;

import java.util.UUID;

public interface PersistentObject {
    public Long getId();
    public void setId(Long id);

    public UUID getUuid();
    public void setUuid(UUID uuid);
}
