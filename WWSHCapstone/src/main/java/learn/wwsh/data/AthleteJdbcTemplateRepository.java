package learn.wwsh.data;
import learn.wwsh.data.mappers.AthleteMapper;
import learn.wwsh.models.Athlete;
import learn.wwsh.data.AthleteRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public class AthleteJdbcTemplateRepository implements AthleteRepository{

    private final JdbcTemplate jdbcTemplate;

    public AthleteJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Athlete findById(int athleteId) {
        final String sql = "select athlete_id, " +
                "team_id, " +
                "first_name, last_name, " +
                "height, display_height, " +
                "headshot, jersey, " +
                "position_name, experience_year "
                + "from athlete "
                + "where athlete_id = ?;";
        return jdbcTemplate.query(sql, new AthleteMapper(), athleteId).stream()
                .findFirst().orElse(null);
    }

    @Override
    public List<Athlete> findAllByTeamId(int teamId) {
        final String sql = "select athlete_id, " +
                "team_id, " +
                "first_name, last_name, " +
                "height, display_height, " +
                "headshot, jersey, " +
                "position_name, experience_year "
                + "from athlete "
                + "where team_id = ?;";
        return jdbcTemplate.query(sql, new AthleteMapper(), teamId);
    }

    @Override
    public List<Athlete> findAll() {
        final String sql = "select athlete_id, " +
                "team_id, " +
                "first_name, last_name, " +
                "height, display_height, " +
                "headshot, jersey, " +
                "position_name, experience_year "
                + "from athlete;";
        return jdbcTemplate.query(sql, new AthleteMapper());
    }

    @Override
    public List<Athlete> findByName(String name) {
        name = "%" + name + "%";
        final String sql = "select athlete_id, " +
                "team_id, " +
                "first_name, last_name, " +
                "height, display_height, " +
                "headshot, jersey, " +
                "position_name, experience_year "
                + "from athlete "
                + "where first_name like ? or last_name like ?;";
        return jdbcTemplate.query(sql, new AthleteMapper(), name, name);
    }

    @Override
    public Athlete add(Athlete athlete) {

        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("athlete")
                .usingGeneratedKeyColumns("athlete_id");

        Map<String, Object> fields = Map.of(
                "team_id", athlete.getTeamId(),
                "first_name", athlete.getFirstName(),
                "last_name", athlete.getLastName(),
                "height", athlete.getHeight(),
                "display_height", athlete.getDisplayHeight(),
                "headshot", athlete.getHeadshot(),
                "jersey", athlete.getJersey(),
                "position_name", athlete.getPositionName(),
                "experience_year", athlete.getExperienceYear()
        );

        int id = insert.executeAndReturnKey(fields).intValue();
        athlete.setAthleteId(id);
        return athlete;
    }

    @Override
    public boolean update(Athlete athlete) {
        final String sql = "update athlete set "
                + "team_id = ?, "
                + "first_name = ?, "
                + "last_name = ?, "
                + "height = ?, "
                + "display_height = ?, "
                + "headshot = ?, "
                + "jersey = ?, "
                + "position_name = ?, "
                + "experience_year = ? "
                + "where athlete_id = ?;";
        return jdbcTemplate.update(sql,
                athlete.getTeamId(),
                athlete.getFirstName(),
                athlete.getLastName(),
                athlete.getHeight(),
                athlete.getDisplayHeight(),
                athlete.getHeadshot(),
                athlete.getJersey(),
                athlete.getPositionName(),
                athlete.getExperienceYear(),
                athlete.getAthleteId()) > 0;
    }

}
