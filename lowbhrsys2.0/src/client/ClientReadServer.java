package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * 客户端接收服务器端消息的线程
 * @author whh
 *
 */
public class ClientReadServer implements Runnable {
	private Socket socket;
	private static ObjectInputStream is = null;
    public ClientReadServer(Socket socket){
        this.socket=socket;
    }
	@Override
	public void run() {
		try {
			 //数据流接收数据
			is = new ObjectInputStream(socket.getInputStream());
			while(true){
				Object msg = is.readObject();
				System.out.println(msg);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				is.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
