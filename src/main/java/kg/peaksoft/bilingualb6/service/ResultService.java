package kg.peaksoft.bilingualb6.service;

import kg.peaksoft.bilingualb6.dto.request.EvaluationRequest;
import kg.peaksoft.bilingualb6.dto.response.*;
import kg.peaksoft.bilingualb6.entites.*;
import kg.peaksoft.bilingualb6.entites.enums.QuestionType;
import kg.peaksoft.bilingualb6.entites.enums.Status;
import kg.peaksoft.bilingualb6.exceptions.NotFoundException;
import kg.peaksoft.bilingualb6.repository.ClientRepository;
import kg.peaksoft.bilingualb6.repository.QuestionAnswerRepository;
import kg.peaksoft.bilingualb6.repository.ResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultService {

    private final ResultRepository resultRepository;

    private final ClientRepository clientRepository;

    private final JavaMailSender javaMailSender;

    private final QuestionAnswerRepository questionAnswerRepository;

    public List<ResultResponses> getAll(String email) {
        Client client = clientRepository.getClientByAuthInfoEmail(email);
        return resultRepository.findAll(client.getId());
    }

    public String sendResult(Long id) throws MessagingException {
        Result result = resultRepository.findById(id).
                orElseThrow(()->new NotFoundException("mail not found"));
        Client client = result.getClient();
        AuthInfo authInfo = client.getAuthInfo();
        String email = authInfo.getEmail();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true,"UTF-8");
        messageHelper.setSubject("[bilingual-b6] result");
        messageHelper.setFrom("bilingualbatch6@gmail.com");
        messageHelper.setTo(email);
        messageHelper.setText("Здраствуйте, Уважаемый "+client.getFirstName()+" "+client.getLastName()+" Ваш результат готова " + result.getFinalScore()+"",true);
        javaMailSender.send(mimeMessage);
        return email;
    }

    public List<ResultResponses> deleteResult(Long id, Principal principal) {
        Result result = resultRepository.findById(id).orElseThrow();
        resultRepository.delete(result);
        return getAll(principal.getName());
    }

    public List<ViewAllResultResponse> viewResults() {
        return resultRepository.getResults();
    }

    @Transactional
    public ViewResultResponse getResultResponse(Long id){
        Result result = resultRepository.findById(id).orElseThrow();
        Float score = 0f;
        int status = 0;
        for (QuestionAnswer q : result.getQuestionAnswers()){
            score += q.getScore();
            if (q.getStatus()==Status.NOT_EVALUATED){
                status++;
            }
        }
        result.setFinalScore(score);
        if (status == 0){
            result.setStatus(Status.EVALUATED);
        }
        return new ViewResultResponse(result.getId(), result.getClient().getLastName() + " " + result.getClient().getFirstName(),
                result.getTest().getTitle(), result.getDateOfSubmission(), result.getFinalScore(), result.getStatus(), resultRepository.getResultQuestions(id));
    }

    public CheckQuestionAnswerResponse getAnswerForEvaluation(Long id) {
        QuestionAnswer questionAnswer = questionAnswerRepository.findById(id).orElseThrow();
        Question question = questionAnswerRepository.findQuestionByQuestionAnswerId(id);
        CheckQuestionAnswerResponse response = null;
        if (questionAnswer.getQuestion().getQuestionType() == QuestionType.SELECT_REAL_ENGLISH_WORDS ||
                questionAnswer.getQuestion().getQuestionType() == QuestionType.LISTEN_AND_SELECT_WORD){
            List<OptionResponse> questionOptions = new ArrayList<>();
            for (Option o : question.getOptions()){
                OptionResponse optionResponse = new OptionResponse(o.getId(), o.getOption(), o.getTitle(), o.getIsTrue());
                questionOptions.add(optionResponse);
            }
            List<OptionResponse> userOptionsAnswer = new ArrayList<>();
            for (Option o : questionAnswer.getOptions()){
                OptionResponse optionResponse = new OptionResponse(o.getId(), o.getOption(), o.getTitle());
                userOptionsAnswer.add(optionResponse);
            }
            response = new CheckQuestionAnswerResponse(questionOptions, userOptionsAnswer);
        }
        if (questionAnswer.getQuestion().getQuestionType() == QuestionType.TYPE_WHAT_YOU_HEAR){
            response = new CheckQuestionAnswerResponse(questionAnswer.getQuestion().getNumberOfReplays(),
                    questionAnswer.getQuestion().getContent().getContent(), questionAnswer.getQuestion().getCorrectAnswer(),
                    questionAnswer.getTextResponseUser(), questionAnswer.getNumberOfPlays());
        }
        if (questionAnswer.getQuestion().getQuestionType() == QuestionType.RECORD_SAYING_STATEMENT){
            response = new CheckQuestionAnswerResponse(questionAnswer.getQuestion().getContent().getContent(), questionAnswer.getQuestion().getCorrectAnswer(), questionAnswer.getTextResponseUser());
        }
        if (questionAnswer.getQuestion().getQuestionType() == QuestionType.DESCRIBE_IMAGE){
            response = new CheckQuestionAnswerResponse(questionAnswer.getScore(), questionAnswer.getQuestion().getContent().getContent(), questionAnswer.getQuestion().getCorrectAnswer(), questionAnswer.getTextResponseUser());
        }
        if (questionAnswer.getQuestion().getQuestionType() == QuestionType.RESPOND_IN_AT_LEAST_N_WORDS){
            response = new CheckQuestionAnswerResponse(questionAnswer.getQuestion().getStatement(), questionAnswer.getQuestion().getMinNumberOfWords(), questionAnswer.getTextResponseUser(), questionAnswer.getNumberOfWords());
        }
        if (questionAnswer.getQuestion().getQuestionType() == QuestionType.HIGHLIGHT_THE_ANSWER){
            response = new CheckQuestionAnswerResponse(questionAnswer.getQuestion().getCorrectAnswer(), questionAnswer.getQuestion().getStatement(), questionAnswer.getQuestion().getPassage(), questionAnswer.getTextResponseUser());
        }
        if (questionAnswer.getQuestion().getQuestionType() == QuestionType.SELECT_MAIN_IDEA ||
                questionAnswer.getQuestion().getQuestionType() == QuestionType.SELECT_BEST_TITLE){
            List<OptionResponse> questionOptions = new ArrayList<>();
            for (Option o : questionAnswer.getQuestion().getOptions()){
                OptionResponse optionResponse = new OptionResponse(o.getId(), o.getOption(), o.getTitle(), o.getIsTrue());
                questionOptions.add(optionResponse);
            }
            List<OptionResponse> userOptionsAnswer = new ArrayList<>();
            for (Option o : questionAnswer.getOptions()){
                OptionResponse optionResponse = new OptionResponse(o.getId(), o.getOption(), o.getTitle());
                userOptionsAnswer.add(optionResponse);
            }
            response = new CheckQuestionAnswerResponse(questionOptions, questionAnswer.getQuestion().getPassage(), userOptionsAnswer);
        }
        assert response != null;
        response.setId(questionAnswer.getId());
        response.setFullName(questionAnswer.getResult().getClient().getLastName() + " " + questionAnswer.getResult().getClient().getFirstName());
        response.setTestTitle(questionAnswer.getResult().getTest().getTitle());
        response.setQuestionTitle(questionAnswer.getQuestion().getTitle());
        response.setDuration(questionAnswer.getQuestion().getDuration());
        response.setQuestionType(questionAnswer.getQuestion().getQuestionType());
        if (questionAnswer.getQuestion().getQuestionType() != QuestionType.DESCRIBE_IMAGE) {
            response.setScoreOfQuestion(questionAnswer.getScore());
        }
        return response;
    }

    @Transactional
    public ViewResultResponse giveScoreForQuestion(EvaluationRequest request) {
        QuestionAnswer questionAnswer = questionAnswerRepository.findById(request.getQuestionAnswerId()).orElseThrow();
        if (questionAnswer.getQuestion().getQuestionType() == QuestionType.SELECT_REAL_ENGLISH_WORDS ||
                questionAnswer.getQuestion().getQuestionType() == QuestionType.LISTEN_AND_SELECT_WORD ||
                questionAnswer.getQuestion().getQuestionType() == QuestionType.SELECT_BEST_TITLE||
                questionAnswer.getQuestion().getQuestionType() == QuestionType.SELECT_MAIN_IDEA){
            questionAnswer.setScore(questionAnswer.getScore());
        }else {
            questionAnswer.setScore(request.getScore());
        }
        questionAnswer.setStatus(Status.EVALUATED);
        questionAnswer.setSeen(true);
        return getResultResponse(questionAnswer.getResult().getId());
    }

    public List<ViewAllResultResponse> delete(Long id){
        resultRepository.deleteById(id);
        return viewResults();
    }
}