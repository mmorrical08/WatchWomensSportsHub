package learn.wwsh.data.mappers;

import learn.wwsh.models.Favorite;
import org.springframework.jdbc.core.RowMapper;

public class FavoriteTeamMapper implements RowMapper<Favorite> {
    @Override
    public Favorite mapRow(java.sql.ResultSet resultSet, int i) throws java.sql.SQLException {
        Favorite favorite = new Favorite();
        favorite.setFavoriteId(resultSet.getInt("favorite_team_id"));
        favorite.setUserId(resultSet.getInt("app_user_id"));
        favorite.setTeamId(resultSet.getInt("team_id"));
        favorite.setAthleteId(0);

        return favorite;
    }
}
