package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.*;
import ar.com.simore.simoreapi.entities.enums.NotificationTypeEnum;
import ar.com.simore.simoreapi.repositories.CheckInRepository;
import ar.com.simore.simoreapi.repositories.CheckInResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CheckInService extends BaseService<CheckInRepository, CheckIn> {

    @Autowired
    private CheckInRepository checkInRepository;

    @Autowired
    private CheckInResultRepository checkInResultRepository;

    @Autowired
    private NotificationService notificationService;

    @Override
    protected CheckInRepository getRepository() {
        return checkInRepository;
    }

    public List<CheckIn> getByUserId(final Long userId) {
        List<CheckIn> checkins = new ArrayList<>();
        final List<Notification> checkInNotifications = notificationService.getByUserIdAndType(userId, NotificationTypeEnum.CHECKIN);
        checkInNotifications.forEach(not -> {
            final CheckIn checkIn = checkInRepository.findOne(not.getReferenceId());
            if (checkIn != null) {
                checkins.add(checkIn);
            }
        });
        return checkins;
    }

    /**
     * We set the answer to the question. If the question is choice
     * then the answer is the choice ID, otherwise it is the plain text answer
     *
     * @param checkInId
     * @param answer
     * @return
     */
    public void answerQuestion(Long checkInId, String answer) {
        final CheckIn checkin = checkInRepository.findOne(checkInId);
        final Question question = checkin.getQuestion();
        CheckInResult checkInResult = new CheckInResult();
        checkInResult.setCheckIn(checkin);
        if (question instanceof ChoiceQuestion) {
            ChoiceQuestionOption choiceQuestionOption = new ChoiceQuestionOption();
            choiceQuestionOption.setId(Long.parseLong(answer));
            ChoiceAnswer choiceAnswer = new ChoiceAnswer();
            choiceAnswer.setChoiceQuestionOption(choiceQuestionOption);
            checkInResult.setAnswer(choiceAnswer);
        } else {
            OpenAnswer openAnswer = new OpenAnswer();
            openAnswer.setAnswer(answer);
            checkInResult.setAnswer(openAnswer);
        }
        checkInResultRepository.save(checkInResult);
    }
}
