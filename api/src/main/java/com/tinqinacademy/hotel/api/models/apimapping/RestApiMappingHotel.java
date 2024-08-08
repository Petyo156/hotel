package com.tinqinacademy.hotel.api.models.apimapping;

public class RestApiMappingHotel {
    //hotel
    public static final String GET_checkAvailability_PATH = "/hotel/rooms";
    public static final String GET_basicInfoForRoom_PATH = "/hotel/{roomId}";
    public static final String POST_bookSpecifiedRoom_PATH = "/hotel/{roomId}";
    public static final String DELETE_unbookBookedRoom_PATH = "/hotel/{bookingId}";

    //system
    public static final String POST_registerVisitor_PATH = "/system/register";
    public static final String GET_adminReportVisitor_PATH = "/system/register";
    public static final String POST_adminCreateRoom_PATH = "/system/room";
    public static final String PUT_adminUpdateInfoForRoom_PATH = "/system/room/{id}";
    public static final String PATCH_adminPartialUpdate_PATH = "/system/room/{id}";
    public static final String DELETE_deleteRoom_PATH = "/system/room/{id}";
}
