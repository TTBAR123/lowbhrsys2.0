package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.CRUD;
import pojo.Employee;
import pojo.Message;

/**
 * 服务端
 */
public class Server {
    private static ServerSocket server = null;
    private static Socket ss = null;
    //用于增删改查
    private static CRUD crud = null;
 
    public static void main(String[] args) {
        server();
    }

    /**
     * 普通服务器连接
     */
    private static void server() {
        try {
            //建立服务端
            server = new ServerSocket(10010);
            System.out.println("server端已启动！");
            while (true) {
                //创建接收接口
                ss = server.accept();
                //启动新客户监听线程
                new ServerThread(server, ss).start();
                
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ss.close();
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 内部类线程，每连接一个新的客户端就启动一个对应的监听线程
     */
    @SuppressWarnings("Duplicates")
    private static class ServerThread extends Thread {
        ServerSocket server = null;
        Socket socket = null;
        ObjectInputStream is = null;
        ObjectOutputStream os = null;
        String clientName = null;
        boolean alive = true;

        public ServerThread() {
        }

        ServerThread(ServerSocket server, Socket socket) {
            this.socket = socket;
            this.server = server;
        }

        @Override
        public void run() {
            
            try {
            	//接收数据流
                is = new ObjectInputStream(socket.getInputStream());
                //发送数据流
                os = new ObjectOutputStream(socket.getOutputStream());
                while(alive){

                	 Message msg = (Message)is.readObject();
                	 //那种操作，1代表添加员工；2，代表按id查询员工；3，代表查询所有；4，代表修改员工；5，代表根据id删除员工
                     int oper = msg.getOper();
                     if(oper==1){
                    	 //添加用户
                    	 getcrud().addEmp(msg.getEmp());
                    	 sendMsg("添加成功");
                     }else if(oper==2){
                    
                    	 Employee emp =  getcrud().getEmp(msg.getEmp().getId());
                    	 System.out.println(emp);
                    	 sendMsg(emp);
                     }else if(oper==3){
                    	 List<Employee> query =  getcrud().query();
                    	 sendMsg(query);
                     }else if(oper==4){
                    	 getcrud().updateEmp(msg.getEmp().getId(), msg.getEmp());
                    	 sendMsg("修改成功");
                     }else{
                    	 getcrud().delEmp(msg.getEmp().getId());
                    	 sendMsg("删除成功");
                     }
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("与" + clientName + "连接中断，被迫关闭监听线程！");
            } catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				sendMsg("操作失败");
				e.printStackTrace();
			} finally {
                try {
                    os.close();
                    is.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        //发送数据
        public void sendMsg(Object msg) {
            try {
                //调用发送
                os.writeObject(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
 
    
  //返回一个CRUD的实例
  	private static CRUD getcrud() {
  		if (crud == null) {
  			crud = new CRUD();
  		}
  		return crud;
  	}
}