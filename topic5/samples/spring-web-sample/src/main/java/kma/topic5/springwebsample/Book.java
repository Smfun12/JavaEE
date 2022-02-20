package kma.topic5.springwebsample;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@RequiredArgsConstructor(staticName = "of")
public class Book {
    final String title;
    final String isbn;
    final String author;
}
