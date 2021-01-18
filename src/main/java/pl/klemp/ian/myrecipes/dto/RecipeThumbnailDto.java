package pl.klemp.ian.myrecipes.dto;

import lombok.Getter;
import lombok.Setter;
import pl.klemp.ian.myrecipes.model.Author;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class RecipeThumbnailDto {

    private UUID id;

    private String name;

    private String image;

}
