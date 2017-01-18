package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Connection;
import com.netcracker.edu.inventory.model.ConnectorType;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.FeelableEntity;
import com.netcracker.edu.inventory.model.impl.OpticFiber;
import com.netcracker.edu.inventory.model.impl.TwistedPair;
import com.netcracker.edu.inventory.model.FeelableEntity.Field;

import java.lang.reflect.Array;
import java.util.*;

class ParserUtils {
    int parseInteger(String value) {
        return Integer.parseInt(value.replace("[", "").replace("]", "").trim());
    }

    String parseString(String value) {
        if (value.length() > 1) {
            return value.substring(1, value.length() - 1);
        }
        return null;
    }

    ConnectorType parseConnectorType(String value) {
        return ConnectorType.valueOf(parseString(value));
    }

    Date parseDate(String value) {
        long time = Long.parseLong(value.trim());
        if (time != -1) {
            return new Date(time);
        }
        return null;
    }

    Set parseSet(String value) {
        int size = Integer.parseInt(value.trim());
        return new HashSet(size);
    }

    Device parseDevice(String value) {
        return null;
    }

    Device[] parseArray(String value) {
        int size = Integer.parseInt(value.trim());
        return new Device[size];
    }

    List parseList(String value) {
        int size = Integer.parseInt(value.trim());
        return new ArrayList(size);
    }

    TwistedPair.Type parseType(String value) {
        return TwistedPair.Type.valueOf(parseString(value));
    }

    OpticFiber.Mode parseMode(String value) {
        return OpticFiber.Mode.valueOf(parseString(value));
    }

    int getPresentationOfProperty(Connection[] value) {
        return value.length;
    }

    String getPresentationOfProperty(Connection value) {
        return "[ ]";
    }

    String getPresentationOfPropertyForWriter(Field field) {
        if (field.getType() == Integer.class) {
            return " " + (field.getValue()).toString() + " ";
        }

        if (field.getType() == String.class) {
            if (field.getValue() != null) {
                return " " + field.getValue() + " ";
            }
            return " ";
        }

        if (field.getType() == Date.class) {
            if (field.getValue() != null) {
                return " " + ((Long) (((Date) field.getValue()).getTime())).toString() + " ";
            }
            return " -1 ";
        }

        if (field.getType() == Device.class) {
            return " ";
        }

        if (field.getType() == Set.class) {
            Set set = (Set) field.getValue();
            return " " + set.size() + " ";
        }

        if (field.getType() == List.class) {
            List list = (List) field.getValue();
            return " " + list.size() + " ";
        }

        if (field.getType() == TwistedPair.Type.class) {
            return " " + ((TwistedPair.Type) field.getValue()).name() + " ";
        }

        if (field.getType() == OpticFiber.Mode.class) {
            return " " + ((OpticFiber.Mode) field.getValue()).name() + " ";
        }

        if (field.getType() == Array.class) {
            Object[] array = (Object[]) field.getValue();
            return " " + array.length + " ";
        }

        if (field.getType() == ConnectorType.class) {
            return " " + ((ConnectorType) field.getValue()).name() + " ";
        }

        if (field.getType() == Connection.class) {
            return " ";
        }
        return null;
    }

    String getPresentationOfProperty(String value) {
        if (value == null) {
            return "[ ]";
        }
        return value;
    }

    Long getPresentationOfProperty(Date date) {
        if (date == null) {
            return -1L;
        }
        return date.getTime();
    }

    Integer getPresentationOfProperty(Integer value) {
        return value;
    }
}
