package ar.com.simore.simoreapi.services;

import ar.com.simore.simoreapi.entities.CheckInResult;
import ar.com.simore.simoreapi.repositories.CheckInResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckInResultService extends BaseService<CheckInResultRepository, CheckInResult> {

    @Autowired
    private CheckInResultRepository checkInResultRepository;

    @Override
    protected CheckInResultRepository getRepository() {
        return checkInResultRepository;
    }


}
