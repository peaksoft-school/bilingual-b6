package kg.peaksoft.bilingualb6.repository;

import kg.peaksoft.bilingualb6.entites.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findClientByAuthInfoEmail(String email);

    @Query("select c from Client c where c.authInfo.email = ?1")
    Optional<Client> findClientByAuthInfoEmailOptional(String email);

    Client getClientByAuthInfoEmail(String email);
}