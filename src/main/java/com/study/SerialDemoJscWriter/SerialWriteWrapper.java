package com.study.SerialDemoJscWriter;

import com.fazecast.jSerialComm.SerialPort;

public class SerialWriteWrapper {
    
    public SerialPort openComPort(String portName, int baudRate, int dataBits, int stopBits, int parity) {
        SerialPort comPort = null;
        
        if (portName == null || "".equals(portName)) {
            SerialPort [] serialPorts = SerialPort.getCommPorts();
            if (serialPorts != null && serialPorts.length > 0) {
                portName = serialPorts[0].getSystemPortName();
            }
        }
        
        comPort = SerialPort.getCommPort(portName);
        comPort.openPort();
        comPort.setComPortParameters(baudRate, dataBits, stopBits, parity);
        comPort.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);
        return comPort;
    }
    
    public void closeComPort(SerialPort serialPort) {
        if (serialPort != null && serialPort.isOpen()) {
            serialPort.closePort();
        }
    }
}
