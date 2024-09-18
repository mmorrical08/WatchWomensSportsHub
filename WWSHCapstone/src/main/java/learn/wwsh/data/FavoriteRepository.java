package learn.wwsh.data;

import learn.wwsh.models.Favorite;

import java.util.List;

public interface FavoriteRepository {

    List<Favorite> findByUserId(int userId) throws DataAccessException;

    Favorite add(Favorite favorite) throws DataAccessException;

    boolean deleteByFave(Favorite favorite) throws DataAccessException;
}
