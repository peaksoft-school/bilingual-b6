package kg.peaksoft.bilingualb6.repository;

import kg.peaksoft.bilingualb6.entites.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Override
    Page<Question> findAll(Pageable pageable);
    @Query("select q from Question q where q.test.id = ?1")
    Page<Question> getTestById(Long id,Pageable pageable);
}