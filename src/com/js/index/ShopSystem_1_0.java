package com.js.index;

import java.util.Collection;
import java.util.Scanner;

import com.js.modular.Admin_Mod;
import com.js.modular.Goods_Mod;
import com.js.modular.Login_Mod;
import com.js.modular.Member_Mod;
import com.js.modular.SetUp_Mod;
import com.js.modular.Settle_Mod;
import com.js.pageui.Comon_Ui;
import com.js.pageui.Index_Ui;
import com.js.pageui.Settle_Ui;

public class ShopSystem_1_0 {
	public static void main(String[] args) {
		//登录界面
		Login_Mod.main(null);
		if(GuoDu_FF.gd == 9) {
			return;
		}
		
		if(GuoDu_FF.gd == 2) {
			//会员欢迎界面
			for (int i =0;  i != -1;) {
				//系统功能选择主界面：
				Index_Ui.zhuYeUi_Meb();
				
				//进入系统准备：
				Scanner sc = new Scanner(System.in);
				System.out.println("请选择功能：");
				int panDuan = sc.nextInt();
				
				//进入主页
				switch(panDuan) {
				case 0://返回功能界面
					return;
				case 1://购物结算界面界面
					Index_Ui.zhuYe_Up1();
					 Member_Mod meb = Settle_Mod.inPut_ID(); //是否有会员卡号判断，返回值为相应Member_Mod对象
					 if(meb != null) {
						 Collection<Goods_Mod> ls_List = Settle_Mod.inGoods(meb); //录入购买商品
						 System.out.println("会员编号："+meb.getBianHao());
						int a =  Settle_Mod.print_D(meb,ls_List); //打印购物清单，并积分
						Settle_Mod.entry(meb,a); //录入会员积分
					 }
					
					Comon_Ui.zhuYe_Low();
					if(GuoDu_FF.gd == 9) {
						break;
					}
				
				case 2:
					break;
					
				default :System.out.println("功能编号输入错误！请重新输入");
				break;
				}
			}
		}
		
		//管理员欢迎界面
		for (int i =0;  i != -1;) {
			//系统公告：
			new SetUp_Mod().printNotice();
			
			//系统功能选择主界面：
			Index_Ui.zhuYeUi();
			
			//进入系统准备：
			Scanner sc = new Scanner(System.in);
			System.out.println("请选择功能：");
			int panDuan = sc.nextInt();
			
			//进入主页
			switch(panDuan) {
			case 0://返回功能界面
				return;
			case 1://会员管理界面
				Member_Mod.main(null);
				if(GuoDu_FF.gd == 9) {
					break;
				}
			case 2://商品管理界面
				Goods_Mod.main(null);
				if(GuoDu_FF.gd == 9) {
					break;
				}
			case 3://结算管理界面
				Settle_Mod.main(null);
				if(GuoDu_FF.gd == 9) {
					break;
				}
			case 4://营销管理界面
				SetUp_Mod.main(null);
				
				if(GuoDu_FF.gd == 9) {
					break;
				}
			case 5://权限管理界面
				Admin_Mod.main(null);
				
				if(GuoDu_FF.gd == 9) {
					break;
				}
			case 6://系统设置界面
				SetUp_Mod.main(null);
				
				if(GuoDu_FF.gd == 9) {
					break;
				}
			default :System.out.println("功能编号输入错误！请重新输入");
			break;
			}
		}
	}
}
