package ru.kpfu.security.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
public class PostDTO {
    private Long id;
    @Size(min = 2, max = 10, message = "Title should be more than 2 chars")
    private String title;
    @Size(min = 5, message = "Text should be more than 5 chars")
    private String text;
}
