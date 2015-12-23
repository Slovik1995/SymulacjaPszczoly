package paczka;

import java.util.Random;

class Bee extends Thread{
	Random rand = new Random();
	BeeHive hive;
	int baseTimeOfSomethingElse;
	boolean ChangeTask;
	long start, end;
	private int id;
	private char placement;   // i - inside,  o - outside
	private int timeOfWaiting;
	private int howManyFlights;
	public Bee(int id, char placement, int baseTimeOfSomethingElse, BeeHive b)
	{
		this.id=id;
		this.placement=placement;
		timeOfWaiting=0;
		howManyFlights=0;
		this.baseTimeOfSomethingElse=baseTimeOfSomethingElse;
	
		hive=b;
	}
	
	public void run(){
	/*
		Thread t = new Thread(){
		public void run(){
			while(true){
			try {
				sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("id: "+getID());
		}
		}
	};
	t.start();
	*/
		doSomething();
		while(true){
			if(ChangeTask==true)
			{
				doSomething();
				ChangeTask=false;
			} else
				try {
					this.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
	}
	public void changeTask(){
		ChangeTask=true;
	}
	public int getID(){
		return id;
	}
	public void setPlacement(char c){
		placement = c;
	}
	public char getPlacement(){
		return placement;
	}
	public void changePlacement(){
		if(placement=='o')
			placement='i';
		else placement='o';
	}
	public void setTime(int time){
		timeOfWaiting = time;
	}
	public int getTime(){
		return timeOfWaiting;
	}
	public void addFlight(){
		howManyFlights++;
	}
	public int getFlights(){
		return howManyFlights;
	}
	public void doSomething(){
		try {
		//	System.out.println(">>>>>>>>  "+getID());
			//System.out.println(baseTimeOfSomethingElse);
			this.sleep(Math.abs((rand.nextInt()%baseTimeOfSomethingElse))+1100);
	//		System.out.println("<<<<<<<<  "+getID());
			findHole();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String calculateTime(){
		if(end<start) return "";
		long diff = end - start;
		long h=0,s=0,m=0;
		if (diff >= 60000)
			{
			h=diff/60000;
			diff = diff%60000;
			}
		if(diff>=1000)
			{
			s=diff/1000;
			diff=diff%1000;
			}
		if(diff>0)
			{
			m=diff;
			}
		return ""+h+":"+s+":"+m;
		
	}
	
	private void findHole() throws InterruptedException{
		
		if(getPlacement()=='o')
		{
			
			if(chooseHole()==1){
				WriteToFile.write("Pszczo³a "+getID()+" podlatuje pod przelot 1");
				System.out.println("Pszczo³a "+getID()+" podlatuje pod przelot 1");	
				if(!hive.out1.isEmpty())
					if(flip()==true)
					{
	
						WriteToFile.write("Pszczo³a "+getID()+" przelot zajêty, sprawdza przelot 2");
						System.out.println("Pszczo³a "+getID()+" przelot zajêty, sprawdza przelot 2");
						start = System.currentTimeMillis( );
						hive.out2.put(this);			
					return;
					}
				start = System.currentTimeMillis( );
				hive.out1.put(this);
					
			}
			else
			{
				WriteToFile.write("Pszczo³a "+getID()+" podlatuje pod przelot 2");
				System.out.println("Pszczo³a "+getID()+" podlatuje pod przelot 2");	
				if(!hive.out2.isEmpty())
					if(flip()==true)
					{
						WriteToFile.write("Pszczo³a "+getID()+" przelot zajêty, sprawdza przelot 1");
						System.out.println("Pszczo³a "+getID()+" przelot zajêty, sprawdza przelot 1");
						start = System.currentTimeMillis( );
						hive.out1.put(this);
					return;
					}
				start = System.currentTimeMillis( );
				hive.out2.put(this);
			}
		}
		else
		{
			if(chooseHole()==1){
				WriteToFile.write("Pszczo³a "+getID()+" podlatuje pod przelot 1");
				System.out.println("Pszczo³a "+getID()+" podlatuje pod przelot 1");	
				if(!hive.in1.isEmpty())
					if(flip()==true)
					{
						WriteToFile.write("Pszczo³a "+getID()+" przelot zajêty, sprawdza przelot 2");
						System.out.println("Pszczo³a "+getID()+" przelot zajêty, sprawdza przelot 2");
						start = System.currentTimeMillis( );
						hive.in2.put(this);
					return;
					}
				start = System.currentTimeMillis( );
				hive.in1.put(this);
			}
			else
			{
				WriteToFile.write("Pszczo³a "+getID()+" podlatuje pod przelot 2");
				System.out.println("Pszczo³a "+getID()+" podlatuje pod przelot 2");
				if(!hive.in2.isEmpty())
					if(flip()==true)
					{
						WriteToFile.write("Pszczo³a "+getID()+" przelot zajêty, sprawdza przelot 1");
						System.out.println("Pszczo³a "+getID()+" przelot zajêty, sprawdza przelot 1");
						start = System.currentTimeMillis( );
						hive.in1.put(this);
					return;
					}
				start = System.currentTimeMillis( );
				hive.in2.put(this);
			}	
		}
	}
	private int chooseHole(){
		int wynik = rand.nextInt()%2;
		if(wynik==0) return 1;
		else return 2;
	}
	
	private boolean flip(){
		if(rand.nextInt()%2 == 0) return true;
		return false;
	}	
	
}


