package learn.wwsh.domain;

import learn.wwsh.data.AthleteRepository;
import learn.wwsh.data.DataAccessException;
import learn.wwsh.models.Athlete;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static learn.wwsh.domain.MockData.makeAthlete;
import static learn.wwsh.domain.MockData.makeAthlete2;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AthleteServiceTest {

    @MockBean
    AthleteRepository repository;

    @Autowired
    AthleteService service;

    @Test
    void findById() throws DataAccessException {
        when(repository.findById(1)).thenReturn(makeAthlete());
        Athlete actual = service.findById(1);
        assertNotNull(actual);
        assertEquals(1, actual.getAthleteId());
        assertEquals(1, actual.getTeamId());
        assertEquals("First 1", actual.getFirstName());
    }

    @Test
    void findAllByTeamId() throws DataAccessException {
        when(repository.findAllByTeamId(1)).thenReturn(List.of(makeAthlete(), makeAthlete2()));
        List<Athlete> actual = service.findAllByTeamId(1);
        assertNotNull(actual);
        assertEquals(2, actual.size());
    }

    @Test
    void findAll() throws DataAccessException {
        when(repository.findAll()).thenReturn(List.of(makeAthlete(), makeAthlete2()));
        List<Athlete> actual = service.findAll();
        assertNotNull(actual);
        assertEquals(2, actual.size());
    }

    @Test
    void findByName() throws DataAccessException {
        when(repository.findByName("First")).thenReturn(List.of(makeAthlete(), makeAthlete2()));
        List<Athlete> actual = service.findByName("First");
        assertNotNull(actual);
        assertEquals(2, actual.size());
    }
    @Test
    void shouldNotFindMissingName() throws DataAccessException {
        when(repository.findByName("Missing")).thenReturn(List.of());
        List<Athlete> actual = service.findByName("Missing");
        assertNotNull(actual);
        assertEquals(0, actual.size());
    }

    @Test
    void add() throws DataAccessException {
        Athlete athlete = makeAthlete();
        athlete.setAthleteId(0);
        Athlete mockOut = makeAthlete();
        mockOut.setAthleteId(1);

        when(repository.add(athlete)).thenReturn(mockOut);
        Athlete actual = service.add(athlete).getPayload();
        assertNotNull(actual);
        assertEquals(1, actual.getAthleteId());

    }
    @Test
    void shouldNotAddWithId() throws DataAccessException {
        Athlete athlete = makeAthlete();
        athlete.setAthleteId(1);
        Athlete mockOut = makeAthlete();
        mockOut.setAthleteId(1);

        when(repository.add(athlete)).thenReturn(mockOut);
        Athlete actual = service.add(athlete).getPayload();
        assertNull(actual);
    }

    @Test
    void update() throws DataAccessException {
        Athlete athlete = makeAthlete();
        athlete.setAthleteId(1);
        when(repository.update(athlete)).thenReturn(true);
        assertTrue(service.update(athlete).isSuccess());
    }

    @Test
    void shouldNotUpdateMissing() throws DataAccessException {
        Athlete athlete = makeAthlete();
        athlete.setAthleteId(1);
        when(repository.update(athlete)).thenReturn(false);
        assertTrue(service.update(athlete).isSuccess());
    }
}