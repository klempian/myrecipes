package pl.klemp.ian.myrecipes.dto;

import lombok.Getter;
import lombok.Setter;
import pl.klemp.ian.myrecipes.model.Author;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class RecipeThumbnailDto {

    private String name;

    private String image;

}
