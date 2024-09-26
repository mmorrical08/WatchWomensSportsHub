package learn.wwsh.data;

import learn.wwsh.data.mappers.FavoriteAthleteMapper;
import learn.wwsh.data.mappers.FavoriteTeamMapper;
import learn.wwsh.models.Favorite;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class FavoriteJdbcTemplateRepository implements FavoriteRepository {

    private final JdbcTemplate jdbcTemplate;

    public FavoriteJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Favorite> findByUserId(int userId) throws DataAccessException {
        final String sql = "select favorite_team_id, app_user_id, team_id "
                + "from favorite_team "
                + "where app_user_id = ?;";
        List<Favorite> favorites = new ArrayList<>(jdbcTemplate.query(sql, new FavoriteTeamMapper(), userId));

        final String sql2 = "select favorite_athlete_id, app_user_id, athlete_id "
                + "from favorite_athlete "
                + "where app_user_id = ?;";
        favorites.addAll(jdbcTemplate.query(sql2, new FavoriteAthleteMapper(), userId));
        return favorites;
    }

    @Override
    public Favorite add(Favorite favorite) throws DataAccessException {
        if (favorite.getAthleteId()==0) {
            return addTeam(favorite);
        } else {
            return addAthlete(favorite);
        }
    }

    public Favorite addTeam(Favorite favorite) throws DataAccessException {
        final String sql = "insert into favorite_team (app_user_id, team_id) "
                + "values (?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("favorite_team")
                .usingGeneratedKeyColumns("favorite_team_id");
        Map<String, Object> fields = Map.of(
                "app_user_id", favorite.getUserId(),
                "team_id", favorite.getTeamId()
        );
        int favoriteId = insert.executeAndReturnKey(fields).intValue();

        Favorite result = favorite;
        result.setFavoriteId(favoriteId);

        return result;
    }

    public Favorite addAthlete(Favorite favorite) throws DataAccessException {
        final String sql = "insert into favorite_athlete (app_user_id, athlete_id) "
                + "values (?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("favorite_athlete")
                .usingGeneratedKeyColumns("favorite_athlete_id");
        Map<String, Object> fields = Map.of(
                "app_user_id", favorite.getUserId(),
                "athlete_id", favorite.getAthleteId()
        );
        int favoriteId = insert.executeAndReturnKey(fields).intValue();

        Favorite result = favorite;
        result.setFavoriteId(favoriteId);

        return result;
    }

    @Override
    public boolean deleteByFave(Favorite favorite) throws DataAccessException {
        if (favorite.getAthleteId()==0) {
            return deleteTeam(favorite);
        } else {
            return deleteAthlete(favorite);
        }
    }

    public boolean deleteTeam(Favorite favorite) throws DataAccessException {
        final String sql = "delete from favorite_team "
                + "where app_user_id = ? and team_id = ?;";
        return jdbcTemplate.update(sql, favorite.getUserId(), favorite.getTeamId()) > 0;
    }

    public boolean deleteAthlete(Favorite favorite) throws DataAccessException {
        final String sql = "delete from favorite_athlete "
                + "where app_user_id = ? and athlete_id = ?;";
        return jdbcTemplate.update(sql, favorite.getUserId(), favorite.getAthleteId()) > 0;
    }
}
