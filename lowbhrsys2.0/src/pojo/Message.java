package pojo;

import java.io.Serializable;

/**
 * 封装操作指令
 * @author whh
 *
 */
public class Message implements Serializable{
	private int oper;
	private Employee emp;
	public Message(int oper, Employee emp) {
		super();
		this.oper = oper;
		this.emp = emp;
	}
	public Message(int oper) {
		super();
		this.oper = oper;
	}
	@Override
	public String toString() {
		return "Message-[oper=" + oper + ", emp=" + emp + "]";
	}
	
	
	
	public int getOper() {
		return oper;
	}
	public void setOper(int oper) {
		this.oper = oper;
	}
	public Employee getEmp() {
		return emp;
	}
	public void setEmp(Employee emp) {
		this.emp = emp;
	}
	//测试封装效果
	public static void main(String[] args) {
		Message msg = new Message(1, new Employee("zs", 11, 1, 12));
		
		System.out.println(msg);
	}
	
	
}
