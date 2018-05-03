package com.js.modular;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import com.js.data.Goods_DT;
import com.js.data.Member_DT;
import com.js.index.GuoDu_FF;
import com.js.pageui.Comon_Ui;
import com.js.pageui.Settle_Ui;

public class Settle_Mod {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		

		//模块核心：
		Settle_Ui.zhuYe();    //主页UI界面
		
		biaoji1:for (int i =0;  i != -1;) {
			
			//进入模块准备
			System.out.println("请选择功能：");
			int panDuan = sc.nextInt();
			
			 //全局静态变量，控制退出模块。
			if(panDuan == 9) {
				GuoDu_FF.gd = panDuan;   
				break biaoji1;
			}
			//进入模块成功：
			else {
				//进入主页
				switch(panDuan) {
				
				case 0://回到主页
					Settle_Ui.zhuYe();
					break;
				
				case 1://购物结算
					Settle_Ui.zhuYe_Up1();
					 Member_Mod meb = inPut_ID(); //是否有会员卡号判断，返回值为相应Member_Mod对象
					 if(meb != null) {
						 Collection<Goods_Mod> ls_List = inGoods(meb); //录入购买商品
						 System.out.println("会员编号："+meb.getBianHao());
						int a =  print_D(meb,ls_List); //打印购物清单，并积分
						 entry(meb,a); //录入会员积分
					 }
					
					Comon_Ui.zhuYe_Low();
					break;
				
				case 2://查询订单
					Settle_Ui.zhuYe_Up2();
					
					Comon_Ui.zhuYe_Low();
					break;
				
				case 3://修改订单信息
					Settle_Ui.zhuYe_Up3();
					
					Comon_Ui.zhuYe_Low();
					break;
					
				case 4://删除订单
					Settle_Ui.zhuYe_Up4();
					
					Comon_Ui.zhuYe_Low();
					break;
					
				default :System.out.println("功能编号输入错误！请重新输入");
				break;
				}
			}		
		}	
	}
	
	
//----------------------------------------------------------------------------------------
	//--订单属性：
		private  int bianHao; //订单编号
		private  String date; //订单日期
		
		public int getBianHao() {
			return bianHao;
		}

		public void setBianHao(int bianHao) {
			this.bianHao = bianHao;
		}

		public String getPrice() {
			return date;
		}

		public void setPrice(String date) {
			this.date =date;
		}

		//创建商品对象无参构造器
		public Settle_Mod() {}
		
