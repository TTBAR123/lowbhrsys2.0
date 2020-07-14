package client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import pojo.Employee;
import pojo.Message;
/**
 * 客户端向服务器发送消息的线程
 * @author whh
 *
 */
public class ClientSendServer implements Runnable {
	private Socket socket;
	private static ObjectOutputStream os= null;
	 private static Scanner scan = null;
    public ClientSendServer(Socket socket){
        this.socket=socket;
    }
	@Override
	public void run() {
		
		 try {
			os = new ObjectOutputStream(socket.getOutputStream());
			operator();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				os.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		

	}
	
	
	public static void sendMsg(Object msg) {
        try {
            //调用发送
            os.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	public static void operator(){
    	help();
		while (getScan().hasNextLine()) {
			String s = scan.nextLine();

			if (s.equals("1")) {
				sendMsg(addEmp());
			}

			if (s.equals("2")) {
				sendMsg(findEmpById());
			}

			if (s.equals("3")) {
				sendMsg(queryAllEmp());
			}

			if (s.equals("4")) {
				sendMsg(updateEmp());
			}
			
			if (s.equals("5")) {
				sendMsg(delEmp());
			}

			if (s.equals("help")) {
				help();
			}

			if (s.equals("exit")) {
				System.out.println("退出系统!");
				System.exit(0);
			}

		}
    	
    }
    
    
    /**
	 * 指令提示信息
	 */
	public static void help() {
		System.out.println("1->添加员工");
		System.out.println("2->根据id查询员工");
		System.out.println("3->查询所有员工");
		System.out.println("4->修改员工信息");
		System.out.println("5->删除员工");
		System.out.println("help->命令帮助");
		System.out.println("exit->退出系统");
	}
	
	/**
	 * 添加员工
	 */
	public static Message addEmp() {
			System.out.println("输入员工姓名：");
			String name= scan.nextLine();

			System.out.println("输入员工年龄：");
			int age= Integer.parseInt(scan.nextLine());

			System.out.println("输入员工性别：");
			int sex = scan.nextLine().equals("男") ? 0 : 1;

			System.out.println("输入员工薪水：");
			double salary = Double.parseDouble(scan.nextLine());

			Employee emp = new Employee(name, age, sex, salary);
			
			//封装成Message返回
			return new Message(1,emp);
			
	}

	/**
	 * 根据id查询
	 */
	public static Message findEmpById() {

		System.out.println("输入员工id：");
		int id = Integer.parseInt(scan.nextLine());
		
		//封装成Message返回
		return new Message(2,new Employee(id));
		

	}
	
	
	/**
	 * 查询所有员工
	 */
	public static Message queryAllEmp(){
	
		//封装成Message返回
		return new Message(3);
	}
	
	
	/**
	 * 修改员工信息
	 */
	public static Message updateEmp(){
		System.out.println("输入员工id：");
		int id = Integer.parseInt(scan.nextLine());
		
		//封装成Message返回
		return new Message(4,new Employee(id));
		

	}
	
	
	
	/**
	 * 根据id删除员工信息
	 */
	public static Message delEmp(){
		System.out.println("输入员工id：");
		int id = Integer.parseInt(scan.nextLine());


		//封装成Message返回
		return new Message(5,new Employee(id));
		
		

	}
	
	//返回一个scanner的实例
	private static Scanner getScan() {
		if (scan == null) {
			scan = new Scanner(System.in);
		}

		return scan;
	}

}
