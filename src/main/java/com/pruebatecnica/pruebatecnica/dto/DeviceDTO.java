package com.pruebatecnica.pruebatecnica.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDTO implements Serializable {
    
    private Long id;
    private String mac;
    private String type;
    private Boolean connected;
    private String ip;
    private String trademark;
    private ConnectionDTO connection;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((connected == null) ? 0 : connected.hashCode());
        result = prime * result + ((connection == null) ? 0 : connection.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((ip == null) ? 0 : ip.hashCode());
        result = prime * result + ((mac == null) ? 0 : mac.hashCode());
        result = prime * result + ((trademark == null) ? 0 : trademark.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DeviceDTO other = (DeviceDTO) obj;
        if (connected == null) {
            if (other.connected != null)
                return false;
        } else if (!connected.equals(other.connected))
            return false;
        if (connection.getId() == null) {
            if (other.connection != null)
                return false;
        } else if (!connection.getId().equals(other.connection.getId()))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (ip == null) {
            if (other.ip != null)
                return false;
        } else if (!ip.equals(other.ip))
            return false;
        if (mac == null) {
            if (other.mac != null)
                return false;
        } else if (!mac.equals(other.mac))
            return false;
        if (trademark == null) {
            if (other.trademark != null)
                return false;
        } else if (!trademark.equals(other.trademark))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }

}
