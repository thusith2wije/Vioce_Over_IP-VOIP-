/*
	This is the Main method that hve to run.
*/
import java.net.* ;

public class Main {
	/*
	
	*/
	
	public static void main(String[] args){	
		
		if( args.length != 3 ){
			System.out.println( "server port, peer ip port" ) ;//Must give this variables in User command line	
			return ;
		}
		CaptureBox cp = new CaptureBox(args[1],args[2]);
		PlayBox pl = new PlayBox(args[0]);
		
		//CaptureBox cp = new CaptureBox();
		//PlayBox pl = new PlayBox();
	
		cp.AudioRecord();
		cp.start();
		pl.AudioPlay();
		pl.start();
		
	}
	

}