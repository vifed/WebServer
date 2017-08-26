/**
 *
 * @author Viola Federico
 * 
 * @category Academic Project / Web Server
 * @version 1.0
 */

public class HttpRequest {

    public String filename;

    public HttpRequest(String request){
        String lines[] = request.split("\n");
        filename = lines[0].split(" ")[1];
    }
}
