import java.util.Random;
import java.util.Vector;

class MyTank extends Tank{ //因为子弹跟着坦克走 所以子弹的功能大多都和坦克类有关
	Vector<Bullet> vb=new Vector<Bullet>();//多颗子弹
	Bullet bt;
	
	public MyTank(int x,int y){
		super(x,y);
	}
	
	public void shoot() { ////////////////射击方法写在我方坦克类里还是敌方子弹类里还是子弹类里？？？？？？跟着坦克走 不是随意活动的
		switch(fangxiang){
		case 0:
			bt=new Bullet(x+9,y-20,0);
			vb.add(bt);
			break;
		case 1:
			bt=new Bullet(x+9,y+40,1);
			vb.add(bt);
			break;
		case 2:
			bt=new Bullet(x-20,y+9,2);
			vb.add(bt);
			break;
		case 3:
			bt=new Bullet(x+40,y+9,3);
			vb.add(bt);
			break;
		}
		Thread t=new Thread(bt);///////////////////在坦克类里启动线程
		t.start();
	}
}
class OtherTank extends Tank implements Runnable{
	Vector<Bullet> ob=new Vector<Bullet>();
	Bullet bt;
	OtherTank(int x, int y) {
		
		super(x, y);
		
	}
	public void run(){
		while(true){
			if(this.live==false){
				break;
				}
			Random r=new Random();
			fangxiang=r.nextInt(4);
			switch(fangxiang){
			case 0:
				for(int i=0;i<30;i++){
					if(y>10){y--;}else{y=y;}
					try{
						Thread.sleep(50);
					}catch(Exception e){	
					}
				}
				break;
			case 1:
				for(int i=0;i<30;i++){
				if(y<370){y++;}else{y=y;}
					try{
						Thread.sleep(50);
					}catch(Exception e){	
					}
				}
				break;
			case 2:
				for(int i=0;i<30;i++){
					if(x>10){x--;}else{x=x;}
					try{
						Thread.sleep(50);
					}catch(Exception e){	
					}
				}
				break;
			case 3:
				for(int i=0;i<30;i++){
				if(x<480){x++;}else{x=x;}
					try{
						Thread.sleep(50);
					}catch(Exception e){	
					}
				}
				break;
			}
			
			if(live){
			if(ob.size()<5){
			
				switch(this.fangxiang){
				case 0:
					bt=new Bullet(x+9,y-20,0);
					ob.add(bt);
					break;
				case 1:
					bt=new Bullet(x+9,y+40,1);
					ob.add(bt);
					break;
				case 2:
					bt=new Bullet(x-20,y+9,2);
					ob.add(bt);
					break;
				case 3:
					bt=new Bullet(x+40,y+9,3);
					ob.add(bt);
					break;
				}
				Thread x=new Thread(bt);
				x.start();
			}
			}
		}
	}
}
class Tank{  //坦克类
	int x,y;
	int fangxiang;
	int leixing;
	boolean live=true;
	Tank(int x,int y){   //坦克类构造方法
		this.x=x;
		this.y=y;
	}
	public int getFangxiang() {
		return fangxiang;
	}
	public void setFangxiang(int fangxiang) {
		this.fangxiang = fangxiang;
	}
	public int getLeixing() {
		return leixing;
	}
	public void setLeixing(int leixing) {
		this.leixing = leixing;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
}