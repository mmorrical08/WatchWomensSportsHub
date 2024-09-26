package learn.wwsh.data;

import learn.wwsh.data.mappers.TeamMapper;
import learn.wwsh.models.Team;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class TeamJdbcRepository implements TeamRepository {
    private final JdbcTemplate jdbcTemplate;

    public TeamJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Team findById(int teamId) throws DataAccessException {
        final String sql = "select team_id, " +
                "sport_id, name, city, " +
                "state, default_logo, " +
                "dark_logo " +
                "from team " +
                "where team_id = ?";
        return jdbcTemplate.query(sql, new TeamMapper(), teamId).stream().findFirst().orElse(null);
    }

    @Override
    public List<Team> findAllByName(String name) throws DataAccessException {
        name = "%" + name + "%";
        final String sql = "select team_id, " +
                "sport_id, name, city, " +
                "state, default_logo, " +
                "dark_logo " +
                "from team " +
                "where name like ?";
        return jdbcTemplate.query(sql, new TeamMapper(), name);
    }

    @Override
    public List<Team> findBySportId(int sportId) throws DataAccessException {
        final String sql = "select team_id, " +
                "sport_id, name, city, " +
                "state, default_logo, " +
                "dark_logo " +
                "from team " +
                "where sport_id = ?";
        return jdbcTemplate.query(sql, new TeamMapper(), sportId);
    }

    @Override
    public List<Team> findAll() throws DataAccessException {
        final String sql = "select team_id, " +
                "sport_id, name, city, " +
                "state, default_logo, " +
                "dark_logo " +
                "from team";
        return jdbcTemplate.query(sql, new TeamMapper());
    }

    @Override
    public Team add(Team team) throws DataAccessException {
        if (team == null) {
            return null;
        }

        // First, check if the team already exists by its name, city, and sport_id.
        final String selectSql = "SELECT team_id FROM team WHERE name = ? AND city = ? AND sport_id = ?";

        List<Integer> existingTeamIds = jdbcTemplate.query(selectSql, (rs, rowNum) -> rs.getInt("team_id"),
                team.getTeamName(), team.getCity(), team.getSportId());

        if (!existingTeamIds.isEmpty()) {
            // If the team exists, return the existing team with its ID
            team.setTeamId(existingTeamIds.get(0));
            return team;
        }

        // If the team doesn't exist, insert the new team
        final String insertSql = "INSERT INTO team (sport_id, name, city, state, default_logo, dark_logo) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("team");

        Map<String, Object> fields = Map.of(
                "team_id", team.getTeamId(),
                "sport_id", team.getSportId(),
                "name", team.getTeamName(),
                "city", team.getCity(),
                "state", team.getState(),
                "default_logo", team.getDefaultLogo(),
                "dark_logo", team.getDarkLogo()
        );

        insert.execute(fields);

        return team;
    }



    @Override
    public boolean deleteById(int teamId) throws DataAccessException {
        final String sql1 = "delete from favorite_team where team_id = ?";
        jdbcTemplate.update(sql1, teamId);

        final String sql2 = "update athlete set team_id = null where team_id = ?";
        jdbcTemplate.update(sql2, teamId);


        final String sql = "delete from team where team_id = ?";
        return jdbcTemplate.update(sql, teamId) > 0;
    }
}