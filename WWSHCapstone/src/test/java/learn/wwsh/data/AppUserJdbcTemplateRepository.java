package learn.wwsh.data;

import learn.wwsh.models.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

@SpringBootTest
class AppUserJdbcTemplateRepositoryTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    AppUserJdbcTemplateRepository repository;

    static boolean hasRun = false;

    @BeforeEach
    void setup() {
        if (!hasRun) {
            jdbcTemplate.update("call set_known_good_state();");
            hasRun = true;
        }
    }

    @Test
    void shouldFindJohnSmithByUsername() {
        AppUser actual = repository.findByUsername("john@smith.com");

        assertTrue(actual.isEnabled());
        assertEquals(1, actual.getAuthorities().size());
        assertTrue(actual.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN")));
    }

    @Test
    void shouldFindSallyJonesByUsername() {
        AppUser actual = repository.findByUsername("sally@jones.com");

        assertEquals(1, actual.getAuthorities().size());
        assertTrue(actual.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER")));
    }

    @Test
    void shouldCreatePaiTongsukum() {
        AppUser appUser = new AppUser(0, "paitongsukum", "strongPassPhrase", true, List.of("USER"));

        AppUser actual = repository.create(appUser);

        assertEquals(3, actual.getAppUserId());

        AppUser pai = repository.findByUsername("paitongsukum");

        assertTrue(pai.isEnabled());
        assertEquals("paitongsukum", pai.getUsername());
        assertEquals("strongPassPhrase", pai.getPassword());
        assertEquals(1, pai.getAuthorities().size());
        assertTrue(pai.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER")));
    }

}