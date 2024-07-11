package com.tinqinacademy.hotel.core.services;


import com.tinqinacademy.hotel.api.models.system.admin.create.room.AdminCreateRoomInput;
import com.tinqinacademy.hotel.api.models.system.admin.create.room.AdminCreateRoomOutput;
import com.tinqinacademy.hotel.api.models.system.admin.partial.update.AdminPartialUpdateInput;
import com.tinqinacademy.hotel.api.models.system.admin.partial.update.AdminPartialUpdateOutput;
import com.tinqinacademy.hotel.api.models.system.admin.report.visitor.AdminReportVisitorInput;
import com.tinqinacademy.hotel.api.models.system.admin.report.visitor.AdminReportVisitorOutput;
import com.tinqinacademy.hotel.api.models.system.admin.update.info.forroom.AdminUpdateInfoForRoomInput;
import com.tinqinacademy.hotel.api.models.system.admin.update.info.forroom.AdminUpdateInfoForRoomOutput;
import com.tinqinacademy.hotel.api.models.system.delete.room.DeleteRoomInput;
import com.tinqinacademy.hotel.api.models.system.delete.room.DeleteRoomOutput;
import com.tinqinacademy.hotel.api.models.system.register.visitor.RegisterVisitorInput;
import com.tinqinacademy.hotel.api.models.system.register.visitor.RegisterVisitorOutput;

public interface SystemService {
    RegisterVisitorOutput registerVisitor(RegisterVisitorInput input);
    AdminReportVisitorOutput adminReport(AdminReportVisitorInput input);
    AdminCreateRoomOutput adminCreateRoom(AdminCreateRoomInput input);
    AdminUpdateInfoForRoomOutput adminUpdateInfoForRoom(AdminUpdateInfoForRoomInput input);
    AdminPartialUpdateOutput adminPartialUpdate(AdminPartialUpdateInput input);
    DeleteRoomOutput deleteRoom(DeleteRoomInput input);
}
