package kg.peaksoft.bilingualb6.repository;

import kg.peaksoft.bilingualb6.dto.response.*;
import kg.peaksoft.bilingualb6.entites.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {

    @Query("select new kg.peaksoft.bilingualb6.dto.response.ResultResponse(" +
            "r. id," +
            "r.dateOfSubmission," +
            "r.status," +
            "r.finalScore," +
            "r.test.title," +
            "r.test.id" +
            ") from Result r where r.client.id =:id")
    List<ResultResponse> findAll(Long id);

    @Query("select new kg.peaksoft.bilingualb6.dto.response.ViewAllResultResponse(" +
            "r. id," +
            "concat(r.client.firstName,' ',r.client.lastName)," +
            "r.dateOfSubmission," +
            "r.status," +
            "r.finalScore," +
            "r.test.title" +
            ") from Result r")
    List<ViewAllResultResponse> viewAllResults();

    @Query("select new kg.peaksoft.bilingualb6.dto.response.ViewResultResponse(" +
            "r. id," +
            "concat(r.client.firstName,' ',r.client.lastName)," +
            "r.dateOfSubmission," +
            "r.status," +
            "r.finalScore," +
            "r.test.title"+
            ") from Result r where r.id = :id")
    ViewResultResponse getQuestionAnswerById(Long id);

    @Query("select new kg.peaksoft.bilingualb6.dto.response.QuestionAnswersResponse(" +
            "qa.id," +
            "qa.question.title," +
            "qa.score," +
            "qa.status," +
            "qa.seen) from QuestionAnswer qa where qa.result.id = :id")
    List<QuestionAnswersResponse> getAllQuestionAnswersByResultId(Long id);

    @Query("select new kg.peaksoft.bilingualb6.dto.response.QuestionAnswerResponse(" +
            "qa.id," +
            "qa.question.title," +
            "qa.question.duration," +
            "qa.question.questionType," +
            "qa.score) from QuestionAnswer qa where qa.id = :id")
    QuestionAnswerResponse getQuestionAnswer(Long id);
}