package com.js.modular;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import com.js.data.Admin_DT;
import com.js.data.Goods_DT;
import com.js.data.Member_DT;
import com.js.index.GuoDu_FF;
import com.js.pageui.Goods_UI;
import com.js.pageui.Admin_Ui;
import com.js.pageui.Comon_Ui;

public class Goods_Mod implements Serializable{
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		

		//模块核心：
		Goods_UI.zhuYe();    //主页UI界面
		
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
					Goods_UI.zhuYe();
					break;
				
				case 1://显示所有商品信息
					Goods_UI.zhuYe_Up1();
					printList();
					Comon_Ui.zhuYe_Low();
					break;
				
				case 2://上架新商品
					Goods_UI.zhuYe_Up2();
					while(true) {
						Goods_Mod xin = getGoods();
						if(xin != null) {
							addFlie(xin);
						}
						 //判断是否继续上架新产品。
                        System.out.println("是否继续上架？------（是：1）/（否：0）");
                        if(sc.nextInt() == 0) {
                        	break;
                        }
					}
					Comon_Ui.zhuYe_Low();
					break;
				
				case 3://修改商品信息
					Goods_UI.zhuYe_Up3();
					modifyGoods();
					Comon_Ui.zhuYe_Low();
					break;
					
				case 4://下架商品
					Goods_UI.zhuYe_Up4();
					delGoods();
					Comon_Ui.zhuYe_Low();
					break;
					
				default :System.out.println("功能编号输入错误！请重新输入");
				break;
				}
			}		
		}	
	}


