package com.js.modular;

import java.util.Scanner;

import com.js.comon.Comon_YZ;
import com.js.data.Member_DT;
import com.js.index.GuoDu_FF;
import com.js.pageui.Comon_Ui;
import com.js.pageui.Member_Ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class Member_Mod implements Serializable{
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		

		//模块核心：
		Member_Ui.zhuYe();    //主页UI界面
		
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
					Member_Ui.zhuYe();
					break;
				
				case 1://显示所有会员信息
					Member_Ui.zhuYe_Up1();
					printList();
					Comon_Ui.zhuYe_Low();
					break;
				
				case 2://增加新会员
					Member_Ui.zhuYe_Up2();
					while(true) {
						Member_Mod xin = getMember();
						if(xin != null) {
							addFlie(xin);
						}
						 //判断是否继续增加新会员。
                        System.out.println("是否继续增加？------（是：1）/（否：0）");
                        if(sc.nextInt() == 0) {
                        	break;
                        }
					}
					Comon_Ui.zhuYe_Low();
					break;
				
				case 3://修改会员信息
					Member_Ui.zhuYe_Up3();
					modifyMember();
					Comon_Ui.zhuYe_Low();
					break;
					
				case 4://删除商品
					Member_Ui.zhuYe_Up4();
					delMember();
					Comon_Ui.zhuYe_Low();
					break;
					
				default :System.out.println("功能编号输入错误！请重新输入");
				break;
				}
			}		
		}	
	}
	
