package com.Bibo.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Profiles {

    public static String profiles;

    public String getProfiles() {
        return profiles;
    }

    @Value("${spring.cloud.config.profile}")
    public void setProfiles(String profiles) {
        Profiles.profiles = profiles;
    }
}
