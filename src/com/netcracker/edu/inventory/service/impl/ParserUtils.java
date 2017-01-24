package com.netcracker.edu.inventory.service.impl;

import com.netcracker.edu.inventory.model.Connection;
import com.netcracker.edu.inventory.model.ConnectorType;
import com.netcracker.edu.inventory.model.Device;
import com.netcracker.edu.inventory.model.FeelableEntity;
import com.netcracker.edu.inventory.model.impl.OpticFiber;
import com.netcracker.edu.inventory.model.impl.TwistedPair;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Array;
import java.util.*;

class ParserUtils {
    String lineRead(Reader reader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        char ch = (char) reader.read();
        while ((ch != '\n') && (ch != '\r')) {
            stringBuilder.append(ch);
            ch = (char) reader.read();
        }
        reader.read();
        return stringBuilder.toString();
    }

    String removeSpace(String str) {
        return (str.equals(" ")) ? null : str.substring(1, str.length() - 1);
    }

    void setValuesToList(StringTokenizer stringTokenizer, List<FeelableEntity.Field> fieldList) {
        for (FeelableEntity.Field field : fieldList) {
            Class type = field.getType();
            if (type == int.class) {
                if (fieldList.indexOf(field) == 0) {
                    field.setValue(Integer.parseInt(stringTokenizer.nextToken()));
                } else {
                    field.setValue(Integer.parseInt(removeSpace(stringTokenizer.nextToken())));
                }
            }
            if (type == String.class) {
                if (fieldList.indexOf(field) == 0) {
                    String status = stringTokenizer.nextToken();
                    status = status.substring(0, status.length() - 1);
                    field.setValue(status);
                } else {
                    field.setValue(removeSpace(stringTokenizer.nextToken()));
                }
            }
            if (type == Date.class) {
                long productionDate = Long.parseLong(removeSpace(stringTokenizer.nextToken()));
                Date date = null;
                if (productionDate != -1) {
                    date = new Date(productionDate);
                }
                field.setValue(date);
            }
            if (type == Device.class) {
                Device device = removeSpace(stringTokenizer.nextToken()) == null ? null : null;
                field.setValue(device);
            }
            if (type == ConnectorType.class) {
                String connectorType = removeSpace(stringTokenizer.nextToken());
                field.setValue(ConnectorType.valueOf(connectorType));
            }
            if (type == OpticFiber.Mode.class) {
                String mode = removeSpace(stringTokenizer.nextToken());
                field.setValue(mode == null ? OpticFiber.Mode.need_init : OpticFiber.Mode.valueOf(mode));
            }
            if (type == TwistedPair.Type.class) {
                String numType = removeSpace(stringTokenizer.nextToken());
                field.setValue(numType == null ? TwistedPair.Type.need_init : TwistedPair.Type.valueOf(numType));
            }
            if (type == Set.class) {
                Set<Device> deviceSet = new HashSet<Device>();
                int sizeOfSet = Integer.parseInt(removeSpace(stringTokenizer.nextToken()));
                for (int i = 0; i < sizeOfSet; i++) {
                    stringTokenizer.nextToken();
                    deviceSet.add(null);
                }
                field.setValue(deviceSet);
            } else if (type == Set.class) {
                List<Connection> connectionList = new ArrayList<Connection>();
                int sizeOfList = Integer.parseInt(removeSpace(stringTokenizer.nextToken()));
                for (int i = 0; i < sizeOfList; i++) {
                    stringTokenizer.nextToken();
                    connectionList.add(null);
                }
                field.setValue(connectionList);
            } else if (type == Array.class) {
                int lenght = Integer.parseInt(removeSpace(stringTokenizer.nextToken()));
                Device[] deviceArray = new Device[lenght];
                for (int i = 0; i < deviceArray.length; i++) {
                    stringTokenizer.nextToken();
                    deviceArray[i] = null;
                }
                field.setValue(deviceArray);
            }
        }
    }

    void choiceOfMethod(StringTokenizer stringTokenizer, DataInputStream dataInputStream, List<FeelableEntity.Field> fieldList) throws IOException {
        if (stringTokenizer != null) {
            setValuesToList(stringTokenizer, fieldList);
        } else {
            setValuesToList(dataInputStream, fieldList);
        }
    }

    private void setValuesToList(DataInputStream dataInputStream, List<FeelableEntity.Field> fieldList) throws IOException {
        for (FeelableEntity.Field field : fieldList) {
            Class type = field.getType();
            if (type == int.class) {
                field.setValue(dataInputStream.readInt());
            }
            if (type == String.class) {
                String str = dataInputStream.readUTF();
                field.setValue(str.equals("\n") ? null : str);
            }
            if (type == Date.class) {
                long productionDate = dataInputStream.readLong();
                Date date = null;
                if (productionDate != -1) {
                    date = new Date(productionDate);
                }
                field.setValue(date);
            }
            if (type == Device.class) {
                Device device = dataInputStream.readUTF().equals("\n") ? null : null;
                field.setValue(device);
            }
            if (type == ConnectorType.class) {
                String connectorType = dataInputStream.readUTF();
                field.setValue(ConnectorType.valueOf(connectorType));
            }
            if (type == OpticFiber.Mode.class) {
                String mode = dataInputStream.readUTF();
                field.setValue(OpticFiber.Mode.valueOf(mode));
            }
            if (type == TwistedPair.Type.class) {
                String numType = dataInputStream.readUTF();
                field.setValue(TwistedPair.Type.valueOf(numType));
            }
            if (type == Set.class) {
                Set<Device> deviceSet = new HashSet<Device>();
                int sizeOfSet = dataInputStream.readInt();
                for (int i = 0; i < sizeOfSet; i++) {
                    dataInputStream.readUTF();
                    deviceSet.add(null);
                }
                field.setValue(deviceSet);
            }
            if (type == List.class) {
                List<Connection> connectionList = new ArrayList<Connection>();
                int sizeOfList = dataInputStream.readInt();
                for (int i = 0; i < sizeOfList; i++) {
                    dataInputStream.readUTF();
                    connectionList.add(null);
                }
                field.setValue(connectionList);
            }
            if (type == Array.class) {
                int arrayLenght = dataInputStream.readInt();
                Device[] deviceArray = new Device[arrayLenght];
                for (int i = 0; i < deviceArray.length; i++) {
                    dataInputStream.readUTF();
                    deviceArray[i] = null;
                }
                field.setValue(deviceArray);
            }
        }
    }

}
