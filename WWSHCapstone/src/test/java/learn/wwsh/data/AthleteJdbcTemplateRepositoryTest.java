package learn.wwsh.data;

import learn.wwsh.models.Athlete;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AthleteJdbcTemplateRepositoryTest {

    @Autowired
    AthleteJdbcTemplateRepository repository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("call set_known_good_state();");
    }

    @Test
    void findById() {
        Athlete athlete = repository.findById(1);
        assertNotNull(athlete);
        assertEquals(1, athlete.getAthleteId());
        assertEquals(1, athlete.getTeamId());
        assertEquals("First 1", athlete.getFirstName());
    }

    @Test
    void findByIdMissing() {
        Athlete athlete = repository.findById(1000);
        assertNull(athlete);
    }

    @Test
    void findAllByTeamId() {
        List<Athlete> athletes = repository.findAllByTeamId(1);
        assertNotNull(athletes);
        assertEquals(1, athletes.size());
    }

    @Test
    void findAllByTeamIdMissing() {
        List<Athlete> athletes = repository.findAllByTeamId(1000);
        assertNotNull(athletes);
        assertEquals(0, athletes.size());
    }

    @Test
    void findAll() {
        List<Athlete> athletes = repository.findAll();
        assertNotNull(athletes);
        assertEquals(2, athletes.size());
    }

    @Test
    void findByName() {
        List<Athlete> athletes = repository.findByName("First");
        assertNotNull(athletes);
        assertEquals(2, athletes.size());
    }

    @Test
    void findByNameMissing() {
        List<Athlete> athletes = repository.findByName("Missing");
        assertNotNull(athletes);
        assertEquals(0, athletes.size());
    }

    @Test
    void add() {
        Athlete athlete = new Athlete();
        athlete.setTeamId(1);
        athlete.setFirstName("Test");
        athlete.setLastName("Athlete");
        athlete.setHeight(72);
        athlete.setDisplayHeight(6);
        athlete.setHeadshot("test.jpg");
        athlete.setJersey(99);
        athlete.setPositionName("Test Position");
        athlete.setExperienceYear("Test Year");

        athlete = repository.add(athlete);
        assertNotNull(athlete);
        assertEquals(3, athlete.getAthleteId());
    }

    @Test
    void update() {
        Athlete athlete = repository.findById(1);
        athlete.setFirstName("Updated");
        assertTrue(repository.update(athlete));
        assertEquals("Updated", repository.findById(1).getFirstName());

        athlete.setAthleteId(1000);

    }
}