package learn.wwsh.domain;

import learn.wwsh.domain.Result;
import learn.wwsh.data.AthleteRepository;
import learn.wwsh.data.DataAccessException;
import learn.wwsh.models.Athlete;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AthleteService {
    private final AthleteRepository repository;

    public AthleteService(AthleteRepository repository) {
        this.repository = repository;
    }

    public Athlete findById(int athleteId) throws DataAccessException {
        return repository.findById(athleteId);
    }

    public List<Athlete> findAllByTeamId(int teamId) throws DataAccessException {
        return repository.findAllByTeamId(teamId);
    }

    public List<Athlete> findAll() throws DataAccessException {
        return repository.findAll();
    }

    public List<Athlete> findByName(String name) throws DataAccessException {
        return repository.findByName(name);
    }

    public Result<Athlete> add(Athlete athlete) throws DataAccessException {
        Result<Athlete> result = validate(athlete);
        if (result.isSuccess()) {
            return result;
        }

        if (athlete.getAthleteId() != 0) {
            result.addMessage(ActionStatus.INVALID, "athleteId cannot be set for `add` operation.");
            return result;
        }

        athlete = repository.add(athlete);
        result.setPayload(athlete);
        return result;
    }

    public Result<Athlete> update(Athlete athlete) throws DataAccessException {
        Result<Athlete> result = validate(athlete);
        if (result.isSuccess()) {
            return result;
        }

        if (athlete.getAthleteId() <= 0) {
            result.addMessage(ActionStatus.INVALID, "athleteId is required for `update` operation.");
            return result;
        }

        if (!repository.update(athlete)) {
            result.addMessage(ActionStatus.NOT_FOUND, "athleteId not found.");
        }

        return result;
    }

    private Result<Athlete> validate(Athlete athlete) {
        Result<Athlete> result = new Result<>();
        if (athlete == null) {
            result.addMessage(ActionStatus.INVALID, "athlete cannot be null.");
            return result;
        }

        if (Validations.isNullOrEmpty(athlete.getFirstName())) {
            result.addMessage(ActionStatus.INVALID, "firstName is required.");
        }

        if (Validations.isNullOrEmpty(athlete.getLastName())) {
            result.addMessage(ActionStatus.INVALID, "lastName is required.");
        }

        if (Validations.isNullOrEmpty(athlete.getPositionName())) {
            result.addMessage(ActionStatus.INVALID, "positionName is required.");
        }
        return result;
    }
}
