package learn.wwsh.controllers;
import learn.wwsh.data.DataAccessException;
import learn.wwsh.domain.SportService;

import learn.wwsh.models.Sport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sport")
public class SportController {

    private final SportService service;

    public SportController(SportService service) {
        this.service = service;
    }

    @GetMapping // localhost:8080/api/sport
    public List<Sport> findAll() throws DataAccessException {
        return service.findAll();
    }

    @GetMapping("/{name}") // localhost:8080/api/sport/{name}
    public List<Sport> findAllByName(@PathVariable String name) throws DataAccessException {
        return service.findAllByName(name);
    }

    @GetMapping("league/{league}") // localhost:8080/api/sport/league/{league}
    public Sport findByLeague(@PathVariable String league) throws DataAccessException {
        return service.findByLeague(league);
    }

    @GetMapping("/id/{sportId}") // localhost:8080/api/sport/id/{sportId}
    public Sport findById(@PathVariable int sportId) throws DataAccessException {
        return service.findById(sportId);
    }

}
