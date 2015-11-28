
package com.ashif.play;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;


/**
 *
 * @author Ashif
 */




public class Server {

    //Constants
    public static int PORT = 2222;
    public static final String SERVER_DIRECTORY="d:/temp/server/";
    
    public static void main(String[] args) throws IOException{
        
        //initializing our server
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
        
        while (true) {            
            System.out.println("Waiting for Client");
            
            SocketChannel clientChannel= serverSocketChannel.accept();
            System.out.println("Accepting Client request");
            
            ByteBuffer pingbuff=ByteBuffer.allocate(500);
            clientChannel.read(pingbuff);
            byte[] pingbyte=new byte[500];

            int i=0;       
            int position=pingbuff.position();
           
            while(i<position){
                pingbyte[i]=pingbuff.get(i);
                i++;
            }
            
            String ping=new String(pingbyte,0,position);            
            
            //Calling appropriate handler for client request
            if (ping.equalsIgnoreCase("receive")) {
                Receiver(clientChannel);
            }
        }
    }
    
    
    public static void Receiver(SocketChannel clientChannel){
        try {
            ByteBuffer numBuffer=ByteBuffer.allocate(500);
            clientChannel.read(numBuffer);
            byte[] numByte=new byte[500];

            int j=0;       
            int pos=numBuffer.position();
           
            while(j<pos){
                numByte[j]=numBuffer.get(j);
                j++;
            }
            
            String number=new String(numByte,0,pos);
            int numofFiles=Integer.parseInt(number);
            
            
            //receiving files from client
            for(int i=0; i<numofFiles; i++){
                            
                ByteBuffer namebuff=ByteBuffer.allocate(500);
                clientChannel.read(namebuff);
                byte[] namebyte=new byte[500];

                int k=0;       
                int position=namebuff.position();
           
                while(k<position){
                    namebyte[k]=namebuff.get(k);
                    k++;
                }
                
                String filename=new String(namebyte,0,position); 
                File file=new File(SERVER_DIRECTORY+filename);

                ByteBuffer bb=ByteBuffer.allocate(10000000);
                int bytesRead=clientChannel.read(bb);
                FileOutputStream bout =new FileOutputStream(file);
                FileChannel sbc=bout.getChannel();

                while(bytesRead != -1){
                    bb.flip();
                    sbc.write(bb);
                    bb.compact();
                    bytesRead=clientChannel.read(bb);
                }
                
                System.out.println(filename+"has been received");
            }

        } catch (Exception e) {
        }
    }
}
