package com.barracuda.jcampaign;

public final class ErrorMessages {
    public static final String INVALID_EXPIRY_DATE_ERROR_MESSAGE = "Expiry date cannot be before now";
    public static final String INVALID_ADD_POINTS_VALUE_ERROR_MESSAGE = "Only positive number of points can be added to the loyalty card";
    public static final String INVALID_SUBTRACTING_NEGATIVE_POINTS_ERROR_MESSAGE = "Only positive number of points can be subtracted from the loyalty card";
    public static final String INVALID_ADD_OPERATION_ERROR_MESSAGE = "Loyalty card is locked. No points can be added to the loyalty card";
    public static final String INVALID_SUBTRACT_OPERATION_ERROR_MESSAGE = "Loyalty card is locked. No points can be subtracted from the loyalty card";
    public static final String INVALID_SUBTRACTING_MORE_THAN_POSSIBLE_POINTS_ERROR_MESSAGE = "Trying to subtract %d points from a loyalty card that only has %d points";

    private ErrorMessages() {}
}
