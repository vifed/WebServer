# WebServer
Developing of a small web server with Java that is capable of serving static content.
The program was made on Arch Linux os tested with the Web browser – Mozilla Firefox and NetBeans as IDE.

This is an academic project that have several restriction, one of this is that I can't use the API's of Java.

First of all I get the request from browser in 'request' class and after processing this request I passed that to the 'response' class that generate the output which is given to the browser back.

In Main.java, I declared a ServerSocket which will create the server at specific port number, and accept all the requests inside the main function.

The ConnectionHandler.java class is made to handle connection.
This class basically handles all the connection which contains the requests.
I used DataOutputStream for sending the output to client and BufferedReader for
getting the input from client.

In HttpRequest.java class, the constructor accepts a string that contains 3 parts:
1. Request type;
2. File name;
3. HTTP version;
According the task, I focused my attention only in the file name that is important for the header.

The HttpResponse.java class,  has a constructor which gets HttpRequest request as a parameter. It checks extension of requested file and calls corresponding function. If extension is directory then it searches for “index.html” file in that directory. If that file is found then it returns “index.html” as a response, else it searches for “index.htm”.

The htmlResponse() function gives response when requested file has “.html” or “.htm” extension. 
First it checks if requested file is accessible, because there might be files to which client has no access. If it is forbidden file, function gives “Error 403. Access Denied” as a response, else if given filename in given directory is not found it gives “Error 404, File Not Found” as a response. If there is an unexpected error it gives “Error 500, Unexpected Error.
If an error occurred and your request couldn’t be completed. Please try again.” as a response. It is checked with try/catch. In “try” program reads the file and gives it as a response. But before it adds a header to the response.

This is the first version of the program.
