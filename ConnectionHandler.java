import java.io.*;
import java.net.Socket;

import static java.awt.SystemColor.info;

/**
 *
 * @author Viola Federico
 * 
 * @category Academic Project / Web Server
 * @version 1.0
 */

public class ConnectionHandler extends Thread {

    Socket s;
    public DataOutputStream out;
    BufferedReader br;

    public ConnectionHandler(Socket s) throws IOException {
        this.s = s;
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        out = new DataOutputStream(s.getOutputStream());
    }

    @Override
    public void run() {
        try {
            String reqS = "";
            while (br.ready() || reqS.length()==0 )
                reqS += (char) br.read();
            System.out.println(reqS);
            HttpRequest req = new HttpRequest(reqS);
            HttpResponse res = new HttpResponse(req);
            if(res.info.equals("string")) out.writeBytes(res.response);
            if(res.info.equals("byte")) {
                out.writeBytes(res.header("200","image/png",res.byteArray.length));
                out.write(res.byteArray);
            }
            out.close();
            br.close();
            s.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