//-------------------------------------------------------------------------------------------
	//--商品属性：
	private  String name; //名字
	private  int bianHao; //编号
	private  double price; //价格
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBianHao() {
		return bianHao;
	}

	public void setBianHao(int bianHao) {
		this.bianHao = bianHao;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	//创建商品对象无参构造器
	public Goods_Mod() {}
	
	//实现了Serializable接口,要为其指定ID！
	private static final long serialVersionUID =-1898938359705462232L;
//----------------------------------------------------------------------------------------------
	//调用方法，创建新商品
	public  static Goods_Mod getGoods() {
		Scanner sc = new Scanner(System.in);
		Goods_Mod a = new Goods_Mod();
		
		//控制台获取商品信息
		System.out.println("请输入商品名：");
		a.name=sc.next();
		System.out.println("请输入编号：");
		a.bianHao=sc.nextInt();
		System.out.println("请输入价格（元）：");
		a.price=sc.nextDouble();
		
		//确认获取到的商品信息
		System.out.println("商品名称："+a.name);
		System.out.println("商品编号："+a.bianHao);
		System.out.println("商品价格（元）："+a.price);
		System.out.println("确认无误创建：--------（是：1）/（否：0）");
		if(sc.nextInt() ==1) {
			//此处调用查重验证，判断编号商品是否存在，存在返回a，不存在返回null，并输出"上架失败，该商品编号已存在！"
			return a;
		}else {return null;}
	}
//---------------------------------------------------------------------------------------------（2）	
	//2.加入新的商品到数据文件中；
			public static void addFlie(Goods_Mod a) {	
				boolean isexist=false;//定义一个用来判断文件是否需要截掉头aced 0005的
				
					if(Goods_DT.goods_dt.exists()){    //文件是否存在
						
		                   isexist=true;
		                   FileOutputStream fo;
						try {
							fo = new FileOutputStream(Goods_DT.goods_dt,true);
							ObjectOutputStream oos = new ObjectOutputStream(fo);
			                  long pos=0;
			                  if(isexist){
			                            pos=fo.getChannel().position()-4;//追加的时候去掉头部aced 0005
			                            fo.getChannel().truncate(pos);
			                               }
			                  			
			                  			Goods_DT.list.add(a);//将对象加入到列表中；
			                  
			                            oos.writeObject(Goods_DT.list);//进行序列化，将列表输出； 
			                            oos.flush();
			                            System.out.println("商品名称："+a.name+"\t商品编号："+a.bianHao+"\t商品上架成功！");
			                            
			                            oos.close();//关闭流
			                            fo.close();//关闭流
						} catch (FileNotFoundException e) {	
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}   
				}
			}
//---------------------------------------------------------------------------------------------（1）
	//1.输出商品数据库中所有对象；
	public static void printList() {
		if(Goods_DT.goods_dt.length() != 0) { //如果文件长度不为0就读取内容；
			try {
				FileInputStream in = new FileInputStream(Goods_DT.goods_dt); //通过打开一个到实际文件的连接来创建一个 FileInputStream
				ObjectInputStream ois = new ObjectInputStream(in); //创建从指定 InputStream 读取的 ObjectInputStream
						
				while(in.available()>0) { //代表文件还有内容
					Goods_DT.list = (ArrayList<Goods_Mod>) ois.readObject(); //创建列表接收读取来的对象
				}

				for (int i = 0; i < Goods_DT.list.size(); i++) {
					Goods_Mod goods = ((ArrayList<Goods_Mod>) Goods_DT.list).get(i); //获取列表中相应的类型对象	
					System.out.println("商品名称："+goods.name+"\t商品编号："+goods.bianHao+"\t商品价格："+goods.price+"元");
					System.out.println("----------------------------------------------------------------------------------------------------");
				}	
				System.out.println("现存商品总数："+Goods_DT.list.size());
				
				ois.close();// 关闭流
				in.close(); //关闭流
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		else {
			System.out.println("商品库中没有一件产品呢！快去上架新商品吧！");
		}	
	}
//---------------------------------------------------------------------------------------------（3）	
//	3.根据输入的商品编号查询商品

		public  static void modifyGoods() {
			Scanner sc1 = new Scanner(System.in);
			
			biaoji1:while(true) {
				//控制台获取商品信息
				System.out.println("请输入要修改的商品编号：");
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
							int b2 =goods.bianHao;
							if( b2== a) { //查询编号和已有编号相同
								System.out.println("要修改的商品为：");
								System.out.println("商品名称："+goods.name+"\t商品编号："+goods.bianHao+"\t商品价格："+goods.price+"元");
								System.out.println("确认修改此商品？--------（是：1）/（否：0）");
								if(sc1.nextInt() == 1) {
									//控制台获取商品信息
									System.out.println("请输入新的商品名：");
									goods.name=sc1.next();
									System.out.println("请输入新的编号：");
									goods.bianHao=sc1.nextInt();
									System.out.println("请输入新的价格：");
									goods.price=sc1.nextDouble();
									
									while(true) { //修改信息错误，重新修改
										//确认修改的商品信息
										System.out.println("商品名称："+goods.name);
										System.out.println("商品编号："+goods.bianHao);
										System.out.println("商品价格："+goods.price);
										System.out.println("确认修改内容：？--------（是：1）/（否：0）");
										if(sc1.nextInt() == 1) {
											boolean isexist=false;//定义一个用来判断文件是否需要截掉头aced 0005的
											
											if(Goods_DT.goods_dt.exists()){    //文件是否存在
												
								                   isexist=true;
								                   FileOutputStream fo;
												try {
													fo = new FileOutputStream(Goods_DT.goods_dt,true);
													ObjectOutputStream oos = new ObjectOutputStream(fo);
									                   long pos=0;
									                  if(isexist){
									                            pos=fo.getChannel().position()-4;//追加的时候去掉头部aced 0005
									                            fo.getChannel().truncate(pos);
									                               }
									                            oos.writeObject(Goods_DT.list);//进行序列化，输出新的对象； 
									                            oos.flush();
									                            
									                            oos.close();//关闭流
									                            fo.close();//关闭流
												} catch (FileNotFoundException e) {	
													e.printStackTrace();
												} catch (IOException e) {
													e.printStackTrace();
												}   
										}
											System.out.println("商品修改成功！");
											break biaoji1;
										}
										else {
											break biaoji1;    //修改内容不确定，返回主页面
										}
									}	
//									-----------------------------------------------------
								}
								else {
									break biaoji1;    //修改商品不确定，返回主页面
								}
//								--------------------------------------------------------
							}
//							--------------------------------------------------------
					}    //标记2
					System.out.println("要查询的商品不存在！是否重新查询？--------（是：1）/（否：0）");
					if(sc1.nextInt() == 0) {
						break biaoji1;
					}
//					---------
				
//				---------
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

//---------------------------------------------------------------------------------------------（4）	
//		4.根据输入的商品编号删除商品

			public  static void delGoods() {
				Scanner sc1 = new Scanner(System.in);
				
				biaoji1:while(true) {
					//控制台获取商品信息
					System.out.println("请输入要删除的商品编号：");
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
								int b2 =goods.bianHao;
								if( b2== a) { //查询编号和已有编号相同
									System.out.println("要删除的商品为：");
									System.out.println("商品名称："+goods.name+"\t商品编号："+goods.bianHao+"\t商品价格："+goods.price+"元");
									System.out.println("确认删除此商品？--------（是：1）/（否：0）");
									if(sc1.nextInt() == 1) {
										boolean isexist=false;//定义一个用来判断文件是否需要截掉头aced 0005的
												
										if(Goods_DT.goods_dt.exists()){    //文件是否存在
													
									        isexist=true;
									        FileOutputStream fo;
									        try {
											fo = new FileOutputStream(Goods_DT.goods_dt,true);
											ObjectOutputStream oos = new ObjectOutputStream(fo);
										    long pos=0;
										    if(isexist){
										    	pos=fo.getChannel().position()-4;//追加的时候去掉头部aced 0005
										        fo.getChannel().truncate(pos);
										        }
										    
										    	Goods_DT.list.remove(goods); //移除要删除的商品；
										        oos.writeObject(Goods_DT.list);//进行序列化，输出新的对象； 
										        oos.flush();
										                            
										         oos.close();//关闭流
										         fo.close();//关闭流
													} catch (FileNotFoundException e) {	
														e.printStackTrace();
													} catch (IOException e) {
														e.printStackTrace();
													}   
											}
												System.out.println("商品删除成功！");
												break biaoji1;
											}
											else {
												break biaoji1;    //删除内容不确定，返回主页面
											}
										}		
						}//标记2
//						---------
						System.out.println("要删除的商品不存在！是否重新查询？--------（是：1）/（否：0）");
						if(sc1.nextInt() == 0) {
							break biaoji1;
						}
//					---------
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			} //标记1
		}
		
		
		
//----------------------------------------------------------------------------------------------------	
}
