package ar.com.simore.simoreapi.services;

import org.springframework.stereotype.Service;

@Service
public class FitBitService {

    private String clientID = "228LHW";
    private String clientSecret = "c368db81c2c03dc93128854c14d1f293";
    private String callbackURL = "http://localhost:8088/wearable-callback-authorization";
    private String authorizacionURI = "https://www.fitbit.com/oauth2/authorize";
    private String refreshTokenURI = "https://api.fitbit.com/oauth2/token";

}
