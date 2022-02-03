package kma.topic5.springwebsample;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FormController {

    List<Book> bookList = new ArrayList<>();

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String formControllerGet() {
        return "form-controller-get";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String formControllerPost(@ModelAttribute FormModel formModel, Model model) {
        model.addAttribute("formModel", formModel);
        return "form-controller-post";
    }

    @GetMapping("/getBooks")
    public String getAllBooks(Model model){
        model.addAttribute("books", bookList);
        return "books";
    }

    @RequestMapping(value = "/book/add", method = RequestMethod.GET)
    public String addBookPage(){
        return "book-create";
    }

    @RequestMapping(value = "/addBook", method = RequestMethod.POST)
    public String addBook(
            @RequestParam(name = "isbn") String isbn,
            @RequestParam(name = "title") String title,
            @RequestParam(name = "author") String author, Model model){
        Book book = new Book();
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setTitle(title);
        if (isbn.equals("") || author.equals("")||title.equals("")){
            model.addAttribute("errorText","Check book data");
            return "book-create";
        }
        if(!bookList.contains(book))
            bookList.add(book);
        return "redirect:/getBooks";
    }

}
