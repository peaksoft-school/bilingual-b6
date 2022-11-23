package kg.peaksoft.bilingualb6.repository;

import kg.peaksoft.bilingualb6.dto.response.QuestionResponse;
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
            "q.questionType," +
            "q.statement) from Question q where q.test.id = ?1 and q.isActive=true")
    List<QuestionResponse> getQuestionByTestId(Long id);

    @Query("select q from Question q where q.test.id = ?1 and q.isActive=true")
    List<Question> getQuestionsByTestId(Long id);
}


