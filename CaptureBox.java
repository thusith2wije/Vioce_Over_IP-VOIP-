/*
This code is Audio capturing 
*/
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.DataLine;
import java.net.* ;
 
public class CaptureBox extends Hedder{

	TargetDataLine targetDataLine; //initializing
	byte tempBuffer[] = new byte[500];
	boolean stopCapture;
	public static DatagramSocket socket;
	DatagramPacket packet;
	InetAddress host;
	int port;
	static int i = 1;
	int j=1;
//------------------------------------------------------------------------------------------------------------------------
public  CaptureBox(String ip, String Hport){ 
		try{
			this.stopCapture=false;
			host = InetAddress.getByName(ip);
			port = Integer.parseInt(Hport);
		}catch(Exception e){
			System.out.println(e);
			System.exit(0);
		}
	}
//---------------------------------------------------------------------------------------------------------------------
public void AudioRecord(){// Get Audio packets
        try{
        //audioFormat = getAudioFormat();     //get the audio format
        DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);

        targetDataLine = (TargetDataLine) mixer.getLine(dataLineInfo);
        targetDataLine.open(audioFormat);
        targetDataLine.start();
        }
        catch(LineUnavailableException e){
            System.out.println(e);
			System.exit(0);
        }
    }
 //------------------------------------------------------------------------------------------------------------------------
	public void startCapture(){
		this.stopCapture=false;
	}
	
	public void stopCapture(){
		this.stopCapture=true;
	}
 //------------------------------------------------------------------------------------------------------------------------
public void sendAudio(byte[] data){	//Sending Packets to recevr through a Socket
		
		try{
			socket = new DatagramSocket() ;
			Serial(j);
			packet = new DatagramPacket(data, data.length, host, port);
			socket.send( packet );
		}catch(IOException e){
			System.out.println(e);
			System.exit(0);
		}
	}
//------------------------------------------------------------------------------------------------------------------------
	public int Serial(int i){// Audio Packet serializing
		
		return i++;
	}
 //------------------------------------------------------------------------------------------------------------------------
 @Override
	public void run (){
		try {
            int readCount;
            while (!this.stopCapture) {
				readCount = targetDataLine.read(tempBuffer, 0, tempBuffer.length);  //capture sound into 
				sendAudio(tempBuffer);
            }
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }
	}
}