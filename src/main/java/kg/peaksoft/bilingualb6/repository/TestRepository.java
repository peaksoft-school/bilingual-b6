package kg.peaksoft.bilingualb6.repository;

import kg.peaksoft.bilingualb6.dto.response.TestResponse;
import kg.peaksoft.bilingualb6.entites.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TestRepository extends JpaRepository<Test, Long> {

    @Query("select new kg.peaksoft.bilingualb6.dto.response.TestResponse(" +
            "t.id, t.title, t.shortDescription, t.isActive) from Test t")
    List<TestResponse> getAllTest();

    @Query("select t from Test t where t.isActive = true")
    List<Test> findAllForClient();
}