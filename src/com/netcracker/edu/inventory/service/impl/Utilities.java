package com.netcracker.edu.inventory.service.impl;


import com.netcracker.edu.inventory.model.Device;

import java.util.Arrays;
import java.util.Comparator;

class Utilities {

    public void sortByIN(Device[] devices) {
        Arrays.sort(devices, new Comparator<Device>() {
            @Override
            public int compare(Device deviceFirst, Device deviceSecond) {
                if (deviceFirst == null && deviceSecond == null) {
                    return 0;
                }
                if (deviceFirst == null) {
                    return 1;
                }
                if (deviceSecond == null) {
                    return -1;
                }
                if (deviceFirst.getIn() == 0 && deviceSecond.getIn() != 0) {
                    return 1;
                }
                if (deviceSecond.getIn() == 0) {
                    return -1;
                }
                return deviceFirst.getIn() - deviceSecond.getIn();
            }
        });
    }

    public void filtrateByType(Device[] devices, String type) {
        if (devices == null) {
            return;
        }
        if (type == null) {
            for (int i = 0; i < devices.length; i++) {
                if (devices[i] != null && (devices[i].getType() != null)) {
                    devices[i] = null;
                }
            }
        } else {
            for (int i = 0; i < devices.length; i++) {
                if (devices[i] != null && !type.equals(devices[i].getType())) {
                    devices[i] = null;
                }
            }
        }
    }
}
