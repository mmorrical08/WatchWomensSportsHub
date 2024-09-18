package learn.wwsh.data;

import learn.wwsh.models.Favorite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class FavoriteJdbcTemplateRepositoryTest {

    @Autowired
    FavoriteJdbcTemplateRepository repository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("call set_known_good_state();");
    }

    @Test
    void findByUserId() throws DataAccessException {
        List<Favorite> favorites = repository.findByUserId(1);
        assertNotNull(favorites);
        assertEquals(3, favorites.size());
    }

    @Test
    void shouldAddTeam() throws DataAccessException{
        Favorite favorite = new Favorite();
        favorite.setUserId(2);
        favorite.setTeamId(1);
        Favorite actual = repository.add(favorite);
        assertNotNull(actual);
        assertEquals(3, actual.getFavoriteId());
    }
    @Test
    void shouldAddAthlete() throws DataAccessException{
        Favorite favorite = new Favorite();
        favorite.setUserId(2);
        favorite.setAthleteId(2);
        Favorite actual = repository.add(favorite);
        assertNotNull(actual);
        assertEquals(3, actual.getFavoriteId());
    }

    @Test
    void shouldNotAddTeam() {
        Favorite favorite = new Favorite();
        favorite.setUserId(2);
        favorite.setTeamId(0);
        assertThrows(DataIntegrityViolationException.class, () -> repository.add(favorite));
    }

    @Test
    void shouldNotAddAthlete() {
        Favorite favorite = new Favorite();
        favorite.setUserId(2);
        favorite.setAthleteId(0);
        assertThrows(DataIntegrityViolationException.class, () -> repository.add(favorite));
    }

    @Test
    void deleteByFaveTeam() throws DataAccessException {
        Favorite favorite = new Favorite();
        favorite.setFavoriteId(1);
        favorite.setUserId(1);
        favorite.setTeamId(1);
        assertTrue(repository.deleteByFave(favorite));
    }

    @Test
    void deleteByFaveAthlete() throws DataAccessException {
        Favorite favorite = new Favorite();
        favorite.setFavoriteId(1);
        favorite.setUserId(1);
        favorite.setAthleteId(1);
        assertTrue(repository.deleteByFave(favorite));
    }

    @Test
    void shouldNotDeleteTeam() throws DataAccessException {
        Favorite favorite = new Favorite();
        favorite.setFavoriteId(1);
        favorite.setUserId(1);
        favorite.setTeamId(0);
        assertFalse(repository.deleteByFave(favorite));
    }

    @Test
    void shouldNotDeleteAthlete() throws DataAccessException {
        Favorite favorite = new Favorite();
        favorite.setFavoriteId(1);
        favorite.setUserId(1);
        favorite.setAthleteId(0);
        assertFalse(repository.deleteByFave(favorite));
    }
}