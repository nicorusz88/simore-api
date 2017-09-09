package ar.com.simore.simoreapi.entities;

import ar.com.simore.simoreapi.entities.enums.WearableTypeEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


/**
 * Class to store OAUTH2 information from wearables API
 */

@Entity
@Table(name = "oauth")
public class OAuth extends BaseEntity{

    /**
     * If the token is for any of the wearables, ex: Fitbit
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private WearableTypeEnum wearableType;

    @NotNull
    private String user_id;

    @NotNull
    @Column( length = 500 )
    private String access_token;

    @NotNull
    private int expires_in;

    public WearableTypeEnum getWearableType() {
        return wearableType;
    }

    public void setWearableType(WearableTypeEnum wearableType) {
        this.wearableType = wearableType;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }
}
