package com.js.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.js.modular.Admin_Mod;
import com.js.modular.Goods_Mod;

public class Goods_DT {
	//数据库功能完善性测试
	public static void main(String[] args) {
		
	}
	
	
	
//------------------------------------------------------------------------------------------	
		//读取商品数据文件，路径：c:\\JSshop_DT\\Goods_DT.txt
		public static  File goods_dt = new File("c:\\JSshop_DT\\Goods_DT.txt");
		
		public static Collection<Goods_Mod> list = new ArrayList(); //创建列表接收读取来的对象
		
		//静态块给文件初始一个数据
//		public static Goods_Mod xin; 测试商品静态化，全局调用；
		static {
			if(goods_dt.length() == 0) { //如果文件内容为0就增加测试商品；
				//初始化商品：
				Goods_Mod xin = new Goods_Mod();
				xin.setName("测试商品"); 
				xin.setBianHao(10000);
				xin.setPrice(99.99);
				//输出流，将对象保存到文件中
				try {
					FileOutputStream out = new FileOutputStream(goods_dt); //创建一个向指定 File 对象表示的文件中写入数据的文件输出流。
					ObjectOutputStream oos = new ObjectOutputStream(out); //创建写入指定 OutputStream 的 ObjectOutputStream。
					list.add(xin);
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
				goods_dt.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//！！商品数据文件删除，谨慎操作！！
		public static void beEnd() {
			goods_dt.delete();
		}
		
//--------------------------------------------------------------------------------------------		
}
		