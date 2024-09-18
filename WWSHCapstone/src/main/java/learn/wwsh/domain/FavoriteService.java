package learn.wwsh.domain;

import learn.wwsh.data.DataAccessException;
import learn.wwsh.data.FavoriteRepository;
import learn.wwsh.models.Favorite;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {
    private final FavoriteRepository repository;

    public FavoriteService(FavoriteRepository repository) {
        this.repository = repository;
    }

    public List<Favorite> findByUserId(int userId) throws DataAccessException {
        return repository.findByUserId(userId);
    }

    public Favorite add(Favorite favorite) throws DataAccessException {
        return repository.add(favorite);
    }

    public boolean deleteByFave(Favorite favorite) throws DataAccessException {
        return repository.deleteByFave(favorite);
    }
}
