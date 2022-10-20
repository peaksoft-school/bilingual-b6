package kg.peaksoft.bilingualb6.repository;

import kg.peaksoft.bilingualb6.entites.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
