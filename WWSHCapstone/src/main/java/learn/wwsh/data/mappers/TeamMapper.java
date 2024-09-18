package learn.wwsh.data.mappers;

import learn.wwsh.models.Team;
import org.springframework.jdbc.core.RowMapper;

public class TeamMapper implements RowMapper<Team> {
    @Override
    public Team mapRow(java.sql.ResultSet resultSet, int i) throws java.sql.SQLException {
        Team team = new Team();
        team.setTeamId(resultSet.getInt("team_id"));
        team.setSportId(resultSet.getInt("sport_id"));
        team.setTeamName(resultSet.getString("name"));
        team.setCity(resultSet.getString("city"));
        team.setState(resultSet.getString("state"));
        if (resultSet.getString("default_logo")!=null){
            team.setDefaultLogo(resultSet.getString("default_logo"));
        }
        if (resultSet.getString("dark_logo")!=null) {
            team.setDarkLogo(resultSet.getString("dark_logo"));
        }

        return team;
    }
}
