package learn.wwsh.data;
import learn.wwsh.models.Sport;
import java.util.List;

public interface SportRepository {
    Sport findById(int sportId) throws DataAccessException;

    List<Sport> findAllByName(String name) throws DataAccessException;

    Sport findByLeague(String league) throws DataAccessException;

    List<Sport> findAll() throws DataAccessException;
}
