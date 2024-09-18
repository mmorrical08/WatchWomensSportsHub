package learn.wwsh.data;

import learn.wwsh.data.mappers.SportMapper;
import learn.wwsh.models.Sport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SportJdbcTemplateRepository implements SportRepository{

        private final JdbcTemplate jdbcTemplate;

        public SportJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }

        @Override
        public Sport findById(int sportId) throws DataAccessException {
            final String sql = "select sport_id, `name`, league from sport where sport_id = ?";
            return jdbcTemplate.query(sql, new SportMapper(), sportId).stream()
                    .findFirst()
                    .orElse(null);
        }

        @Override
        public List<Sport> findAllByName(String name) throws DataAccessException {
            name = "%" + name + "%";
            final String sql = "select sport_id, `name`, league from sport where `name` like ?";
            return jdbcTemplate.query(sql, new SportMapper(), name);
        }

        @Override
        public Sport findByLeague(String league) throws DataAccessException {
            league = "%" + league + "%";
            final String sql = "select sport_id, `name`, league from sport where league like ?";
            return jdbcTemplate.query(sql, new SportMapper(), league).stream()
                    .findFirst()
                    .orElse(null);
        }

        @Override
        public List<Sport> findAll() throws DataAccessException {
            final String sql = "select sport_id, `name`, league from sport";
            return jdbcTemplate.query(sql, new SportMapper());
        }
}
