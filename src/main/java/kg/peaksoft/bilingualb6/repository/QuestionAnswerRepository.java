package kg.peaksoft.bilingualb6.repository;

import kg.peaksoft.bilingualb6.entites.Question;
import kg.peaksoft.bilingualb6.entites.QuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer, Long> {

    @Query("select q from Question q where q.questionAnswer.id = ?1")
    Question findQuestionByQuestionAnswerId(Long id);
}