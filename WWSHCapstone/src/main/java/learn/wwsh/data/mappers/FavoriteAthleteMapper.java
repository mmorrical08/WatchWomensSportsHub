package learn.wwsh.data.mappers;

import learn.wwsh.models.Favorite;

import org.springframework.jdbc.core.RowMapper;

public class FavoriteAthleteMapper implements RowMapper<Favorite> {
    @Override
    public Favorite mapRow(java.sql.ResultSet resultSet, int i) throws java.sql.SQLException {
        Favorite favorite = new Favorite();
        favorite.setFavoriteId(resultSet.getInt("favorite_athlete_id"));
        favorite.setUserId(resultSet.getInt("app_user_id"));
        favorite.setTeamId(0);
        favorite.setAthleteId(resultSet.getInt("athlete_id"));

        return favorite;
    }
}
