import java.io.*;
import java.net.Socket;

/**
 *
 * @author Viola Federico
 * 
 * @category Academic Project / Web Server
 * @version 1.0
 */

public class HttpResponse  {

    HttpRequest req;
    String response;
    String root = "/home/federico/Scrivania/LNU/root/dir1;"; //change the path in basis of your organization of the folders
    public String info="";
    public byte[] byteArray;
    String myroot="", myfilename="";

    HttpResponse(HttpRequest request) throws IOException {
        req = request;
        String ext = checkExtension();
        if(ext.equals("html") || ext.equals("htm")){
            info = "string";
            htmlResponse();
        }
        else if(ext.equals("png"))
        {
            info = "byte";
            byteArray=pngResponse();
        }
        else{
            info = "string";
            boolean exists = checkFileExists(req.filename , "index.html");
            if(exists) directoryResponse(req.filename , "index.html");
            else directoryResponse(req.filename,"index.htm");
        }
    }
    public static boolean checkFileExists(String directory,String fileName){
        File dir = new File(directory);
        File[] dir_contents = dir.listFiles();
        String temp = fileName;
        boolean check = new File(directory,temp).exists();
        return check;
    }
    public String checkExtension(){
        String filename = req.filename;
        String extension;
        int size = filename.length();
        int index =0, slash=0, s;
        Boolean ext;
        if (filename.charAt(size-5) == '.'){
            extension = filename.substring(size-4);
            ext = true;
            index = 4;
        }
        else if (filename.charAt(size-4) == '.') {
            extension = filename.substring(size-3);
            ext = true;
            index = 3;
        }
        else {
            extension = filename;
            ext = false;
        }
        if(ext){
            slash=0;
            s = req.filename.length()-index-1;
            while(s >= 0){
                if(req.filename.charAt(s) == '/')slash++;;
                s--;
            }
        }
        else myroot=root;

        if(slash > 1){
            myroot="";
            s = filename.lastIndexOf('/');
            myroot = filename.substring(0,s+1);
            myfilename = filename.substring(s+1);
        }
        else {
            myroot=root;
            myfilename = filename;
        }

        return extension;
    }
    public String header(String status_code ,String content_type,long content_length){
        String headerString = "";
        headerString = "HTTP/1.1" + status_code + "\r\n";
        headerString += "Server: Our Java Server/1.0 \r\n";
        headerString += "Content-Type: " + content_type + "\r\n";
        headerString += "Connection: close \r\n";
        headerString += "Content-Length: " + content_length + " \r\n";
        headerString+= "\r\n";
        return headerString;
    }
    public void htmlResponse() throws IOException {

        File f = new File(myroot + myfilename);
        if(myfilename.equals("forbidden.html") && myroot.equals("/home/federico/Scrivania/LNU/root/pages/")){ //change the path in basis of your organization of the folders
            response += header("403", "text/html", f.length());
            response = "Error 403. Access Denied";
        }else {
            try {
                response += header("200", "text/html", f.length());
                FileInputStream fis = new FileInputStream(f);
                int s;
                while ((s = fis.read()) != -1) {//-1 means end of file
                    response += (char) s;
                }
                fis.close();
            } catch (FileNotFoundException e) {
                response = "";
                response += header("404", "text/html", f.length());
                response = "Error 404 , File Not Found.";
            } catch (Exception e) {
                response = "";
                response += header("500", "text/html", f.length());
                response = "Error 500 , Unexpected Error. An error occurred/n" +
                        "and your request couldn't be completed.Please try again.";
            }
        }
    }
    public void directoryResponse(String filename , String newMyFileName) throws IOException {
        int size;
        size = filename.length();
        if(filename.charAt(size-1)!='/') filename+='/';
        this.root = filename;
        myfilename=newMyFileName;
        myroot=filename;
        htmlResponse();

    }
    public byte[] pngResponse() throws IOException {
        String fileName = myroot + myfilename;
        byte[] data=(new String("")).getBytes();
        String dir="/home/federico/Scrivania/LNU/root/images/"; //change the path in basis of your organization of the folders
        if(myfilename.equals("forbidden.png") && myroot.equals(dir)){
            myroot = dir;
            myfilename = "error403.png";
            return pngResponse();
        }else {
            try {
                File file = new File(fileName);
                FileInputStream fis = new FileInputStream(file);
                data = new byte[(int) file.length()];

                fis.read(data);
                fis.close();
            } catch (FileNotFoundException e) {
                myroot = dir;
                myfilename = "error404.png";
                return pngResponse();
            } catch (Exception e) {
                myroot = dir;
                myfilename = "error500.png";
                return pngResponse();
            }
            return data;
        }
    }

}