//----------------------------------------------------------------------
	//--会员属性：
	private  String name; //名字
	private  int bianHao; //编号
	private  int passWord; //密码（6位数字）
	private  int integral = 0; //积分
	
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

	public int getPassWord() {
		return passWord;
	}

	public void setPassWord(int passWord) {
		this.passWord = passWord;
	}

	public int getIntegral() {
		return integral;
	}

	public void setIntegral(int integral) {
		this.integral = integral;
	}

	//创建会员账户无参构造器
	public Member_Mod() {}
	
	//调用方法创建新的会员账户
	public  static Member_Mod getMember() {
		Scanner sc = new Scanner(System.in);
		Member_Mod a = new Member_Mod();
		
		System.out.println("请输入姓名：");
		a.name=sc.next();
		System.out.println("请输入编号：");
		a.bianHao=sc.nextInt();
		System.out.println("请输入密码：");
		int p;
		while(true) {
			p = sc.nextInt();
			if(p>99999 && p<1000000) {
				break;
			}
			else {
				System.out.println("输入不合法！请输入6位数字密码！");
				System.out.println("请输入密码：");
			}	
		}
		a.passWord = p;
		
		System.out.println("姓名："+a.name);
		System.out.println("编号："+a.bianHao);
		System.out.println("密码："+a.passWord);
		System.out.println("确认无误创建：1/0");
		if(sc.nextInt() ==1) {
			return a;
		}else {return null;}
	}

	//---------------------------------------------------------------------------------------------（2）	
		//2.加入新的会员到数据文件中；
				public static void addFlie(Member_Mod a) {	
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
				                  			
				                  			Member_DT.list.add(a);//将对象加入到列表中；
				                  
				                            oos.writeObject(Member_DT.list);//进行序列化，将列表输出； 
				                            oos.flush();
				                            System.out.println("会员姓名："+a.name+"\t会员编号："+a.bianHao+"\t创建成功！");
				                            
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
		//1.输出会员数据库中所有对象；
		public static void printList() {
			if(Member_DT.member_dt.length() != 0) { //如果文件长度不为0就读取内容；
				try {
					FileInputStream in = new FileInputStream(Member_DT.member_dt); //通过打开一个到实际文件的连接来创建一个 FileInputStream
					ObjectInputStream ois = new ObjectInputStream(in); //创建从指定 InputStream 读取的 ObjectInputStream
							
					while(in.available()>0) { //代表文件还有内容
						Member_DT.list = (ArrayList<Member_Mod>) ois.readObject(); //创建列表接收读取来的对象
					}

					for (int i = 0; i < Member_DT.list.size(); i++) {
						Member_Mod meb = ((ArrayList<Member_Mod>) Member_DT.list).get(i); //获取列表中相应的类型对象	
						System.out.println("会员名称："+meb.name+"\t会员编号："+meb.bianHao+"\t会员密码："+meb.passWord+"\t会员积分："+meb.integral);
						System.out.println("----------------------------------------------------------------------------------------------------");
					}	
					System.out.println("现存会员总数："+Member_DT.list.size()+"人");
					
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
				System.out.println("还没有一个会员哦~");
			}	
		}
	//---------------------------------------------------------------------------------------------（3）	
//		3.根据输入的会员编号查询会员并修改

			public  static void modifyMember() {
				Scanner sc1 = new Scanner(System.in);
				
				biaoji1:while(true) {
					//控制台获取商品信息
					System.out.println("请输入要修改的会员编号：");
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
								int b2 =meb.bianHao;
								if( b2== a) { //查询编号和已有编号相同
									System.out.println("要修改的会员为：");
									System.out.println("会员名称："+meb.name+"\t会员编号："+meb.bianHao+"\t会员密码："+meb.passWord+"\t会员积分："+meb.integral);
									System.out.println("确认修改此会员？--------（是：1）/（否：0）");
									if(sc1.nextInt() == 1) {
										//控制台获取商品信息
										System.out.println("请输入新的会员名：");
										meb.name=sc1.next();
										System.out.println("请输入新的会员编号：");
										meb.bianHao=sc1.nextInt();
										System.out.println("请输入新的会员密码（6位数字）：");
										int p = sc1.nextInt();
										while(true) {
											if(p>99999 && p<1000000) {
												break;
											}
											else {
												System.out.println("输入不合法！请输入6位数字密码！");
												System.out.println("请输入密码：");
											}	
										}
										meb.passWord= p; //密码6位验证
										
										while(true) { //修改信息错误，重新修改
											//确认修改的会员信息
											System.out.println("会员名称："+meb.name);
											System.out.println("会员编号："+meb.bianHao);
											System.out.println("会员密码："+meb.passWord);
											System.out.println("确认修改内容：？--------（是：1）/（否：0）");
											if(sc1.nextInt() == 1) {
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
												System.out.println("会员修改成功！");
												break biaoji1;
											}
											else {
												break biaoji1;    //修改内容不确定，返回主页面
											}
										}	
//										-----------------------------------------------------
									}
									else {
										break biaoji1;    //修改商品不确定，返回主页面
									}
//									--------------------------------------------------------
								}
//								--------------------------------------------------------
						}    //标记2
						System.out.println("要查询的会员不存在！是否重新查询？--------（是：1）/（否：0）");
						if(sc1.nextInt() == 0) {
							break biaoji1;
						}
//						---------
					
//					---------
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
//			4.根据输入的会员编号删除会员

				public  static void delMember() {
					Scanner sc1 = new Scanner(System.in);
					
					biaoji1:while(true) {
						//控制台获取商品信息
						System.out.println("请输入要删除的会员编号：");
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
									int b2 =meb.bianHao;
									if( b2== a) { //查询编号和已有编号相同
										System.out.println("要删除的会员为：");
										System.out.println("会员名称："+meb.name+"\t会员编号："+meb.bianHao+"\t会员密码："+meb.passWord+"\t会员积分："+meb.integral);
										System.out.println("确认删除此会员？--------（是：1）/（否：0）");
										if(sc1.nextInt() == 1) {
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
											    
											    	Member_DT.list.remove(meb); //移除要删除的商品；
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
													System.out.println("会员删除成功！");
													break biaoji1;
												}
												else {
													break biaoji1;    //删除内容不确定，返回主页面
												}
											}		
							}//标记2
//							---------
							System.out.println("要删除的会员不存在！是否重新查询？--------（是：1）/（否：0）");
							if(sc1.nextInt() == 0) {
								break biaoji1;
							}
//						---------
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
				
	
//--------------------------------------------------	
}
