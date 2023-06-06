package bitlab.spring.sprint2.repos;

import bitlab.spring.sprint2.entity.ApplicationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<ApplicationRequest, Long> {
    List<ApplicationRequest> findAllByHandled(boolean handled);
    ApplicationRequest findApplicationRequestById(Long id);
}
