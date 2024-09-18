package learn.wwsh.domain;

import learn.wwsh.data.DataAccessException;
import learn.wwsh.data.FavoriteRepository;
import learn.wwsh.models.Favorite;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static learn.wwsh.domain.MockData.makeFavoriteAthlete;
import static learn.wwsh.domain.MockData.makeFavoriteTeam;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class FavoriteServiceTest {

    @MockBean
    FavoriteRepository repository;

    @Autowired
    FavoriteService service;

    @Test
    void shouldFindByUserId() throws DataAccessException {
        when(repository.findByUserId(1)).thenReturn(List.of(
                makeFavoriteTeam(),
                makeFavoriteAthlete()
        ));
        assertEquals(2, service.findByUserId(1).size());
    }
    @Test
    void shouldNotFindByUserId() throws DataAccessException {
        when(repository.findByUserId(1)).thenReturn(List.of());
        assertEquals(0, service.findByUserId(1).size());
    }

    @Test
    void shouldAddFavoriteTeam() throws DataAccessException {
        Favorite favorite = makeFavoriteTeam();
        when(repository.add(favorite)).thenReturn(favorite);
        try {
            Favorite actual = service.add(favorite);
            assertNotNull(actual);
            assertEquals(favorite.getFavoriteId(), actual.getFavoriteId());
        } catch (DataAccessException ex) {
            fail(ex);
        }

    }
    @Test
    void shouldAddFavoriteAthlete() throws DataAccessException {
        Favorite favorite = makeFavoriteAthlete();
        when(repository.add(favorite)).thenReturn(favorite);
        try {
            Favorite actual = service.add(favorite);
            assertNotNull(actual);
            assertEquals(favorite.getFavoriteId(), actual.getFavoriteId());
        } catch (DataAccessException ex) {
            fail(ex);
        }
    }

    @Test
    void shouldDeleteByFaveTeam() throws DataAccessException {
        Favorite favorite = makeFavoriteTeam();
        when(repository.deleteByFave(favorite)).thenReturn(true);
        try {
            assertTrue(service.deleteByFave(favorite));
        } catch (DataAccessException ex) {
            fail(ex);
        }
    }
    @Test
    void shouldDeleteByFaveAthlete() throws DataAccessException {
        Favorite favorite = makeFavoriteAthlete();
        when(repository.deleteByFave(favorite)).thenReturn(true);
        try {
            assertTrue(service.deleteByFave(favorite));
        } catch (DataAccessException ex) {
            fail(ex);
        }
    }
}