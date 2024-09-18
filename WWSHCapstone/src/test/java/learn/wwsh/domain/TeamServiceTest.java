package learn.wwsh.domain;

import learn.wwsh.data.DataAccessException;
import learn.wwsh.data.TeamRepository;

import learn.wwsh.models.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static learn.wwsh.domain.MockData.makeTeam;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TeamServiceTest {

    @MockBean
    private TeamRepository repository;

    @Autowired
    private TeamService service;

    @Test
    void shouldFindFindById() throws DataAccessException {
        when(repository.findById(1)).thenReturn(makeTeam());
        Team actual = service.findById(1);
        Team expected = makeTeam();
        assertEquals(expected.getTeamName(), actual.getTeamName());
        assertEquals(expected.getCity(), actual.getCity());
    }

    @Test
    void shouldFindBySportId() throws DataAccessException {
        when(repository.findBySportId(1)).thenReturn(List.of(makeTeam()));
        assertEquals(1, service.findBySportId(1).size());
    }

    @Test
    void shouldFindAll() throws DataAccessException {
        when(repository.findAll()).thenReturn(List.of(makeTeam()));
        assertEquals(1, service.findAll().size());
    }

    @Test
    void shouldFindAllByName() throws DataAccessException {
        when(repository.findAllByName("Storm")).thenReturn(List.of(makeTeam()));
        assertEquals(1, service.findAllByName("Storm").size());
    }

    @Test
    void shouldNotFindAllByName() throws DataAccessException {
        when(repository.findAllByName("Seahawks")).thenReturn(List.of());
        assertEquals(0, service.findAllByName("Seahawks").size());
    }

    @Test
    void shouldAdd() throws DataAccessException {
        Team team = makeTeam();
        when(repository.add(team)).thenReturn(team);
        Team actual = service.add(team);
        assertEquals(team, actual);
    }

    @Test
    void shouldDeleteById() throws DataAccessException {
        when(repository.deleteById(1)).thenReturn(true);
        assertEquals(true, service.deleteById(1));
    }

    @Test
    void shouldNotDeleteById() throws DataAccessException {
        when(repository.deleteById(2)).thenReturn(false);
        assertEquals(false, service.deleteById(2));
    }

    @Test
    void shouldNotFindById() throws DataAccessException {
        when(repository.findById(2)).thenReturn(null);
        assertEquals(null, service.findById(2));
    }


}
