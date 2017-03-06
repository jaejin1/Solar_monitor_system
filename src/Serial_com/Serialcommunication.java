package Serial_com;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import Com_agent.HelpAgent;

public class Serialcommunication{
    public Serialcommunication() {
        super();
    }
    private void connect(String portName) throws Exception {
        System.out.printf("Port : %s\n", portName);
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if (portIdentifier.isCurrentlyOwned()) {
            System.out.println("Error: Port is currently in use");
        } else {
            CommPort commPort = portIdentifier.open(this.getClass().getName(),
                    2000);
            if (commPort instanceof SerialPort) {
                SerialPort serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(115200,
                        SerialPort.DATABITS_8,
                        SerialPort.STOPBITS_1,
                        SerialPort.PARITY_NONE);
            InputStream in = serialPort.getInputStream();
            OutputStream out = serialPort.getOutputStream();
                    (new Thread(new SerialWriter(out))).start();    
                    (new Thread(new SerialReader(in))).start();

            } else {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }
    }
    public static class SerialReader implements Runnable {
        InputStream in;
        public SerialReader(InputStream in) {
            this.in = in;
        }
        public void run() {

            byte[] buffer= new byte[1024];

            int len = -1;
            int a = 1;
            String pr = "";

            try {

               while ((len = this.in.read(buffer)) > -1 ) {
            	  
                     if(len>1){
                        pr = new String(buffer, 0, len);
                        }
                        HelpAgent db = new HelpAgent(pr,a);
                        db.dbconnection();
                        	a++;
                        	if(a>2){
                        		a = 1;
                        	}
                        	if(a == 1){                
                        		System.out.println(pr);
                        	}
                     	   

                 }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public static class SerialWriter implements Runnable {

        OutputStream out;
        public SerialWriter(OutputStream out) {
            this.out = out;
        }

        public void run() {
            try {
               int c =0;
                int[] in = {35,49,78,81,49,49,48,48,48,48,36};  //입력값 "#1NQ110000$"
               
               /* System.out.println("\nKeyborad Input Read!!!!");
                while ((c = System.in.read()) > -1) {
                    this.out.write(c);
                }*/
                while(true){
               	for(int i=0; i<11; i++){
               		this.out.write(in[i]);
               		}
               	Thread.sleep(30000);  //30초에 한번 입력 
               	
                }
                	
            } catch (IOException | InterruptedException  e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
    try {
            (new Serialcommunication()).connect("/dev/ttyUSB0");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}