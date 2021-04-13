package com.study.SerialDemoJscWriter;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

/**
 * Hello world!
 *
 */
public class App implements Runnable {
    
    DataInputStream in;
    private boolean checkLoop = false;
    
    private int count = 0;
    
    final SerialWriteWrapper serialWriteWrapper = new SerialWriteWrapper();
    final static SerialPort serialPort = SerialWriteWrapper.openComPort("COM2", 115200, 8, 1, 0);
    
    public void run() {
        BufferedReader into = new BufferedReader(new InputStreamReader(System.in));
        String read;
        System.out.println("Welcome...");
        
        serialWriteWrapper.setSerialPortListener(serialPort, new SerialPortDataListener() {

            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }

            public void serialEvent(SerialPortEvent event) {
                if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
                    return;
                
                byte[] bytes = serialWriteWrapper.readData(serialPort);
                System.out.println("收到的数据："+ new String(bytes));
                count++;
                System.out.println("count: " + count);
            }
        });
        
        while(!checkLoop) {
            try {
                System.out.println("running1");
                read = into.readLine();
                System.out.println(read);
                if(read.equals(".bye"))
                {
                    checkLoop = true;
                }
                Thread.sleep(500);
            } catch (IOException e) {
                System.out.println(e);System.out.println("running2");
            } catch (InterruptedException ie) {
                System.out.println(ie);System.out.println("running3");
            }
        }
        System.out.println("running4");
        serialWriteWrapper.closeComPort(serialPort);
    }
    
    public static void main( String[] args ) throws InterruptedException
    {
        //SerialWriteWrapper serialWriteWrapper = new SerialWriteWrapper();
        //SerialPort serialPort = serialWriteWrapper.openComPort("COM2", 115200, 8, 1, 0);
        //String data = "$GNGLL,4917.236380,N,12043.710968,E,074921.000,A,A*4C\r\n";
        //serialPort.writeBytes(data.getBytes(), data.length());
        
        App main = new App();
        Thread t1 = new Thread(main);
        t1.start();
        
        File file = new File("D:\\Workspaces\\esri\\04_project\\16_大兴安岭林管局\\BD_data\\新建文件夹\\Com4接收日志解析前(20210323154842).txt");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
                //System.out.println("line " + line + ": " + tempString);
                String temp = tempString + "\r\n";
                serialPort.writeBytes(temp.getBytes(), temp.length());
                line++;
                Thread.sleep(100);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        
        serialPort.closePort();
    }
}
