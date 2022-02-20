package kma.topic5.springwebsample;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MyRestController.class)
class MyRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void clearBookList() throws Exception {
        mockMvc.perform(get("/book/clear"));
    }
    @Test
    public void addBookWithRest() throws Exception {
        Book book = Book.of("math","231","kozerenko");
        mockMvc.perform(post("/book/addBookRest")
                .contentType("application/json")
                .param("title","math")
                        .param("isbn","231")
                                .param("author","kozerenko"))
                        .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(book)));
    }

    @Test
    void getBooks() throws Exception {
        List<Book> bookList = new ArrayList<>();
        Book book = Book.of("math","231","kozerenko");
        bookList.add(book);
        mockMvc.perform(post("/book/addBookRest")
                        .contentType("application/json")
                        .param("title","math")
                        .param("isbn","231")
                        .param("author","kozerenko"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/book/getBooks")).andExpect(
                content().json(objectMapper.writeValueAsString(bookList)));

    }

    @Test
    void getBooksByParam() throws Exception {
        List<Book> list = new ArrayList<>();
        Book book = Book.of("math","231","kozerenko");
        list.add(book);
        mockMvc.perform(post("/book/addBookRest")
                        .contentType("application/json")
                        .param("title","math")
                        .param("isbn","231")
                        .param("author","kozerenko"))
                .andExpect(status().isOk());
        mockMvc.perform(post("/book/addBookRest")
                        .contentType("application/json")
                        .param("title","topology")
                        .param("isbn","das")
                        .param("author","1230-9"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/book/books")
                .param("searchField","math"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(list)));
    }
}