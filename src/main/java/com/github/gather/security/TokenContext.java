package com.github.gather.security;

public class TokenContext {
    private static final ThreadLocal<Long> profileIdHolder = new ThreadLocal<>();
    public static void setProfileId(Long userId){
        profileIdHolder.set(userId);
    }

    public static Long getProfileId() {
        return profileIdHolder.get();
    }

}