class Bullet implements Runnable{  
	int x;
	int y;
	int fangxiang;
	boolean live=true;
	Bullet(int x,int y,int fangxiang){
		this.x=x;
		this.y=y;
		this.fangxiang=fangxiang;
	}
	public void run(){
		while(true){  //凡是线程里的死循环都要加个睡眠 以免程序崩溃
			try{
				Thread.sleep(50);
			}
			catch(Exception e){
			}
			switch(fangxiang){
			case 0:
				y-=10;
				break; 
			case 1:
				y+=10;
				break;
			case 2:
				x-=10;
				break;
			case 3:
				x+=10;
				break;
			}
			if(x<0||x>500||y<0||y>400){//////////////////为何写在线程里
				this.live=false;
				break;
			}
		}
	}
}
