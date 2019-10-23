# File_Transfer_Protocol
This is repository is created as a part of the project for Computer Networks (CNT5106C) in University of Florida Master's in Computer Science program

# Description
In this project, you will implement a simple version of FTP client/server software. It consists of two programs: ftpclient and ftpserver. First, the ftpserver is started on a computer. It listens on a certain TCP port. Then, the ftpclient is executed on the same or a different computer; the server’s address and port number are supplied in the command line, for example, “ftpclient sand.cise.ufl.edu 5106”. The client will prompt for username and password. After logon, the user can issue three commands at the client side: “dir” is to retrieve the list of file names available at the server, “get <filename>” is to retrieve a file from the server, and “upload < filename>” is to upload a file to the server.
The implementation does not have to conform to the FTP standard. Data and command may use the same TCP connection or different connections. The server should support multiple concurrent clients.
  
# Programming Environment
Java

# How To Run
cd <ProjectPath>/src

 # Compile:
——————————————
javac com/UFL/FTPServer/FtpServer.java 

javac com/UFL/FTPClient/Client1/FtpClient.java 

javac com/UFL/FTPClient/Client2/FtpClient.java



Run:
———————————————
(Server) java com.UFL.FTPServer.FtpServer

java com.UFL.FTPClient.Client1.FtpClient


java com.UFL.FTPClient.Client2.FtpClient
