package com.js.modular;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
//工具包导入：
import java.util.Scanner;

import com.js.data.Admin_DT;
import com.js.data.Member_DT;
import com.js.index.GuoDu_FF;
import com.js.pageui.Admin_Ui;
import com.js.pageui.Comon_Ui;
import com.js.pageui.Login_Ui;

public class Login_Mod {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Login_Ui.zhuYe();
		biaoji1:for (int i =0;  i != -1;) {
			System.out.println("请选择功能：");
			int panDuan = sc.nextInt();
			//进入主页
			switch(panDuan) {			
			case 1://管理员账号密码登录
				Login_Ui.zhuYe_Up1();
				biaoji2:for (int j = 0; j < 3; j++) {
					boolean flag = getLogin();
					if(flag == true) {
						break biaoji1;
					}else if(flag == false && j==2) {
						System.out.println("-----输入3次错误，系统关闭！----");
						GuoDu_FF.gd = 9;
						return;
					}
				}
				Comon_Ui.zhuYe_Low();
				break;
			
			case 2://会员账号密码登录
				Login_Ui.zhuYe_Up2();
				biaoji3:for (int j = 0; j < 3; j++) {
					boolean flag = getLogin1();
					if(flag == true) {
						GuoDu_FF.gd = 2;
						break biaoji1;
					}else if(flag == false && j==2) {
						System.out.println("-----输入3次错误，系统关闭！----");
						GuoDu_FF.gd = 9;
						return;
					}
				}
				Comon_Ui.zhuYe_Low();
				break;		
			
			case 3://修改管理员密码
				Login_Ui.zhuYe_Up3();
				Admin_Mod.modifyAdmin();
				Comon_Ui.zhuYe_Low();
				break;
				
			case 4://退出系统
				return;
				
			default :System.out.println("功能编号输入错误！请重新输入");
			break;
			}
		}
	}


//控制台获取管理员账号和密码
	public static boolean getLogin() {
		Admin_Mod login = new Admin_Mod();
		Scanner sc = new Scanner(System.in);
		//获取账号
		System.out.println("请输管理员入账号：");
		login.setZhangHao(sc.nextInt());
		//获取密码

		System.out.println("请输入管理员密码：");
		login.setPassWord(sc.nextInt()); 
		

		//控制台获取商品信息
		int a =login.getPassWord(); //此处可增加编号界限
		int a1 = login.getZhangHao();
		//遍历文件对象，将编号相同的对象输出到控制台
		if(Admin_DT.admin_dt.length() != 0) { //如果文件长度不为0就读取内容；
			try {
				FileInputStream in = new FileInputStream(Admin_DT.admin_dt); //通过打开一个到实际文件的连接来创建一个 FileInputStream
				ObjectInputStream ois = new ObjectInputStream(in); //创建从指定 InputStream 读取的 ObjectInputStream	
				
				while(in.available()>0) { //代表文件还有内容
					Admin_DT.list = (ArrayList<Admin_Mod>) ois.readObject(); //创建列表接收读取来的对象
				}
				
				ois.close();// 关闭流
				in.close(); //关闭流
		
					biaoji2:for (int i = 0; i < Admin_DT.list.size(); i++) { //问题：怎么让输出的内容自动对齐呢？
						Admin_Mod meb = ((ArrayList<Admin_Mod>) Admin_DT.list).get(i);
						int b2 =meb.getPassWord();
						int b1 =meb.getZhangHao();
						if( b2== a && a1 ==b1) { //查询编号和已有编号相同
							return true;
						}
				}//标记2
				System.out.println("账户或密码输入错误！请重新输入！");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return false;
	} 

	//控制台获取管理员账号和密码
		public static boolean getLogin1() {
			Member_Mod login = new Member_Mod();
			Scanner sc = new Scanner(System.in);
			//获取账号
			System.out.println("请输入会员编号：");
			login.setBianHao(sc.nextInt());
			//获取密码
			System.out.println("请输入会员密码：");
			login.setPassWord(sc.nextInt()); 
			

			//控制台获取商品信息
			int a =login.getPassWord(); //此处可增加编号界限
			int a1 = login.getBianHao();
			//遍历文件对象，将编号相同的对象输出到控制台
			if(Member_DT.member_dt.length() != 0) { //如果文件长度不为0就读取内容；
				try {
					FileInputStream in = new FileInputStream(Member_DT.member_dt); //通过打开一个到实际文件的连接来创建一个 FileInputStream
					ObjectInputStream ois = new ObjectInputStream(in); //创建从指定 InputStream 读取的 ObjectInputStream	
					
					while(in.available()>0) { //代表文件还有内容
						Member_DT.list = (ArrayList<Member_Mod>) ois.readObject(); //创建列表接收读取来的对象
					}
					
					ois.close();// 关闭流
					in.close(); //关闭流
			
						biaoji2:for (int i = 0; i < Member_DT.list.size(); i++) { //问题：怎么让输出的内容自动对齐呢？
							Member_Mod meb = ((ArrayList<Member_Mod>) Member_DT.list).get(i);
							int b2 =meb.getPassWord();
							int b1 =meb.getBianHao();
							if( b2== a && a1 ==b1) { //查询编号和已有编号相同
								return true;
							}
					}//标记2
					System.out.println("编号或密码输入错误！请重新输入！");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			return false;
		} 

//---------------------------------------------------------------------------------------------------------
}
