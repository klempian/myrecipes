package pl.klemp.ian.myrecipes.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RecipeThumbnailDto {

    private UUID id;

    private String name;

    private String image;

}
