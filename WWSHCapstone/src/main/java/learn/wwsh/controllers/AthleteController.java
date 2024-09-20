package learn.wwsh.controllers;

import learn.wwsh.data.DataAccessException;
import learn.wwsh.domain.AthleteService;
import learn.wwsh.domain.Result;
import learn.wwsh.domain.ResultType;
import learn.wwsh.models.Athlete;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/athlete")
public class AthleteController {

    private final AthleteService service;

    public AthleteController(AthleteService service) {
        this.service = service;
    }

    @GetMapping
    public List<Athlete> findAll() throws DataAccessException {
        return service.findAll();
    }

    @GetMapping("/team/{teamId}")
    public List<Athlete> findAllByTeamId(@PathVariable int teamId) throws DataAccessException {
        return service.findAllByTeamId(teamId);
    }

    @GetMapping("/{athleteId}")
    public Athlete findById(@PathVariable int athleteId) throws DataAccessException {
        return service.findById(athleteId);
    }

    @GetMapping("/name/{name}")
    public List<Athlete> findByName(@PathVariable String name) throws DataAccessException {
        return service.findByName(name);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Athlete athlete) throws DataAccessException {
        Result<Athlete> result = service.add(athlete);
        if (result.isSuccess()) {
            return ResponseEntity.badRequest().body(result.getMessages());
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Athlete athlete) throws DataAccessException {
        Result<Athlete> result = service.update(athlete);
        if (result.isSuccess()) {
            return ResponseEntity.badRequest().body(result.getMessages());
        }
        return ResponseEntity.ok(result);
    }

}
