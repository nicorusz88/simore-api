package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.Treatment;
import ar.com.simore.simoreapi.entities.Vital;
import ar.com.simore.simoreapi.entities.enums.VitalsEnum;
import ar.com.simore.simoreapi.entities.enums.WearableTypeEnum;
import ar.com.simore.simoreapi.repositories.VitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class VitalService extends BaseService<VitalRepository, Vital> {

    @Autowired
    private VitalRepository vitalRepository;

    @Autowired
    private TreatmentService treatmentService;

    @Override
    protected VitalRepository getRepository() {
        return vitalRepository;
    }


    public ResponseEntity<List<Vital>> getTypesAvailableForTreatment(Long treatmentId) {
        final Treatment treatment = treatmentService.getOne(treatmentId);
        final List<Vital> vitalsFromTreatment = treatment.getVitals();

        final CopyOnWriteArrayList<Vital> allVitals = new CopyOnWriteArrayList<>(getAllVitals());
        for (Vital vital : allVitals) {
            for (Vital vitalFromTreatment : vitalsFromTreatment) {
                if (vitalFromTreatment.getType() == vital.getType()) {
                    allVitals.remove(vital);
                }
            }
        }
        return ResponseEntity.ok(allVitals);
    }

    /**
     * Gets all Vitals Combinations
     *
     * @return
     */
    private List<Vital> getAllVitals() {
        final List<Vital> allVitals = new ArrayList<>();
        Vital vitalHeartRateFitbit = new Vital();
        vitalHeartRateFitbit.setName(VitalsEnum.HEART_RATE.getName());
        vitalHeartRateFitbit.setType(VitalsEnum.HEART_RATE);
        vitalHeartRateFitbit.setWearableType(WearableTypeEnum.FITBIT);
        allVitals.add(vitalHeartRateFitbit);

        Vital vitalHeartRateWithings = new Vital();
        vitalHeartRateWithings.setName(VitalsEnum.HEART_RATE.getName());
        vitalHeartRateWithings.setType(VitalsEnum.HEART_RATE);
        vitalHeartRateWithings.setWearableType(WearableTypeEnum.WITHINGS);
        allVitals.add(vitalHeartRateWithings);

        Vital vitalBloodPressureFitbit = new Vital();
        vitalBloodPressureFitbit.setName(VitalsEnum.BLOOD_PRESSURE.getName());
        vitalBloodPressureFitbit.setType(VitalsEnum.BLOOD_PRESSURE);
        vitalBloodPressureFitbit.setWearableType(WearableTypeEnum.FITBIT);
        allVitals.add(vitalBloodPressureFitbit);

        Vital vitalBloodPressureWithings = new Vital();
        vitalBloodPressureWithings.setName(VitalsEnum.BLOOD_PRESSURE.getName());
        vitalBloodPressureWithings.setType(VitalsEnum.BLOOD_PRESSURE);
        vitalBloodPressureWithings.setWearableType(WearableTypeEnum.WITHINGS);
        allVitals.add(vitalBloodPressureWithings);

        Vital vitalBurntCaloriesFitbit = new Vital();
        vitalBurntCaloriesFitbit.setName(VitalsEnum.BURNT_CALORIES.getName());
        vitalBurntCaloriesFitbit.setType(VitalsEnum.BURNT_CALORIES);
        vitalBurntCaloriesFitbit.setWearableType(WearableTypeEnum.FITBIT);
        allVitals.add(vitalBurntCaloriesFitbit);

        Vital vitalBurntCaloriesWithings = new Vital();
        vitalBurntCaloriesWithings.setName(VitalsEnum.BURNT_CALORIES.getName());
        vitalBurntCaloriesWithings.setType(VitalsEnum.BURNT_CALORIES);
        vitalBurntCaloriesWithings.setWearableType(WearableTypeEnum.WITHINGS);
        allVitals.add(vitalBurntCaloriesWithings);

        Vital vitalDistanceFitbit = new Vital();
        vitalDistanceFitbit.setName(VitalsEnum.DISTANCE.getName());
        vitalDistanceFitbit.setType(VitalsEnum.DISTANCE);
        vitalDistanceFitbit.setWearableType(WearableTypeEnum.FITBIT);
        allVitals.add(vitalDistanceFitbit);

        Vital vitalDistanceWithings = new Vital();
        vitalDistanceWithings.setName(VitalsEnum.DISTANCE.getName());
        vitalDistanceWithings.setType(VitalsEnum.DISTANCE);
        vitalDistanceWithings.setWearableType(WearableTypeEnum.WITHINGS);
        allVitals.add(vitalDistanceWithings);

        Vital vitalBloodOxygenFitbit = new Vital();
        vitalBloodOxygenFitbit.setName(VitalsEnum.BLOOD_OXYGEN.getName());
        vitalBloodOxygenFitbit.setType(VitalsEnum.BLOOD_OXYGEN);
        vitalBloodOxygenFitbit.setWearableType(WearableTypeEnum.FITBIT);
        allVitals.add(vitalBloodOxygenFitbit);

        Vital vitalBloodOxygenWithings = new Vital();
        vitalBloodOxygenWithings.setName(VitalsEnum.BLOOD_OXYGEN.getName());
        vitalBloodOxygenWithings.setType(VitalsEnum.BLOOD_OXYGEN);
        vitalBloodOxygenWithings.setWearableType(WearableTypeEnum.WITHINGS);
        allVitals.add(vitalBloodOxygenWithings);

        Vital vitalStepsFitbit = new Vital();
        vitalStepsFitbit.setName(VitalsEnum.STEPS.getName());
        vitalStepsFitbit.setType(VitalsEnum.STEPS);
        vitalStepsFitbit.setWearableType(WearableTypeEnum.FITBIT);
        allVitals.add(vitalStepsFitbit);

        Vital vitalStepsWithings = new Vital();
        vitalStepsWithings.setName(VitalsEnum.STEPS.getName());
        vitalStepsWithings.setType(VitalsEnum.STEPS);
        vitalStepsWithings.setWearableType(WearableTypeEnum.WITHINGS);
        allVitals.add(vitalStepsWithings);

        Vital vitalSleepTrackingFitbit = new Vital();
        vitalSleepTrackingFitbit.setName(VitalsEnum.SLEEP_TRACKING.getName());
        vitalSleepTrackingFitbit.setType(VitalsEnum.SLEEP_TRACKING);
        vitalSleepTrackingFitbit.setWearableType(WearableTypeEnum.FITBIT);
        allVitals.add(vitalSleepTrackingFitbit);

        Vital vitalSleepTrackingWithings = new Vital();
        vitalSleepTrackingWithings.setName(VitalsEnum.SLEEP_TRACKING.getName());
        vitalSleepTrackingWithings.setType(VitalsEnum.SLEEP_TRACKING);
        vitalSleepTrackingWithings.setWearableType(WearableTypeEnum.WITHINGS);
        allVitals.add(vitalSleepTrackingWithings);

        Vital vitalWeightFitbit = new Vital();
        vitalWeightFitbit.setName(VitalsEnum.WEIGHT.getName());
        vitalWeightFitbit.setType(VitalsEnum.WEIGHT);
        vitalWeightFitbit.setWearableType(WearableTypeEnum.FITBIT);
        allVitals.add(vitalWeightFitbit);

        Vital vitalWeightWithings = new Vital();
        vitalWeightWithings.setName(VitalsEnum.WEIGHT.getName());
        vitalWeightWithings.setType(VitalsEnum.WEIGHT);
        vitalWeightWithings.setWearableType(WearableTypeEnum.WITHINGS);
        allVitals.add(vitalWeightWithings);
        return allVitals;
    }
}
