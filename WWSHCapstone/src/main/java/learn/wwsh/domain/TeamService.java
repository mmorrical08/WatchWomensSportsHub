package learn.wwsh.domain;
import learn.wwsh.data.DataAccessException;
import learn.wwsh.data.TeamRepository;
import org.springframework.stereotype.Service;
import learn.wwsh.models.Team;

import java.util.List;

@Service
public class TeamService {

    private final TeamRepository repository;

    public TeamService(TeamRepository repository) {
        this.repository = repository;
    }

    public Team findById(int teamId) throws DataAccessException {
        return repository.findById(teamId);
    }

    public List<Team> findBySportId(int sportId) throws DataAccessException {
        return repository.findBySportId(sportId);
    }

    public List<Team> findAllByName(String name) throws DataAccessException {
        return repository.findAllByName(name);
    }

    public List<Team> findAll() throws DataAccessException {
        return repository.findAll();
    }

    public Team add(Team team) throws DataAccessException {
        return repository.add(team);
    }

    public boolean deleteById(int teamId) throws DataAccessException {
        return repository.deleteById(teamId);
    }

}
