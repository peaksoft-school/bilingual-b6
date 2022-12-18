package kg.peaksoft.bilingualb6.repository;

import kg.peaksoft.bilingualb6.dto.response.QuestionAnswerResponse;
import kg.peaksoft.bilingualb6.dto.response.ResultResponses;
import kg.peaksoft.bilingualb6.dto.response.ViewAllResultResponse;
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
            "r.test.title," +
            "r.test.id" +
            ") from Result r where r.client.id =:id")
    List<ResultResponses> findAll(Long id);

    @Query("select new kg.peaksoft.bilingualb6.dto.response.ViewAllResultResponse(" +
            "r.id," +
            "concat(r.client.lastName, ' ', r.client.firstName), " +
            "r.dateOfSubmission," +
            "r.test.title," +
            "r.status," +
            "r.finalScore" +
            ") from Result r order by r.status desc, r.dateOfSubmission ")
    List<ViewAllResultResponse> getResults();

    @Query("select new kg.peaksoft.bilingualb6.dto.response.QuestionAnswerResponse(" +
            "q.id," +
            "q.question.title," +
            "q.score," +
            "q.status," +
            "q.seen" +
            ") from QuestionAnswer q where q.result.id = ?1 order by q.status desc, q.score")
    List<QuestionAnswerResponse> getResultQuestions(Long id);

    @Query("select r from Result r where r.test.id = ?1")
    List<Result> getResultsByTestId(Long id);
}