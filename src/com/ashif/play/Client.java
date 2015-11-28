
package com.ashif.play;

/**
 *
 * @author Ashif
 */


import java.net.InetSocketAddress;
import java.nio.channels.*;



public class Client {

    public static int port = 2222;
    public static String host = "localhost";
    
    public static void main(String[] args) {
        
        
        
        
    }
    
    
    
    public static void FileUploader(){
        
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.bind(new InetSocketAddress(host, port));
            
            
        } catch (Exception e) {
            System.out.println("Exception inside Client FileUploader "+e);
        }
    }
}
