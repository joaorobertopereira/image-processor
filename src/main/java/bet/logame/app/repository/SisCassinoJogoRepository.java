package bet.logame.app.repository;

import bet.logame.app.model.SisCassinoJogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SisCassinoJogoRepository extends JpaRepository<SisCassinoJogo, Integer> {
    Optional<SisCassinoJogo> findByGameidAndFornecedor(String gameid, String fornecedor);
}
