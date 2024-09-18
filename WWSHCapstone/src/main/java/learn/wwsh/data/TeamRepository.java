package learn.wwsh.data;

import learn.wwsh.models.Team;

import java.util.List;

public interface TeamRepository {
    Team findById(int teamId) throws DataAccessException;

    List<Team> findAllByName(String name) throws DataAccessException;

    List<Team> findBySportId(int sportId) throws DataAccessException;

    List<Team> findAll() throws DataAccessException;

    Team add(Team team) throws DataAccessException;

    boolean deleteById(int teamId) throws DataAccessException;
}
