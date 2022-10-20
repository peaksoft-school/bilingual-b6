package kg.peaksoft.bilingualb6.repository;

import kg.peaksoft.bilingualb6.entites.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Modifying
    @Query("delete from QuestionAnswer q where q.question.id = ?1")
    void deleteQuestionAnswers(Long id);

    //    @Modifying
//    @Query("update QuestionAnswer set question = null where id = ?1")
//    void updateQuestionIdAtQuestionAnswer(Long id);
//
    @Modifying
    @Transactional
    @Query("update Question set test = null where id = ?1")
    void updateByIdForDeleteQuestionToTestId(Long id);

    @Modifying
    @Transactional
    @Query("update Question set content = null where id = ?1")
    void updateByIdForDeleteQuestionToContentId(Long id);
}


