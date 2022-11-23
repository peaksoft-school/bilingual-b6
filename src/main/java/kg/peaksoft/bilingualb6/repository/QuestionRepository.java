package kg.peaksoft.bilingualb6.repository;

import kg.peaksoft.bilingualb6.dto.response.QuestionResponse;
import kg.peaksoft.bilingualb6.dto.response.QuestionResponseForGetByTestId;
import kg.peaksoft.bilingualb6.entites.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Modifying
    @Transactional
    @Query("update Question set test = null where id = ?1")
    void updateByIdForDeleteQuestionToTestId(Long id);

    @Modifying
    @Transactional
    @Query("update Question set content = null where id = ?1")
    void updateByIdForDeleteQuestionToContentId(Long id);

    @Query("select new kg.peaksoft.bilingualb6.dto.response.QuestionResponse(" +
            "q.id," +
            "q.title," +
            "q.passage," +
            "q.isActive," +
            "q.numberOfReplays," +
            "q.duration," +
            "q.content.content," +
            "q.questionType," +
            "q.statement," +
            "q.minNumberOfWords," +
            "q.correctAnswer) from Question q where q.test.id = ?1")
    List<QuestionResponse> getQuestionByTestIdForClient(Long id);

    @Query("select new kg.peaksoft.bilingualb6.dto.response.QuestionResponseForGetByTestId(" +
            "q.id," +
            "q.title," +
            "q.duration," +
            "q.questionType," +
            "q.isActive)"+
            "from Question q where q.test.id = ?1")
    List<QuestionResponseForGetByTestId> getQuestionByTestId(Long id);
}


