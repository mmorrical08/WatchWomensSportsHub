package learn.wwsh.data;
import learn.wwsh.models.Athlete;

import java.util.List;

public interface AthleteRepository {

    Athlete findById(int athleteId) throws DataAccessException;

    List<Athlete> findAllByTeamId(int teamId) throws DataAccessException;

    List<Athlete> findAll() throws DataAccessException;

    List<Athlete> findByName(String name) throws DataAccessException;

    Athlete add(Athlete athlete) throws DataAccessException;

    boolean update(Athlete athlete) throws DataAccessException;

}
