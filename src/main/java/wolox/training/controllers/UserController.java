package wolox.training.controllers;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import wolox.training.exceptions.UserMismatchException;
import wolox.training.exceptions.UserNotFoundException;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.UserRepository;
import wolox.training.services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    public User findByUsername(@PathVariable String username) throws UserNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User newUser){
        return userRepository.save(newUser);
    }

    @PutMapping("/{id}")
    public User update(@RequestBody User user, @PathVariable Long id) throws UserNotFoundException {
        if(user.getId() == id) {
            userRepository.findById(id).orElseThrow(UserNotFoundException::new);
            return userRepository.save(user);
        }else {
            throw new UserMismatchException();
        }
    }

    @PutMapping("/{id}/books")
    public User addBookToUser (@PathVariable Long id, @RequestBody Book bookToAdd) throws JSONException {
        userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return userService.addBook(id, bookToAdd);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws UserNotFoundException {
        userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.deleteById(id);
    }
}
