package com.eazybytes.jobportal.constants;

public class ApplicationConstants {

    private ApplicationConstants(){
        throw new AssertionError("Utility class should not be instantiated");
    }

    public static final String JWT_SECRET_KEY = "JWT_SECRET";
    public static final String JWT_SECRET_DEFAULT_VALUE = "jxgEQeXHuPq8VdbyYFNkANdudQ53YUn4";
    public static final String JWT_AUTHORIZATION = "Authorization";
    public static final String ROLE_JOB_SEEKER = "ROLE_JOB_SEEKER";
    public static final String JOB_STATUS_ACTIVE = "ACTIVE";

    public static final String  NEW_MESSAGE = "NEW";
    public static final String  CLOSED_MESSAGE = "CLOSED";

    public static final String  SYSTEM = "SYSTEM";
}
