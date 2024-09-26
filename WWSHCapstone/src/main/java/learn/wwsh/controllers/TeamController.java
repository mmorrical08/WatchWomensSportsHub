package learn.wwsh.controllers;

import learn.wwsh.data.DataAccessException;
import learn.wwsh.domain.TeamService;
import learn.wwsh.models.Team;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/team")
public class TeamController {

    private final TeamService service;

    public TeamController(TeamService service) {
        this.service = service;
    }

    @GetMapping
    public List<Team> findAll() throws DataAccessException {
        return service.findAll();
    }

    @GetMapping("/{name}")
    public List<Team> findAllByName(String name) throws DataAccessException {
        return service.findAllByName(name);
    }

    @GetMapping("/{sportId}")
    public List<Team> findBySportId(int sportId) throws DataAccessException {
        return service.findBySportId(sportId);
    }

    @GetMapping("/id/{teamId}")
    public Team findById(@PathVariable int teamId) throws DataAccessException {
        return service.findById(teamId);
    }

    @PostMapping("/add")
    public Team add(@RequestBody Team team) throws DataAccessException {
        return service.add(team);
    }

    @DeleteMapping("/delete/{teamId}")
    public boolean deleteById(int teamId) throws DataAccessException {
        return service.deleteById(teamId);
    }
}
