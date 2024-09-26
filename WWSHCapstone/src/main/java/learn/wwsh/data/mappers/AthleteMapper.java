package learn.wwsh.data.mappers;

import learn.wwsh.models.Athlete;
import org.springframework.jdbc.core.RowMapper;

public class AthleteMapper implements RowMapper<Athlete> {
    @Override
    public Athlete mapRow(java.sql.ResultSet resultSet, int i) throws java.sql.SQLException {
        Athlete athlete = new Athlete();
        athlete.setAthleteId(resultSet.getInt("athlete_id"));
        athlete.setTeamId(resultSet.getInt("team_id"));
        athlete.setFirstName(resultSet.getString("first_name"));
        athlete.setLastName(resultSet.getString("last_name"));
        athlete.setHeight(resultSet.getInt("height"));
        athlete.setDisplayHeight(resultSet.getString("display_height"));
        athlete.setHeadshot(resultSet.getString("headshot"));
        athlete.setJersey(resultSet.getInt("jersey"));
        athlete.setPositionName(resultSet.getString("position_name"));
        athlete.setExperienceYear(resultSet.getString("experience_year"));

        return athlete;
    }
}
