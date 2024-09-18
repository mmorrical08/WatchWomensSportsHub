package learn.wwsh.domain;

import learn.wwsh.data.DataAccessException;
import learn.wwsh.data.SportRepository;

import learn.wwsh.models.Sport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static learn.wwsh.domain.MockData.makeSport;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SportServiceTest {

    @MockBean
    private SportRepository repository;

    @Autowired
    private SportService service;

    @Test
    void shouldFindFindById() throws DataAccessException {
        when(repository.findById(1)).thenReturn(makeSport());
        Sport actual = service.findById(1);
        Sport expected = makeSport();
        assertEquals(expected.getLeague(), actual.getLeague());
        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    void shouldFindByLeague() throws DataAccessException {
        when(repository.findByLeague("wnba")).thenReturn(makeSport());
        Sport actual = service.findByLeague("wnba");
        Sport expected = makeSport();
        assertEquals(expected.getLeague(), actual.getLeague());
        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    void shouldFindAll() throws DataAccessException {
        when(repository.findAll()).thenReturn(List.of(makeSport()));
        assertEquals(1, service.findAll().size());
    }

    @Test
    void shouldFindAllByName() throws DataAccessException {
        when(repository.findAllByName("basketball")).thenReturn(List.of(makeSport()));
        assertEquals(1, service.findAllByName("basketball").size());
    }

    @Test
    void shouldNotFindAllByName() throws DataAccessException {
        when(repository.findAllByName("football")).thenReturn(List.of());
        assertEquals(0, service.findAllByName("football").size());
    }

    @Test
    void shouldNotFindById() throws DataAccessException {
        when(repository.findById(2)).thenReturn(null);
        assertEquals(null, service.findById(2));
    }

    @Test
    void shouldNotFindByLeague() throws DataAccessException {
        when(repository.findByLeague("nba")).thenReturn(null);
        assertEquals(null, service.findByLeague("nba"));
    }


}
