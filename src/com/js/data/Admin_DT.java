package com.js.data;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
//管理员账户数据库
import java.util.Scanner;

import com.js.modular.Admin_Mod;
import com.js.modular.Member_Mod;

public class Admin_DT {
	public static void main(String[] args) {
		
	}
	//------------------------------------------------------------------------------------------	
	//读取商品数据文件，路径：c:\\JSshop_DT\\Member_DT.txt
	public static  File admin_dt = new File("c:\\JSshop_DT\\Admin_DT.txt");
	
	public static Collection<Admin_Mod> list = new ArrayList(); //创建列表接收读取来的对象
	
	//静态块给文件初始一个数据
	static {
		if(admin_dt.length() == 0) { //如果文件内容为0就增加测试商品；
			//初始化商品：
			Admin_Mod xin1 = new Admin_Mod();
			xin1.setName("测试管理员"); 
			xin1.setZhangHao(10000);
			xin1.setPassWord(888888);
			//输出流，将对象保存到文件中
			try {
				FileOutputStream out = new FileOutputStream(admin_dt); //创建一个向指定 File 对象表示的文件中写入数据的文件输出流。
				ObjectOutputStream oos = new ObjectOutputStream(out); //创建写入指定 OutputStream 的 ObjectOutputStream。
				list.add(xin1);
				oos.writeObject(list); //将对象写入文件
				oos.flush(); //刷新流
				oos.close(); //关闭流
				out.close(); //关闭流

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
//数据库功能：
	
	//！！初始化，创建新的文件存储数据！！
	public static void getNews() {
		try {
			admin_dt.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//！！商品数据文件删除，谨慎操作！！
	public static void beEnd() {
		admin_dt.delete();
	}
	
//--------------------------------------------------------------------------------------------		
	
}
