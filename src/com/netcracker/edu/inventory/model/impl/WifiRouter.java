package com.netcracker.edu.inventory.model.impl;

import com.netcracker.edu.inventory.model.Device;

public class WifiRouter extends AbstractDevice implements Device {

    protected String securityProtocol;

    public String getSecurityProtocol() {
        return securityProtocol;
    }

    public void setSecurityProtocol(String securityProtocol) {
        this.securityProtocol = securityProtocol;
    }
}
