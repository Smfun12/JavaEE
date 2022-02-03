package kma.topic5.springwebsample;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Book {
    String title;
    String isbn;
    String author;
}
