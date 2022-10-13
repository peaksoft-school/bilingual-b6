package kg.peaksoft.bilingualb6.repository;

import kg.peaksoft.bilingualb6.entites.AuthInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthInfoRepository extends JpaRepository<AuthInfo, Long> {

    Optional<AuthInfo> findByEmail(String email);

    boolean existsAuthInfoByEmail(String email);
}
