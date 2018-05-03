package com.js.modular;

import java.util.Scanner;

import com.js.index.GuoDu_FF;
import com.js.pageui.Comon_Ui;
import com.js.pageui.SetUp_Ui;

public class SetUp_Mod {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		SetUp_Ui.zhuYe();
		
		//模块核心
		biaoji1:for (int i =0;  i != -1;) {
			System.out.println("请选择功能：");
			int panDuan = sc.nextInt();
			if(panDuan == 9) {
				GuoDu_FF.gd = panDuan;
				break biaoji1;
			}else {
				
				switch(panDuan) {	
				case 0:
					SetUp_Ui.zhuYe();
					break ;
				
				case 1://修改系统公告
					SetUp_Ui.zhuYe_Up();
					 inPutNotice();
					Comon_Ui.zhuYe_Low();
					break;
					
				default :System.out.println("功能编号输入错误！请重新输入");
				break;
				}
			}	
		}
	}
	
	
	//系统设置属性
	public static String notice = "\t欢迎使用购物管理系统！";
	
	//系统公告输出
		public static void inPutNotice() {
			Scanner sc = new Scanner(System.in);
			System.out.println("请输入公告内容：");
			notice = sc.next();
			System.out.println("确认修改？1/0");
			if(sc.nextInt() == 1) {
				System.out.println("修改成功！");
			}
		}
		
	//系统公告输出
	public void printNotice() {
		System.out.println(notice);
	}
	
}
