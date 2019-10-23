# File Transfer Protocol(FTP)
This is repository is created as a part of the project for Computer Networks (CNT5106C) in University of Florida Master's in Computer Science program

# Description
In this project, you will implement a simple version of FTP client/server software. It consists of two programs: ftpclient and ftpserver. First, the ftpserver is started on a computer. It listens on a certain TCP port. Then, the ftpclient is executed on the same or a different computer; the server’s address and port number are supplied in the command line, for example, “ftpclient sand.cise.ufl.edu 5106”. The client will prompt for username and password. After logon, the user can issue three commands at the client side: “dir” is to retrieve the list of file names available at the server, “get <filename>” is to retrieve a file from the server, and “upload < filename>” is to upload a file to the server.
The implementation does not have to conform to the FTP standard. Data and command may use the same TCP connection or different connections. The server should support multiple concurrent clients.
  
# Programming Environment
Java

# How To Run
cd ..ProjectPath/src

Compile: <br/> 
Server ->  javac com/UFL/FTPServer/FtpServer.java 

Client1 -> javac com/UFL/FTPClient/Client1/FtpClient.java 

Client2 -> javac com/UFL/FTPClient/Client2/FtpClient.java



Run: <br/>
Server ->  java com.UFL.FTPServer.FtpServer

Client1 -> java com.UFL.FTPClient.Client1.FtpClient

Client2 -> java com.UFL.FTPClient.Client2.FtpClient

The username and password can be found at users.txt file under /src folder. Each pair of user name and password are mentioned in each line of the users.txt file.


# Test Cases
1) Start the server

2) Start client 1

3) Start client 2

4) Try an invalid command on one of the clients (other than "ftpclient <IP port>", "dir", "get <filename>" and "upload <filename>")

5) Try one of the valid commands "dir", "get <filename>" and "upload <filename>"

6) Try command "ftpclient <IP port>" with wrong IP or port number

7) Command "ftpclient <IP port>" with correct IP and port number

8) Try one of the commands "ftpclient <IP port>", "dir", "get <filename>" and "upload <filename>"

9) Try logging in with the wrong username or password

10) Login with correct username and password

11) Try an invalid command on the client (other than "ftpclient <IP port>", "dir", "get <filename>" and "upload <filename>")

12) Try command "ftpclient <IP port>"

13) Try uploading a file that doesn’t exist

14) Command “upload” for a valid file from client 1 to server

15) Command “dir” from client 2

16) Try “get” wrong file name

17) Command “get” on client 2 for the file that client 1 uploaded to the server

# Special Instruction
Before connecting the client with the server, set the correct path to the variables "sharedPath" and "userList" in FTPServer.java file, else the client won't be able to connect to the server. 
userList is the variable which contains the path of the file that maintains list of username and password.
sharedPath is the variable which contains the path of the folder that is shared by the server to the client and where client can upload the files.
Unfortuenately these paths are hardcoded as per mymachine.
