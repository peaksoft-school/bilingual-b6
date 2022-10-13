package kg.peaksoft.bilingualb6.repository;

import kg.peaksoft.bilingualb6.entites.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findClientByAuthInfoEmail(String email);
}
