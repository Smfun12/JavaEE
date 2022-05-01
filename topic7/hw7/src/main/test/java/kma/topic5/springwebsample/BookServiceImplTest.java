package kma.topic5.springwebsample;


import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;


@SpringBootTest
class BookServiceImplTest {

    @Autowired
    BookServiceImpl bookService;

    @BeforeEach
    @Sql("/clean_up.sql")
    public void clearDb(){
        System.out.println("Db is cleaned");
    }

    @Test
    @Sql(value = "/clean_up.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void getBooks(){
        Assertions.assertEquals(bookService.getBooks().size(), 2);
    }

    @Test
    @Sql(value = "/clean_up.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void clearBooks(){
        Assertions.assertEquals(bookService.getBooks().size(), 0);
    }

    @Test
    @Sql(value = "/clean_up.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void saveToDb(){
        Book book = new Book();
        book.setAuthor("Author");
        book.setTitle("Topology");
        book.setIsbn("789879");
        book.setId(1L);
        Assertions.assertEquals(bookService.findById(1L), book);
    }

    @Test
    @Sql(value = "/clean_up.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void findByTitle(){
        Book book = new Book();
        book.setAuthor("Author");
        book.setTitle("Topology");
        book.setIsbn("12321");
        Book book1 = new Book();
        book1.setAuthor("kozerenko");
        book1.setTitle("graph");
        book1.setIsbn("989798");
        book.setId(1L);
        book1.setId(2L);
        Assertions.assertEquals(2, bookService.getBooks().size());
        Assertions.assertEquals(1, bookService.findByField("title", "graph").size());
        Assertions.assertEquals(bookService.findByField("title","graph").get(0), book1);
    }

    @Test
    @Sql(value = "/clean_up.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void findByIsbn(){
        Book book = new Book();
        book.setAuthor("Author");
        book.setTitle("Topology");
        book.setIsbn("789879");
        Book book1 = new Book();
        book1.setAuthor("kozerenko");
        book1.setTitle("graph");
        book1.setIsbn("989798");
        book.setId(1L);
        book1.setId(2L);
        Assertions.assertEquals(2, bookService.getBooks().size());
        Assertions.assertEquals(bookService.findByIsbn("989798"), book1);
        Assertions.assertEquals(bookService.findByField("isbn","7"), Lists.newArrayList(book,book1));
    }

}