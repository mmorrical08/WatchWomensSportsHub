package learn.wwsh.data;

import learn.wwsh.models.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TeamJdbcRepositoryTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    TeamJdbcRepository repository;

    @BeforeEach
    void beforeEach() {
        jdbcTemplate.execute("call set_known_good_state();");
    }

    @Test
    void shouldFindById() throws DataAccessException {
        Team team = repository.findById(1);
        assertNotNull(team);
        assertEquals(1, team.getTeamId());
        assertEquals(1, team.getSportId());
        assertEquals("Team 1", team.getTeamName());
        assertEquals("City 1", team.getCity());
        assertEquals("ST", team.getState());
        assertEquals("default1.png", team.getDefaultLogo());
        assertEquals("dark1.png", team.getDarkLogo());

        assertEquals(null, repository.findById(100));
    }

    @Test
    void shouldFindAllByName() throws DataAccessException {
        assertEquals(2, repository.findAllByName("Team").size());
        assertEquals(0, repository.findAllByName("Team 100").size());
    }

    @Test
    void shouldFindBySportId() throws DataAccessException {
        assertEquals(1, repository.findBySportId(1).size());
        assertEquals(0, repository.findBySportId(100).size());
    }

    @Test
    void findAll() throws DataAccessException {
        assertEquals(2, repository.findAll().size());
    }

    @Test
    void shouldAdd() throws DataAccessException {
        Team team = new Team();
        team.setSportId(1);
        team.setTeamName("New Team");
        team.setCity("New City");
        team.setState("NC");
        team.setDefaultLogo("new-default.png");
        team.setDarkLogo("new-dark.png");

        team = repository.add(team);
        assertNotNull(team);
        assertEquals(3, team.getTeamId());
    }


    @Test
    void deleteById() throws DataAccessException {
        assertTrue(repository.deleteById(1));
        assertFalse(repository.deleteById(100));
    }
}