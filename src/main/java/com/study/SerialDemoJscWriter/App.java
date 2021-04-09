package com.study.SerialDemoJscWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.fazecast.jSerialComm.SerialPort;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InterruptedException
    {
        SerialWriteWrapper serialWriteWrapper = new SerialWriteWrapper();
        SerialPort serialPort = serialWriteWrapper.openComPort("COM2", 115200, 8, 1, 0);
        //String data = "$GNGLL,4917.236380,N,12043.710968,E,074921.000,A,A*4C\r\n";
        //serialPort.writeBytes(data.getBytes(), data.length());
        
        File file = new File("\\Com4接收日志解析前(20210323154842).txt");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
                System.out.println("line " + line + ": " + tempString);
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
