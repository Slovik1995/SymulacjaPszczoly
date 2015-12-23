package paczka;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToFile {
	static String path;
	static File f;
	static FileWriter w;
	public static void initialize(int time){
		  path = System.getProperty("user.home");
		  path+="\\Desktop\\pszczoly000.txt";
		  f = new File(path);
		  try {
			w = new FileWriter(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  Thread t = new Thread(){
			  public void run(){
				 try {
					sleep(time*1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			  
				  try {
					BeeHive.summary();  
					w.flush();
					w.close();
					System.exit(0);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			  }
		  };
		  t.start();
	}
	
  public static void write(String s){
	  try {
		w.write(s);
		w.write(System.getProperty( "line.separator" ));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
	
}
