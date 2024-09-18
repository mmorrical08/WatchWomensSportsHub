package learn.wwsh.controllers;

import learn.wwsh.data.DataAccessException;
import learn.wwsh.domain.FavoriteService;
import learn.wwsh.models.Favorite;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService service;

    public FavoriteController(FavoriteService service) {
        this.service = service;
    }

    @GetMapping("/user/{userId}")
    public List<Favorite> findByUserId(@PathVariable int userId) throws DataAccessException {
        return service.findByUserId(userId);
    }

    @PostMapping("/user/{userId}/add")
    public Favorite add(@PathVariable int userId, @RequestBody Favorite favorite) throws DataAccessException {
        favorite.setUserId(userId);
        return service.add(favorite);
    }

    @DeleteMapping("/user/{userId}/delete")
    public boolean deleteByFave(@PathVariable int userId, @RequestBody Favorite favorite) throws DataAccessException {
        favorite.setUserId(userId);
        return service.deleteByFave(favorite);
    }

}
