package kma.topic5.springwebsample;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/book")
@AllArgsConstructor
public class BookController {

    private BookServiceImpl bookService;

    @GetMapping("/{isbn}")
    public String findByIsbn(@PathVariable(name = "isbn")String isbn, Model model){
        Book book = bookService.findByIsbn(isbn);
        model.addAttribute("id", book.getId());
        model.addAttribute("title", book.getTitle());
        model.addAttribute("author", book.getAuthor());
        model.addAttribute("isbn", book.getIsbn());
        return "book-info";
    }
}
