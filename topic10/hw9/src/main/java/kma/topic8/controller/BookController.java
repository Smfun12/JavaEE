package kma.topic8.controller;

import kma.topic8.model.User;
import kma.topic8.model.WishList;
import kma.topic8.repository.UserRepo;
import kma.topic8.service.BookServiceImpl;
import kma.topic8.model.Book;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/book")
@AllArgsConstructor
public class BookController {

    private BookServiceImpl bookService;
    private UserRepo userRepo;

    @GetMapping("/isbn/{isbn}")
    public String findByIsbn(@PathVariable(name = "isbn")String isbn, Model model){
        Book book = bookService.findByIsbn(isbn);
        model.addAttribute("id", book.getId());
        model.addAttribute("title", book.getTitle());
        model.addAttribute("author", book.getAuthor());
        model.addAttribute("isbn", book.getIsbn());
        return "book-info";
    }

    @GetMapping("/userWishlist")
    public String getWishList(Model model, Authentication authentication){
        if (authentication == null)
            return "redirect:/login";
        User user = userRepo.findByLogin(authentication.getName()).orElseThrow(
                () -> new RuntimeException("No user found"));
        model.addAttribute("books", user.getWishList().getBooks());
        return "user-info";
    }


    @GetMapping("/wishlist/delete/{id}")
    public String removeFromWishList(@PathVariable(name = "id")String bookId,
                                     Authentication authentication){
        User user = userRepo.findByLogin(authentication.getName()).orElseThrow(
                () -> new RuntimeException("No user found"));
        WishList wishList = user.getWishList();
        wishList.setBooks(wishList.getBooks().stream()
                .dropWhile(book -> book.getId()==Long.parseLong(bookId)).collect(Collectors.toList()));
        user.setWishList(wishList);
        userRepo.save(user);
        return "redirect:/book/userWishlist";
    }
}
