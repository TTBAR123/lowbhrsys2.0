package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
/**
 * 
 * lowbhrsys2.0
 * 使用b/s实现该系统，通过socket进行客户端和服务端的交互
 * 
 * 1、客户端向服务端发送封装的Message指令
 * 2、服务端接收Message指令，解析指令，调用CRUD类的方法进行数据库的操作，最后将执行结果返回给客户端
 * 
 * 启动方法：
 * 1、启动server类
 * 2、启动客户端ClientMainApp类
 * @author whh
 *
 */
public class ClientMainApp {
	public static void main(String[] args) throws Exception, IOException {
		 //1.客户端连接服务器端,返回套接字Socket对象
        Socket socket=new Socket("127.0.0.1",10010);
        //2.创建读取服务器端信息的线程和发送服务器端信息的线程
        Thread read=new Thread(new ClientReadServer(socket));
        Thread send=new Thread(new ClientSendServer(socket));
        //3.启动线程
        read.start();
        send.start();
	}
}
