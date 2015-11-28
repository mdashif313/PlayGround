
package com.ashif.play;

/**
 *
 * @author Ashif
 */


import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;



public class Client {

    //Constants
    public static final int PORT = 2222;
    public static String HOST = "localhost";
    public static final String CLIENT_DIRECTORY="d:/temp/client_2/";
    
    public static void main(String[] args) {
        
        while (true) {            
            System.out.println("1.Upload File");
            System.out.println("2.Download File");
            System.out.print("Give your choice : ");
            //taking input from user
            Scanner inFromUser = new Scanner(System.in);
            int choice = inFromUser.nextInt();
        
            if(choice == 1)
                FileUploader();
        }
        
        
    }
    
    
    
    public static void FileUploader(){
        
        try {
            //initializing connection with server
            SocketChannel clientChannel = SocketChannel.open();
            clientChannel.connect(new InetSocketAddress(HOST,PORT));
            
            String pingToServer = "receive";
            //converting ping string to bytearray
            byte[] pingBytes = pingToServer.getBytes("UTF-8");
            
            //sending ping to server
            ByteBuffer pingBuffer=ByteBuffer.wrap(pingBytes);
            clientChannel.write(pingBuffer);
            
            //taking input from user
            Scanner inFromUser = new Scanner(System.in);
            System.out.print("Number of files : ");
            int numofFiles=inFromUser.nextInt();
            
            String quantofFiles=numofFiles+"";
            //converting ping string to bytearray
            byte[] quantBytes = quantofFiles.getBytes("UTF-8");
            
            //sending ping to server
            ByteBuffer quantBuffer=ByteBuffer.wrap(quantBytes);
            clientChannel.write(quantBuffer);
            
            ArrayList<String> listofFiles= new ArrayList<String>();
            
            //taking the name of files from user
            inFromUser.nextLine();
            for(int i=0;i<numofFiles;i++){
                System.out.print("File name : ");
                String str=inFromUser.nextLine();
                listofFiles.add(str);
            }
            
            //sending files to server
            for(int i=0; i<numofFiles; i++){                
                String fileName = listofFiles.get(i);
                File fileToSend=new File(CLIENT_DIRECTORY+fileName);
                
                //converting filename string to bytearray
                byte[] nameBytes = fileName.getBytes("UTF-8");
            
                //sending filename to server
                ByteBuffer nameBuffer=ByteBuffer.wrap(nameBytes);
                clientChannel.write(nameBuffer);
                
                FileChannel sbc=FileChannel.open(fileToSend.toPath());
                ByteBuffer buff=ByteBuffer.allocate(10000000);
                int bytesread=sbc.read(buff);
                
                while(bytesread != -1){
                    buff.flip();
                    clientChannel.write(buff);
                    buff.compact();
                    bytesread=sbc.read(buff);
                }
                System.out.println(fileName+" has been sent.");
                Thread.sleep(300);
            }
            
        } catch (Exception e) {
            System.out.println("Exception inside Client FileUploader "+e);
        }
    }
}
