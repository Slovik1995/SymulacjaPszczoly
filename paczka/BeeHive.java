package paczka;


import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

public class BeeHive {
	Hole Hole1;
	Hole Hole2;
	static BeeQueue in1;
	static BeeQueue in2;
	static BeeQueue out1;
	static BeeQueue out2;
	
	public BeeHive(int bees, int time){
		Hole1 = new Hole(time,1);
		Hole2 = new Hole(time,2);
		in1 = new BeeQueue(bees, Hole1);
		in2 = new BeeQueue(bees, Hole2);
		out1 = new BeeQueue(bees, Hole1);
		out2 = new BeeQueue(bees, Hole2);
		
		in1.run();
		in2.run();
		out1.run();
		out2.run();
		
	}
	public static void summary(){
		WriteToFile.write("============================================================");
		String s="";
		for(Bee bee : BeeGenerator.list){
			s="Pszczo³a "+bee.getID()+" wykona³a "+bee.getFlights();
			if((bee.getFlights()>4)||(bee.getFlights()==0))
				s+=" przelotów";
			else if(bee.getFlights()>1)
				s+=" przeloty";
			else s+=" przelot";
			WriteToFile.write(s); 	
		}
	}
}

class Hole{
	private int timeOfFlightThroughTheHole;
	private int id;
	public int getID(){
		return id;
	}
	public Hole(int time, int id){
		timeOfFlightThroughTheHole=time;
		this.id=id;
	}
	public int howLong(){
		return timeOfFlightThroughTheHole;
	}
}

class BeeQueue extends ArrayBlockingQueue<Bee>implements Runnable{
	Hole hole;
	Random rand = new Random();
	public BeeQueue(int bees, Hole h){
		super(bees);
		hole=h;
	}
	private void go(){
		Bee bee;
		synchronized(this){
			bee = this.poll();
			bee.end = System.currentTimeMillis( );
			bee.addFlight();
			if(bee.getPlacement()=='o')
				{
				WriteToFile.write("Pszczo³a " + bee.getID() + " wlatuje przez przelot "+ hole.getID()+", czas oczekiwania    "+bee.calculateTime());
				System.out.println("Pszczo³a " + bee.getID() + " wlatuje przez przelot "+ hole.getID());
				}
			else{
				WriteToFile.write("Pszczo³a " + bee.getID() + " wylatuje przez przelot "+ hole.getID()+", czas oczekiwania    "+bee.calculateTime());
				System.out.println("Pszczo³a " + bee.getID() + " wylatuje przez przelot "+ hole.getID());
			}
			bee.changePlacement();
			try {
				this.wait(hole.howLong()*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	//	System.out.println(">> " + bee.getID());
		bee.changeTask();
	//	System.out.println("<< " + bee.getID());
		
	}
	
	public void run(){
		Thread t = new Thread(){ 
		public void run(){
			while(true){
	/*		if(isEmpty()){
				try {
					synchronized(this){
					this.wait(50);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}   */
				if(!isEmpty()){
					synchronized(hole){
					go();
					}
				
		/*		try {
					synchronized(this){
					this.wait(Math.abs(rand.nextInt(40)+10));
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		*/		}
		}
		}
		};
		t.start();
		
	}
}

class BeeGenerator{
	static ArrayList<Bee> list = new ArrayList<>(20);
	public static void generateBees(int bees, int time, BeeHive b){
		char where;
		
		Random random = new Random();
		for(int i=1; i<=bees; i++){
			where = ((random.nextInt()%2)==0) ? 'o' : 'i'; 
			Bee bee = new Bee(i, where, time, b);
			list.add(bee);
			bee.start();
		}
	}
}
