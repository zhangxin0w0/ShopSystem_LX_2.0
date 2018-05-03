package com.js.pageui;

public class Member_Ui {
	//1.数据库功能首页
			public static void zhuYe(){
					System.out.println("购物管理系统>会员管理");
					System.out.println("**********************************");
					System.out.println("\t1.显示所有会员信息");
					System.out.println("\t2.添加会员信息");
					System.out.println("\t3.修改会员信息");
					System.out.println("\t4.删除会员信息");
					System.out.println();
					System.out.println("**********************************");
					System.out.println("0.返回上一级\t9.退出设置");
			}
			
			//界面顶部UI
			public static void zhuYe_Up1() {
				System.out.println("购物管理系统>会员管理>显示所有会员信息");
				System.out.println("**********************************");
			}
			
			public static void zhuYe_Up2() {
				System.out.println("购物管理系统>权限设置>添加会员信息");
				System.out.println("**********************************");
			}
			
			public static void zhuYe_Up3() {
				System.out.println("购物管理系统>权限设置>修改会员信息");
				System.out.println("**********************************");
			}
			
			public static void zhuYe_Up4() {
				System.out.println("购物管理系统>权限设置>删除会员信息");
				System.out.println("**********************************");
			}
}
