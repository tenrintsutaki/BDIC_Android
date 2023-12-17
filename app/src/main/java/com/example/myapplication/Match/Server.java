package com.example.myapplication.Match;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author ALiosha
 */

public class Server {
    private int port = 8081;
    private int clientNum = 0;
    private int currentID = 0;
    private HashMap<Integer,ClientModel> clientModels = new HashMap<>();
    private HashMap<Integer,ClientHandler> clientHandlers = new HashMap<>();
    private HashMap<Integer,Integer> matchPairs = new HashMap<>();

    public void startServer(){
        try {
            ServerSocket serverSocket = new ServerSocket(this.port,1000);
//            Client client = new Client(new double[]{114.514,1919.810},"田所浩二");
//            client.startMatch();
//
//            Client client2 = new Client(new double[]{114.514,1919.810},"丰臣秀吉");
//            client2.startMatch();

            System.out.println("Server is active");
            while(true) {
                Socket socket = serverSocket.accept();//监听是否有客户端的连接，如果有，返回socket对象

                System.out.println("New user connected"+socket.getInetAddress().getHostAddress()+" : "+socket.getPort());

                currentID += 1;//Update the ID and Num
                clientNum += 1;

                ClientHandler clientHandler  = new ClientHandler(socket,this,currentID);//Create a handler

                clientHandlers.put(currentID,clientHandler);

                clientHandler.start();//启动子线程

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<Integer,ClientModel> getClientModels(){
        return this.clientModels;
    }

    public void delModel(ClientModel clientModel){
        this.clientModels.remove(clientModel);
    }

    public void delHandler(ClientHandler clientHandler){this.clientHandlers.remove(clientHandler);}

    public int getClientNum(){
        return this.clientNum;
    }

    public void broadcast(String message){

        Iterator<ClientHandler> iterator = clientHandlers.values().iterator();
        while (iterator.hasNext()){//Send this message to all the clients
            ClientHandler handler = iterator.next();
            Socket socket_current = handler.getSocket();
            try {
                PrintStream printStream = new PrintStream(socket_current.getOutputStream(),true);
                printStream.println(message);
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void privateMessage(String message,int senderID,int receiverID){
        ClientHandler receiver = clientHandlers.get(receiverID);
        Socket socket_current = receiver.getSocket();
        try {
            PrintStream printStream = new PrintStream(socket_current.getOutputStream(),true);
            printStream.println("Client "+senderID+" >> "+"Client "+receiverID+": "+message);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void list(int ID){
            try {
                Socket socket_current = clientHandlers.get(ID).getSocket();
                PrintStream printStream = new PrintStream(socket_current.getOutputStream(),true);
                Iterator<ClientModel> iteratorModel = clientModels.values().iterator();
                printStream.println("<<<<----------Current Clients----------->>>>");
                while (iteratorModel.hasNext()){//Send all the information to this user
                    ClientModel currentModel = iteratorModel.next();
                    printStream.println(currentModel.toString());
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void addNewMather(ClientModel clientModel){//When a new client joint, this method will be invoked
            clientModels.put(currentID,clientModel);
            if(currentID>=2){
                match(currentID-1,currentID);//match two clients
            }
            //Add the matcher to the queue.
            //And then, check if there are valid other matchers.
        }

        public void match(int id1, int id2){

//            ClientModel clientModel1 = clientModels.get(id1);
//            ClientModel clientModel2 = clientModels.get(id2);
//            clientModel1.setMatched(true);
//            clientModel2.setMatched(true);
            Socket socket1 = clientHandlers.get(id1).getSocket();
            Socket socket2 = clientHandlers.get(id2).getSocket();
            matchPairs.put(id1,id2);//Record the match pair
            matchPairs.put(id2,id1);
            try {
                PrintStream printStream = new PrintStream(socket1.getOutputStream(),true);
                printStream.println("MATCHED");//Send the matched message to them

                printStream = new PrintStream(socket2.getOutputStream(),true);
                printStream.println("MATCHED");//Send the matched message to them

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void sendMessage(int id,String message){
            int receiverID = matchPairs.get(id);
            Socket receiverSocket = clientHandlers.get(receiverID).getSocket();
            PrintStream printStream = null;
            try {
                printStream = new PrintStream(receiverSocket.getOutputStream(),true);
                System.out.println(message);
                printStream.println(message);//Send the matched message to them
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    public static void main(String[] args) {
        Server server = new Server();
        server.startServer();
    }
}
