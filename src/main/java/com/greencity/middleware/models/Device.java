package com.greencity.middleware.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Objects;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table
public class Device {
    @Id
    @PrimaryKey
    private String macAddress;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Device)) return false;
        Device that = (Device) o;
        return this.getMacAddress().equals(that.getMacAddress());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getMacAddress());
    }
}
