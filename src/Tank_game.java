import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

import javax.swing.*;


public class Tank_game extends JFrame{
	Mymb mb;
	Tank_game(){  //主函数的构造方法
		mb=new Mymb();
		this.add(mb);
		this.addKeyListener(mb);
		Thread t=new Thread(mb);//////////////////////////
		t.start();///////////////////////////////
		this.setSize(500,420);
		this.setLocation(300,300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	public static void main(String[] args){  //主函数
		Tank_game tg=new Tank_game();
	}
}
class Mymb extends JPanel implements KeyListener,Runnable{//////////////////////面板为何也要是线程
	MyTank mt;
	int Other_Tank_Num=5;
	Vector<OtherTank> vot=new Vector<OtherTank>();  //矢量 线程同步
	public Mymb(){    //面板的构造方法
		mt=new MyTank(240,240);//添加我方坦克   初始值
		for(int i=0;i<Other_Tank_Num;i++){  //添加敌方坦克
			OtherTank ot=new OtherTank(i*120,0);
			vot.add(ot);
			Thread d=new Thread(ot);
			d.start();
		}
	}
	public void paint(Graphics g){
		g.fillRect(0, 0, 500, 400);	
		this.drawtank(mt.getX(), mt.getY(), this.mt.fangxiang, 0, g);//画我方坦克
		for(int i=0;i<vot.size();i++){ //画敌方坦克
			if(vot.get(i).live){
				this.drawtank(vot.get(i).getX(), vot.get(i).getY(), vot.get(i).fangxiang, 1, g);
			}
			if(vot.get(i).live==false){///////////////会判断到吗？？？？
				vot.remove(vot);
			}
		}
		for(int i=0;i<mt.vb.size();i++){ //画子弹
			Bullet bt=mt.vb.get(i);//////////////////mytank里也new了不会没联系么
			if(bt!=null&&bt.live==true){///////////////////////////////////mt.bt???
				g.setColor(Color.WHITE);   
				g.fill3DRect(bt.x,bt.y,3,3,false);///////////////为什么写mt.bt.x  mt.bt.y
			}
			if(bt.live==false){///////////////会判断到吗？？？？
				mt.vb.remove(bt);
			}
		}
		for(int x=0;x<vot.size();x++){//画敌方子弹
			for(int y=0;y<vot.get(x).ob.size();y++){
				Bullet bt=vot.get(x).ob.get(y);
				if(bt!=null&&bt.live==true){
					g.setColor(Color.GREEN);   
					g.fill3DRect(bt.x,bt.y,3,3,false);
				}
				if(bt.live==false){
					vot.get(x).ob.remove(bt);
				}
			}
		}
	}
	public void drawtank(int x,int y,int fangxiang,int leixing,Graphics g){
		switch(leixing){
		case 0:
			g.setColor(Color.YELLOW);//我方
			break;
		case 1:
			g.setColor(Color.GREEN);//敌方
			break;
		}
		switch(fangxiang){
		case 0://上
			g.fill3DRect(x, y, 5, 25, false);
			g.fill3DRect(x+15, y, 5, 25, false);
			g.fill3DRect(x+5, y+5, 10, 15, false);
			g.drawLine(x+10, y+10, x+10, y-5);
			g.fillOval(x+5, y+7, 8, 8);
			break;
		case 1://下
			g.fill3DRect(x, y, 5, 25, false);
			g.fill3DRect(x+15, y, 5, 25, false);
			g.fill3DRect(x+5, y+5, 10, 15, false);
			g.drawLine(x+10, y+10, x+10, y+30);
			g.fillOval(x+5, y+7, 8, 8);
			break;
		case 2://左
			g.fill3DRect(x, y, 25, 5, false);
			g.fill3DRect(x, y+15, 25, 5, false);
			g.fill3DRect(x+5, y+5, 15, 10, false);
			g.drawLine(x-5, y+10, x+10, y+10);
			g.fillOval(x+7, y+5, 8, 8);
			break;
		case 3://右
			g.fill3DRect(x, y, 25, 5, false);
			g.fill3DRect(x, y+15, 25, 5, false);
			g.fill3DRect(x+5, y+5, 15, 10, false);
			g.drawLine(x+10, y+10, x+30, y+10);
			g.fillOval(x+7, y+5, 8, 8);
			break;
		}
	}
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_UP){
			this.mt.setFangxiang(0);
			if(mt.y>10){this.mt.y-=10;}else{mt.y=mt.y;}
		}
		else if(e.getKeyCode()==KeyEvent.VK_DOWN){
			this.mt.setFangxiang(1);
			if(mt.y<370){this.mt.y+=10;}else{mt.y=mt.y;}
		}
		else if(e.getKeyCode()==KeyEvent.VK_LEFT){
			this.mt.setFangxiang(2);
			if(mt.x>10){this.mt.x-=10;}else{mt.x=mt.x;}
		}
		else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
			this.mt.setFangxiang(3);
			if(mt.x<470){this.mt.x+=10;}else{mt.x=mt.x;}
		}
		if(e.getKeyCode()==KeyEvent.VK_SPACE){    //子弹用空格控制
			if(mt.vb.size()<8){
				this.mt.shoot();
				}  //调用射击方法
		}
		this.repaint();
	}
	public void run(){         ////////////////////////////////////
		while(true){
			try{
				Thread.sleep(100);
			}catch(Exception e){
			}
			for(int i=0;i<mt.vb.size();i++){
				Bullet bt=mt.vb.get(i);
				if(bt.live){
					for(int j=0;j<vot.size();j++){
						OtherTank ot=vot.get(j);
						if(ot.live){
							this.target(bt,ot);
						}
					}
				}
			}
			this.repaint();
		}
	}
	private void target(Bullet bt, OtherTank ot) {
		switch(ot.fangxiang){
		case 0:
		case 1:
			if(bt.x>ot.x&&bt.x<ot.x+30&&bt.y>ot.y&&bt.y<ot.y+30){
				bt.live=false;
				ot.live=false;
				}
		case 2:
		case 3:
			if(bt.x>ot.x&&bt.x<ot.x+30&&bt.y>ot.y&&bt.y<ot.y+30){
				bt.live=false;
				ot.live=false;
				}
		}
	}

	public void keyReleased(KeyEvent e) {
		
	}
	
	public void keyTyped(KeyEvent e) {
		
		
	}
}
