package learn.wwsh.data.mappers;
import learn.wwsh.models.Sport;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SportMapper implements RowMapper<Sport> {
    @Override
    public Sport mapRow(ResultSet resultSet, int i) throws SQLException {
        Sport sport = new Sport();
        sport.setSportId(resultSet.getInt("sport_id"));
        sport.setName(resultSet.getString("name"));
        sport.setLeague(resultSet.getString("league"));
        return sport;
    }
}
