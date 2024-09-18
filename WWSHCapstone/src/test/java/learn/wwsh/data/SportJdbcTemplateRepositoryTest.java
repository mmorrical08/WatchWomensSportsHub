package learn.wwsh.data;

import learn.wwsh.models.Sport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class SportJdbcTemplateRepositoryTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    SportJdbcTemplateRepository repository;

    @BeforeEach
    void beforeEach() {
        jdbcTemplate.update("call set_known_good_state();");
    }

    @Test
    void findById() throws DataAccessException {
        Sport sport = repository.findById(1);
        assertNotNull(sport);
        assertEquals(1, sport.getSportId());
        assertEquals("basketball", sport.getName());
        assertEquals("wnba", sport.getLeague());
    }

    @Test
    void findAllByName() throws DataAccessException {
        assertEquals(2, repository.findAllByName("basketball").size());
        assertEquals(0, repository.findAllByName("soccer").size());
    }

    @Test
    void findByLeague() throws DataAccessException {
        Sport sport = repository.findByLeague("wnba");
        assertNotNull(sport);
        assertEquals(1, sport.getSportId());
        assertEquals("basketball", sport.getName());
        assertEquals("wnba", sport.getLeague());
    }

    @Test
    void findAll() throws DataAccessException {
        assertEquals(2, repository.findAll().size());
    }
}