/*
This code is Audio Receving and Playing
*/
import java.net.* ;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

public class PlayBox extends Hedder{
	
	ByteArrayOutputStream byteArrayOutputStream;
	boolean stopPlay;
	SourceDataLine sourceDataLine;
	byte tempBuffer[] = new byte[500];
	int recivePort;
	DatagramSocket socket;
	DatagramPacket packet;
	private final static int packetsize = 500 ;
//----------------------------------------------------------------------------------------------------------------	

public PlayBox(String port){
		this.stopPlay=false;
		recivePort = Integer.parseInt(port);
		try{
			socket = new DatagramSocket( recivePort ) ;
			packet = new DatagramPacket( new byte[packetsize], packetsize ) ;
		}catch(SocketException e){
			System.out.println(e);
			System.exit(0);
		}
	}
//----------------------------------------------------------------------------------------------------------------
public void startPlay(){
		this.stopPlay=false;
	}
	
	public void stopPlay(){
		this.stopPlay=true;
	}
//----------------------------------------------------------------------------------------------------------------
	public void AudioPlay(){
        try{
        DataLine.Info dataLineInfo1 = new DataLine.Info(SourceDataLine.class, audioFormat);
        sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo1);
        sourceDataLine.open(audioFormat);
        sourceDataLine.start();
        
        //Setting the maximum volume
        FloatControl control = (FloatControl)sourceDataLine.getControl(FloatControl.Type.MASTER_GAIN);
        control.setValue(control.getMaximum());
        }
        catch(LineUnavailableException e){
        System.out.println(e);
			System.exit(0);
        }
    }
//----------------------------------------------------------------------------------------------------------------
	@Override
	public void run(){
		byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			int i = 1;
            int readCount = 500;
            while (!stopPlay) {
					socket.receive( packet );
					
					System.out.println(packet.getData() +" : SerialNo:" + i);
                    byteArrayOutputStream.write(packet.getData(), 0, readCount);
                    sourceDataLine.write(packet.getData(), 0, 500);   //playing audio available in tempBuffer
				i++;
            }
            byteArrayOutputStream.close();
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
	}
//----------------------------------------------------------------------------------------------------------------


}