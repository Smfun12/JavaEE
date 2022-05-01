package kma.topic7;

import lombok.AllArgsConstructor;
import org.assertj.core.util.Lists;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@AllArgsConstructor
public class BookServiceImpl {

    private BookRepository bookRepository;

    @Transactional
    public void addBook(Book book){
        bookRepository.save(book);
    }

    public List<Book> getBooks(){
        return bookRepository.findAll();
    }

    public List<Book> getBooksPaginated(int size, int page){
        Pageable pageable = PageRequest.of(page-1, size);
        return bookRepository.findAll(pageable).getContent();
    }

    public Book findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn).orElseThrow(()
                -> new RuntimeException("No book with isbn="+isbn+" was found"));
    }

    public List<Book> findByField(String searchField, String value) {

        switch (searchField) {
            case "author":
                return bookRepository.findBookByAuthorContains(value);
            case "title":
                return bookRepository.findBookByTitleContains(value);
            case "isbn":
                return bookRepository.findBookByIsbnContains(value);
        }

        return Lists.emptyList();
    }

    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new RuntimeException("No book with id="+id+" was found")
        );
    }
}
