package learn.wwsh.domain;

import learn.wwsh.data.DataAccessException;
import learn.wwsh.data.SportRepository;
import learn.wwsh.models.Sport;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SportService {
    private final SportRepository repository;

    public SportService(SportRepository repository) {
        this.repository = repository;
    }

    public Sport findById(int sportId) throws DataAccessException {
        return repository.findById(sportId);
    }

    public Sport findByLeague(String league) throws DataAccessException {
        return repository.findByLeague(league);
    }

    public List<Sport> findAll() throws DataAccessException {
        return repository.findAll();
    }

    public List<Sport> findAllByName(String name) throws DataAccessException {
        return repository.findAllByName(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SportService that = (SportService) o;
        return Objects.equals(repository, that.repository);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(repository);
    }
}