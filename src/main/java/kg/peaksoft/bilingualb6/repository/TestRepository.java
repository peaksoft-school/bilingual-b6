package kg.peaksoft.bilingualb6.repository;

import kg.peaksoft.bilingualb6.dto.response.TestResponse;
import kg.peaksoft.bilingualb6.entites.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {

}