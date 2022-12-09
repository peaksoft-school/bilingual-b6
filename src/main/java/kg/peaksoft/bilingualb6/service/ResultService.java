package kg.peaksoft.bilingualb6.service;

import kg.peaksoft.bilingualb6.dto.response.QuestionAnswerResponse;
import kg.peaksoft.bilingualb6.dto.response.ResultResponse;
import kg.peaksoft.bilingualb6.dto.response.ViewAllResultResponse;
import kg.peaksoft.bilingualb6.dto.response.ViewResultResponse;
import kg.peaksoft.bilingualb6.entites.Client;
import kg.peaksoft.bilingualb6.entites.Option;
import kg.peaksoft.bilingualb6.entites.QuestionAnswer;
import kg.peaksoft.bilingualb6.repository.ClientRepository;
import kg.peaksoft.bilingualb6.repository.OptionRepository;
import kg.peaksoft.bilingualb6.repository.QuestionAnswerRepository;
import kg.peaksoft.bilingualb6.repository.ResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultService {

    private final ResultRepository resultRepository;
    private final OptionRepository optionRepository;
    private final QuestionAnswerRepository questionAnswerRepository;
    private final ClientRepository clientRepository;

    public List<ResultResponse> getAll(String email) {
        Client client = clientRepository.getClientByAuthInfoEmail(email);
        return resultRepository.findAll(client.getId());
    }

    public List<ViewAllResultResponse> viewAllResult() {
        return resultRepository.viewAllResults();
    }

    public ViewResultResponse getQuestionAnswersByResultId(Long id){
        ViewResultResponse viewResultResponse = resultRepository.getQuestionAnswerById(id);
        viewResultResponse.setQuestionAnswers(resultRepository.getAllQuestionAnswersByResultId(id));
        return viewResultResponse;
    }

    public QuestionAnswerResponse getQuestionAnswerById(Long id) {
        QuestionAnswer questionAnswer = questionAnswerRepository.findById(id).orElseThrow();
        QuestionAnswerResponse questionAnswerResponse = resultRepository.getQuestionAnswer(id);
        questionAnswerResponse.setOptions(optionRepository.getAllOptionsByQuestionId(questionAnswer.getQuestion().getId()));
        List<String> options = new ArrayList<>();
        for (Option o: questionAnswer.getOptions()) {
            options.add(o.getOption());
        }
        questionAnswerResponse.setAnswersOfUser(options);
        return questionAnswerResponse;
    }
}