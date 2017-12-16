package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.Notification;
import ar.com.simore.simoreapi.entities.Recommendation;
import ar.com.simore.simoreapi.entities.enums.NotificationTypeEnum;
import ar.com.simore.simoreapi.entities.resources.RecommendationResource;
import ar.com.simore.simoreapi.repositories.RecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendationService extends BaseService<RecommendationRepository, Recommendation> {

    @Autowired
    private RecommendationRepository recommendationRepository;

    @Autowired
    private NotificationService notificationService;

    @Override
    protected RecommendationRepository getRepository() {
        return recommendationRepository;
    }

    public List<RecommendationResource> getByUserId(Long userId) {
        List<RecommendationResource> recommendationResources = new ArrayList<>();
        final List<Notification> followingAppointmentsNotifications = notificationService.getByUserIdAndType(userId, NotificationTypeEnum.RECOMMENDATION);
        followingAppointmentsNotifications.forEach(not -> {
            final Recommendation rec = recommendationRepository.findOne(not.getReferenceId());
            if (rec != null) {
                RecommendationResource recommendation = new RecommendationResource(rec.getName(), rec.getText(), not.getActualSendDate());
                recommendationResources.add(recommendation);
            }
        });
        return recommendationResources;
    }
}