//		//实现了Serializable接口,要为其指定ID！
//		private static final long serialVersionUID =-1898938359705462232L;
	//----------------------------------------------------------------------------------------------
		//验证是否输入会员卡号，返回相应会员对象
		public static Member_Mod inPut_ID() {
			Scanner sc1 = new Scanner(System.in);
			
			System.out.println("是否输入会员卡号？--------（是：1）/（否：0）");
			if(sc1.nextInt() == 1) { //是会员的情况下：
				biaoji1:while(true) {
					//控制台获取商品信息
					System.out.println("请输入会员卡号：");
					int a =sc1.nextInt(); //此处可增加编号界限
				
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
								int b2 =meb.getBianHao();
								if( b2== a) { //查询编号和已有编号相同
									return meb;
								}
						}    //标记2
						System.out.println("输入的会员不存在！是否重新输入？--------（是：1）/（否：0）");
						if(sc1.nextInt() == 0) {
							break biaoji1;
						}

					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}	
			}  //biaoji1	
		}
			return null;
	}
		
		//购买物品录入，参数获取为对应会员对象，并返回相应商品列表集合
		public static Collection<Goods_Mod> inGoods(Member_Mod meb) {
			Scanner sc1 = new Scanner(System.in);
			Collection<Goods_Mod> ls_List = new ArrayList();
			
			biaoji0:while(true) {
				biaoji1:while(true) {
					//控制台获取商品信息
					System.out.println("请输入商品编号：");
					int a =sc1.nextInt(); //此处可增加编号界限
				
				//遍历文件对象，将编号相同的对象输出到控制台
				if(Goods_DT.goods_dt.length() != 0) { //如果文件长度不为0就读取内容；
					try {
						FileInputStream in = new FileInputStream(Goods_DT.goods_dt); //通过打开一个到实际文件的连接来创建一个 FileInputStream
						ObjectInputStream ois = new ObjectInputStream(in); //创建从指定 InputStream 读取的 ObjectInputStream	
						
						while(in.available()>0) { //代表文件还有内容
							Goods_DT.list = (ArrayList<Goods_Mod>) ois.readObject(); //创建列表接收读取来的对象
						}
						
						ois.close();// 关闭流
						in.close(); //关闭流
				
							biaoji2:for (int i = 0; i < Goods_DT.list.size(); i++) { //问题：怎么让输出的内容自动对齐呢？
								Goods_Mod goods = ((ArrayList<Goods_Mod>) Goods_DT.list).get(i);
								int b2 =goods.getBianHao();
								if( b2== a) { //查询编号和已有编号相同
									 ls_List.add(goods);
									 break biaoji1;
								}
						}    //标记2
						System.out.println("输入的商品不存在！请重新输入！--------（是：1）/（否：0）");
						if(sc1.nextInt() == 0) {
							break biaoji1;
						}
						
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}	
			}  //biaoji1
			
			//跳出biaoji0循环，不再录入商品。
			System.out.println("是否继续输入？--------（是：1）/（否：0）");
			if(sc1.nextInt() == 0) {
//				break biaoji0;
				return ls_List;
			}
		}		
	}
		
		//打印购物清单
		public static int print_D(Member_Mod meb,Collection<Goods_Mod> ls_List) {
			System.out.println("本次购物商品清单：");
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			double tprice = 0;
			for (int i = 0; i < ls_List.size(); i++) {
				Goods_Mod gm =  ((ArrayList<Goods_Mod>) ls_List).get(i);
				tprice += gm.getPrice(); //获取购物清单总价格
				
				System.out.println("商品名称："+gm.getName()+"\t\t商品编号："+gm.getBianHao()+"\t\t商品价格："+gm.getPrice()+"元");
			}
			System.out.println("商品总数为："+ls_List.size()+"件"+"\t商品总价为："+tprice+"元");
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("应付金额："+tprice+"元");
			System.out.println("会员折扣："+(tprice*0.1)+"元");
			System.out.println("实付金额："+(tprice*0.9)+"元");
			int a = (int) tprice;
			System.out.println("本次积分："+a+"分");
			return a;
		}
		
		//录入会员积分
		public static void entry(Member_Mod meb, int a1) {

			int a =meb.getBianHao(); //此处可增加编号界限
			
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
							Member_Mod meb1 = ((ArrayList<Member_Mod>) Member_DT.list).get(i);
							int b2 =meb1.getBianHao();
							if( b2== a) { //查询编号和已有编号相同
								boolean isexist=false;//定义一个用来判断文件是否需要截掉头aced 0005的
								if(Member_DT.member_dt.exists()){    //文件是否存在
									isexist=true;
									FileOutputStream fo;
									try {
										fo = new FileOutputStream(Member_DT.member_dt,true);
										ObjectOutputStream oos = new ObjectOutputStream(fo);
									    long pos=0;
									    if(isexist){
									    	pos=fo.getChannel().position()-4;//追加的时候去掉头部aced 0005
									        fo.getChannel().truncate(pos);
									        }
									    if(meb1.getIntegral()>0) {
									    	meb1.setIntegral(a1+meb1.getIntegral());
									    }
									    	
									        oos.writeObject(Member_DT.list);//进行序列化，输出新的对象； 
									        oos.flush();
									                            
									         oos.close();//关闭流
									         fo.close();//关闭流
												} catch (FileNotFoundException e) {	
													e.printStackTrace();
												} catch (IOException e) {
													e.printStackTrace();
												}   
										}
									}
								}	
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							} catch (ClassNotFoundException e) {
								e.printStackTrace();
							}
						}
					}    

		


				
//------------------------------------------------------------------------------------------------------------------------------			
}
