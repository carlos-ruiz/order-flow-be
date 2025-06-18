package com.tulipan.ordersapp.status.infrastructure.converters;

import com.tulipan.ordersapp.status.domain.model.Status;
import com.tulipan.ordersapp.status.infrastructure.entities.StatusEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StatusConverter {
    public static Status toModel(StatusEntity statusEntity) {
        if (statusEntity == null) {
            return null;
        }
        Status status = new Status();
        status.setId(statusEntity.getId());
        status.setName(statusEntity.getName());
        status.setIsActive(statusEntity.getIsActive());
        return status;
    }

    public static StatusEntity toEntity(Status status) {
        if (status == null) {
            return null;
        }
        StatusEntity statusEntity = new StatusEntity();
        statusEntity.setId(status.getId());
        statusEntity.setName(status.getName());
        statusEntity.setIsActive(status.getIsActive());
        return statusEntity;
    }
}
