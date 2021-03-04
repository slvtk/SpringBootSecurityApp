package ru.kpfu.security.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
public class PostDTO {
    private Long id;
    @Size(min = 2, max = 10, message = "Title should exist and be more than 2 chars")
    private String title;
    @Size(min = 1, max = 512, message = "Text should be 1-512 chars")
    private String text;
}
