package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.*;
import ar.com.simore.simoreapi.entities.enums.NotificationTypeEnum;
import ar.com.simore.simoreapi.repositories.CheckInRepository;
import ar.com.simore.simoreapi.repositories.CheckInResultRepository;
import ar.com.simore.simoreapi.repositories.ChoiceQuestionOptionRepository;
import ar.com.simore.simoreapi.repositories.UserRepository;
import ar.com.simore.simoreapi.services.utils.DateUtils;
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

    @Autowired
    ChoiceQuestionOptionRepository choiceQuestionOptionRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    protected CheckInRepository getRepository() {
        return checkInRepository;
    }

    /**
     * Gets the Check ins by user that were not answered
     *
     * @param userId
     * @return
     */
    public List<CheckIn> getByUserId(final Long userId) {
        List<CheckIn> checkins = new ArrayList<>();
        final List<Notification> checkInNotifications = notificationService.getByUserIdAndType(userId, NotificationTypeEnum.CHECKIN);
        checkInNotifications.forEach(not -> {
            final CheckIn checkIn = checkInRepository.findOne(not.getReferenceId());
            final List<CheckInResult> checkInResults = checkInResultRepository.findByCheckIn_Id(checkIn.getId());
            if (checkInResults.isEmpty()) {
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
    public CheckInResult answerQuestion(Long checkInId, String answer) {
        final CheckIn checkin = checkInRepository.findOne(checkInId);
        final Question question = checkin.getQuestion();
        CheckInResult checkInResult = new CheckInResult();
        checkInResult.setCheckIn(checkin);
        checkInResult.setAnsweredDate(DateUtils.getCurrentDateFirstHour());
        if (question instanceof ChoiceQuestion) {
            final ChoiceQuestionOption choiceQuestionOption = choiceQuestionOptionRepository.findOne(Long.parseLong(answer));
            ChoiceAnswer choiceAnswer = new ChoiceAnswer();
            choiceAnswer.setChoiceQuestionOption(choiceQuestionOption);
            checkInResult.setAnswer(choiceAnswer);
        } else {
            OpenAnswer openAnswer = new OpenAnswer();
            openAnswer.setAnswer(answer);
            checkInResult.setAnswer(openAnswer);
        }
        checkInResultRepository.save(checkInResult);
        return checkInResult;
    }

    public List<CheckInResult> getAnsweredByUserForCurrentDay(final Long userId) {
        final List<CheckInResult> checkInResults = new ArrayList<>();
        final User user = userRepository.findOne(userId);
        if (user != null) {
            final List<CheckIn> checkIns = user.getTreatment().getCheckIns();
            checkIns.forEach(c ->
            {
                final CheckInResult checkInResult = checkInResultRepository.findByCheckIn_IdAndAnsweredDate(c.getId(), DateUtils.getCurrentDateFirstHour());
                checkInResults.add(checkInResult);
            });
        }
        return checkInResults;
    }
}
