package kma.topic7;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);

    List<Book> findBookByAuthorContains(String author);

    List<Book> findBookByIsbnContains(String isbn);

    List<Book> findBookByTitleContains(String title);

    List<Book> findBookByTitleAndIsbn(String title, String isbn);
}
