package kma.topic5.springwebsample;

import lombok.AllArgsConstructor;
import org.assertj.core.util.Lists;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
@Service
@AllArgsConstructor
public class BookServiceImpl {

    private EntityManager entityManager;

    @Transactional
    public void addBook(Book book){
        entityManager.merge(book);
    }

    public List<Book> getBooks(){
        return entityManager.createQuery("FROM Book", Book.class).getResultList();
    }

    public Book findByIsbn(String isbn) {
        return (Book) entityManager.createQuery("FROM Book WHERE isbn=:bookIsbn")
                .setParameter("bookIsbn", isbn)
                .getSingleResult();
    }

    public List<Book> findByField(String searchField, String value) {
        switch (searchField) {
            case "author":
                return entityManager.createQuery("FROM Book WHERE author LIKE :searchValue", Book.class)
                        .setParameter("searchValue", "%" + value + "%")
                        .getResultList();
            case "title":
                return entityManager.createQuery("FROM Book WHERE title LIKE :searchValue", Book.class)
                        .setParameter("searchValue", "%" + value + "%")
                        .getResultList();
            case "isbn":
                return entityManager.createQuery("FROM Book WHERE isbn LIKE :searchValue", Book.class)
                        .setParameter("searchValue", "%" + value + "%")
                        .getResultList();
        }
        return Lists.emptyList();
    }

    public Book findById(Long id) {
        return entityManager.find(Book.class, id);
    }
}
