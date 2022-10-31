package kg.peaksoft.bilingualb6.repository;

import kg.peaksoft.bilingualb6.dto.response.OptionResponse;
import kg.peaksoft.bilingualb6.entites.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface OptionRepository extends JpaRepository<Option, Long> {

    @Modifying
    @Transactional
    @Query("update Option " +
            "set question = null where id = ?1")
    void updateByIdForDeleteInQuestion(Long id);

    @Modifying
    @Transactional
    @Query("delete from Option " +
            "where id = ?1")
    void deleteById(Long id);

    @Query("select new kg.peaksoft.bilingualb6.dto.response.OptionResponse(" +
            "o.id," +
            "o.option," +
            "o.isTrue) from Option o where o.question.id = ?1")
    List<OptionResponse> getAllOptionsByQuestionId(Long id);
}