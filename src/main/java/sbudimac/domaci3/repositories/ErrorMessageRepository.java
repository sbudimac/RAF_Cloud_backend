package sbudimac.domaci3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sbudimac.domaci3.model.ErrorMessage;

import java.util.List;

public interface ErrorMessageRepository extends JpaRepository<ErrorMessage, Long> {
    List<ErrorMessage> findByUserId(Long userId);
}
