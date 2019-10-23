package com.UFL.FTPServer;

import java.net.*;
import java.io.*;
import java.util.*;


public class FtpServer {
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(4000);
        System.out.println("FTP Server Started on Port Number 4000");
        System.out.println("The server is running.");
        int clientNum = 0;
        while (true) {
//            System.out.println("Waiting for Connection ...");
            ++clientNum;
            CNFTPServerRun cnftpServerRun =
                    new CNFTPServerRun(server.accept(), clientNum);
        }
    }
}

class CNFTPServerRun extends Thread {
    Socket clientSocket;
    DataInputStream inputStream;
    DataOutputStream outputStream;
    int clientNum;
    String clientName;
    String sharedPath = "/Users/shantanughosh/Desktop/Shantanu_MS/Fall 19/CN/Projects/FTP_Project/File_Transfer_Protocol/FTPClientServer/src/Test Folder/Server";

    CNFTPServerRun(Socket soc, int _clientNum) {
        try {
            clientSocket = soc;
            clientNum = _clientNum;
            inputStream = new DataInputStream(clientSocket.getInputStream());
            outputStream = new DataOutputStream(clientSocket.getOutputStream());
//            System.out.println("Client " + _clientNum +
//                    " connected ...");

            start();

        } catch (Exception ex) {
        }
    }

    public void run() {
        authenticateClient();
        try {
            while (true) {
                try {
                    String messageFromClient = inputStream.readUTF();
                    switch (messageFromClient) {
                        case "get":
                            sendFileToClient();
                            continue;
                        case "upload":
                            getFileFromClient();
                            continue;
                        case "dir":
                            browseDir();
                            continue;
                        case "exit":
                            // System.exit(1);
                            System.out.println(clientName + " exits");
                            continue;
                    }
                } catch (ClassNotFoundException classnot) {
                    System.err.println("Data received in unknown format");
                }
            }

            //System.out.println("Total clients connected now:" + clientNum);
        } catch (IOException ioException) {
            System.out.println("Disconnect with Client " + clientName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //Close connections
            try {
                inputStream.close();
                outputStream.close();
                clientSocket.close();
            } catch (IOException ioException) {
                System.out.println("Disconnect with Client " + clientName);
            }
        }
    }

    private void authenticateClient() {
        while (true) {
            try {
                String userName = inputStream.readUTF().trim();
                String password = inputStream.readUTF();
                File userList =
                        new File("/Users/shantanughosh/Desktop/Shantanu_MS/Fall 19/CN/Projects/FTP_Project/File_Transfer_Protocol/FTPClientServer/src/users.txt");
                FileInputStream inp = new FileInputStream(userList);
                DataInputStream dis = new DataInputStream(inp);
                String lines;
                boolean isFound = false;
                lines = dis.readLine();
                while (true) {
                    if (lines == null) break;
                    String pw = dis.readLine();
                    if (lines.equals(userName) && pw.equals(password)) {
                        isFound = true;
                        break;
                    }
                    lines = dis.readLine();
                }
                if (isFound) {
                    clientName = userName;
                    String loginMessage = clientName +
                            " logged in successfully";
                    outputStream.writeUTF("Success");
                    System.out.println(loginMessage);
                    break;
                } else {
                    outputStream.writeUTF("Login failed");
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    private void browseDir() throws Exception {
        File folder = new File(sharedPath);
        ArrayList<String> files = new ArrayList<String>();
        ArrayList<String> directories = new ArrayList<String>();
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                files.add(file.getName());
            } else if (file.isDirectory()) {
                directories.add(file.getName());
            }
        }

        StringBuilder msgToClient = new StringBuilder();
        msgToClient.append("Directories:\n");
        msgToClient.append("-----------------------\n");
        for (int i = 0; i < directories.size(); i++) {
            msgToClient.append("" + (i + 1) + " : " + directories.get(i) + "\n");
        }
        msgToClient.append("\n\nFiles\n");
        msgToClient.append("-----------------------\n");
        for (int i = 0; i < files.size(); i++) {
            msgToClient.append("" + (i + 1) + " : " + files.get(i) + "\n");
        }
        outputStream.writeUTF(msgToClient.toString());
        return;
    }

    private void getFileFromClient() throws Exception {
        String fileFromClient = inputStream.readUTF();
        if (fileFromClient.compareTo("File not found") == 0) {
            return;
        }
        File fileToSave = new File(sharedPath + "/" +
                fileFromClient);
        String option;

        if (fileToSave.exists()) {
            outputStream.writeUTF("File already exists");
            option = inputStream.readUTF();
        } else {
            outputStream.writeUTF("SendFile");
            option = "Y";
        }

        if (option.compareTo("Y") == 0) {
            writeFile(fileToSave);
        } else {
            return;
        }
    }

    private void writeFile(File fileToSave) throws Exception {
        FileOutputStream fileOutputStream = new FileOutputStream(fileToSave);
        int ch;
        do {
            ch = Integer.parseInt(inputStream.readUTF());
            if (ch != -1) {
                fileOutputStream.write(ch);
            }
        } while (ch != -1);
        fileOutputStream.close();
        String responseFromServer = "File received at server successfully"
                + " for client " + clientName;
        System.out.println(responseFromServer);
        outputStream.writeUTF(responseFromServer);
    }

    private void sendFileToClient() throws Exception {
        File fileToSend = new File(sharedPath + "/" +
                inputStream.readUTF());
        if (!fileToSend.exists()) {
            outputStream.writeUTF("File Not Found");
            return;
        } else {
            outputStream.writeUTF("READY");
            System.out.println("Uploading file...");
            readfile(fileToSend);
            System.out.println("File sent from server for client " + clientName);
            outputStream.writeUTF("File received successfully");
        }
    }

    private void readfile(File file) throws Exception {
        FileInputStream fin = new FileInputStream(file);
        int ch;
        do {
            ch = fin.read();
            outputStream.writeUTF(String.valueOf(ch));
        }
        while (ch != -1);
        fin.close();
    }
}
