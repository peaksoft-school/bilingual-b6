package kg.peaksoft.bilingualb6.repository;

import kg.peaksoft.bilingualb6.dto.response.ResultResponses;
import kg.peaksoft.bilingualb6.entites.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {

    @Query("select new kg.peaksoft.bilingualb6.dto.response.ResultResponses(" +
            "r.id," +
            "r.dateOfSubmission," +
            "r.status," +
            "r.finalScore," +
            "r.client," +
            "r.test" +
            ") from Result r where r.client.id = ?1")
    List<ResultResponses> findAll(Long id);
}