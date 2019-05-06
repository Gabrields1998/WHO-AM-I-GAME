/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetcp;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author gabriel
 */
public class Cliente {
    private int id;
    private int daVez;
    private String nome;
    private int master;
    private Integer breakGame;
    private Integer continuaGame;
    private Socket mySocket;
    private String ip;
    private int Score;
    private ObjectOutputStream Saida;
    private ObjectInputStream entrada;

    public Cliente(String ip) {
        this.ip = ip;
        
    }
    
    public int getMaster() {
        return this.master;
    }
    
    public int getBreakGame() {
       return this.breakGame; 
    }
    
    public Integer getContinuaGame() {
       
       return this.continuaGame; 
    }
    
    public void setContinuaGame() {
        try{
            int continuaGame = (int)entrada.readObject();
            this.continuaGame = continuaGame;
        } catch(Exception e) {
            System.out.println("Erro -> setContinuaGame" + e.getMessage());
        }
        
    }
    
    public int getDaVez() {
       return this.daVez; 
    }
    
    public void setDaVez() {
        try{
            int daVez = (int)entrada.readObject();
            this.daVez = daVez;
            if(daVez == 1) {
                System.out.println("eu sou o cara da vez fodao pika do caralho \n");
            }
        } catch(Exception e) {
            System.out.println("Erro -> setDaVez" + e.getMessage());
        }
        
    }
     
    public void setMaster() {
        try{
            int Master = (int)entrada.readObject();
            this.master = Master;
            if(Master == 1) {
                System.out.println("eu sou master fodao pika do caralho \n");
            }
        } catch(Exception e) {
            System.out.println("Erro -> setMaster: " + e.getMessage());
        }
        
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void pegaNome() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Digite seu nome: ");
        this.nome = scan.nextLine();
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setMySocket(Socket mySocket) {
        this.mySocket = mySocket;
    }

    public void setScore(int Score) {
        this.Score = Score;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Socket getMySocket() {
        return mySocket;
    }

    public String getIp() {
        return ip;
    }

    public int getScore() {
        return Score;
    }

    public ObjectOutputStream getSaida() {
        return Saida;
    }

    public ObjectInputStream getEntrada() {
        return entrada;
    }

    public void setSaida(ObjectOutputStream Saida) {
        this.Saida = Saida;
    }

    public void setEntrada(ObjectInputStream entrada) {
        this.entrada = entrada;
    }
    
    public void iniciaSessao() {
        try {
            
            System.out.println("IP :" +this.ip); 
            this.mySocket = new Socket( this.ip ,1800);
            
            this.Saida = new ObjectOutputStream(this.mySocket.getOutputStream());
            this.Saida.flush();
                
            this.Saida.writeObject(this.nome);
            
            this.entrada = new ObjectInputStream(this.mySocket.getInputStream());
            String retorno = (String)entrada.readObject();
            System.out.println(retorno.toString());
            
        } catch(Exception e) {
            System.out.println("Erro -> iniciaSessao" + e.getMessage());
        }
    }
    
    public void IniciaGame() {
        try {
           
            int souMestre = (int)entrada.readObject();
            this.master = souMestre;
            
            int daVez = (int)entrada.readObject();
            this.daVez = daVez;
            
            String iniciaGame = (String)entrada.readObject();
            System.out.println(iniciaGame.toString());
        } catch(Exception e) {
            System.out.println("Erro -> Iniciagame " + e.getMessage());
        }
    }
    
    public void instrucoes() {
        try {
            String instrucoes = (String)entrada.readObject();
            System.out.println(instrucoes.toString());
        } catch(Exception e) {
            System.out.println("Erro -> passa as instrucoes " + e.getMessage());
        }
    }
    
    public void dica(){
        try {
// -----------------Recebe dica do servidor--------------------------
            String recebeDica = (String)entrada.readObject();
            Scanner scanDica = new Scanner(System.in);
            System.out.println(recebeDica.toString());
            this.getSaida().flush();
            this.getSaida().writeObject(scanDica.nextLine());
            System.out.println("\n");
// -----------------Recebe dica do servidor--------------------------

// -----------------Recebe resposta do servidor----------------------
            String recebeResposta = (String)entrada.readObject();
            Scanner scanResposta = new Scanner(System.in);
            System.out.println(recebeResposta.toString());
            this.getSaida().flush();
            this.getSaida().writeObject(scanResposta.nextLine());
            System.out.println("\n");
// -----------------Recebe resposta do servidor----------------------
        } catch(Exception e) {
            System.out.println("Erro -> dica " + e.getMessage());
        }
    }
    
    public void recebe() {
         try {
            String jogadorDaVez = (String)entrada.readObject();
            System.out.println(jogadorDaVez.toString());
        } catch(Exception e) {
            System.out.println("Erro -> recebe" + e.getMessage());
        }
    }
    
    public void recebeInt() {
        try {
            this.breakGame = (Integer)entrada.readObject();
            System.out.println(this.breakGame.toString());
        } catch(Exception e) {
            System.out.println("Erro recebeInt: " + e.getMessage());
        }
    }
    
    public void pergunta(){
        try {
// -----------------Recebe pergunta do servidor--------------------------
            String recebePergunta = (String)entrada.readObject();
            Scanner scanPergunta = new Scanner(System.in);
            System.out.println(recebePergunta.toString());
            this.getSaida().flush();
            this.getSaida().writeObject(scanPergunta.nextLine());
            System.out.println("\n");
// -----------------Recebe pergunta do servidor--------------------------
        } catch(Exception e) {
            System.out.println("Erro -> pergunta" + e.getMessage());
        }
    }
    
    public void resposta(){
        try {
            String recebeResposta = (String)entrada.readObject();
            Scanner scanResposta = new Scanner(System.in);
            System.out.println(recebeResposta.toString());
            this.getSaida().flush();
            this.getSaida().writeObject(scanResposta.nextLine());
            System.out.println("\n");
        } catch(Exception e) {
            System.out.println("Erro -> resposta" + e.getMessage());
        }
    }
    
    public Integer acertou (){
        Integer resposta = 0;
        try {
// -----------------Recebe pergunta do servidor--------------------------
            String recebePergunta = (String)entrada.readObject();
            Scanner scanPergunta = new Scanner(System.in);
            System.out.println(recebePergunta.toString());
            this.getSaida().flush();
            resposta = scanPergunta.nextInt();
            this.getSaida().writeObject(resposta.toString());
            System.out.println("\n");

// -----------------Recebe pergunta do servidor--------------------------
        } catch(Exception e) {
            System.out.println("Erro -> acertou" + e.getMessage());
        }
        return resposta;
    }
    
    public void continuaGame() {
        try {
// -----------------Recebe pergunta do servidor--------------------------
            Integer resposta = 0;
            String recebePergunta = (String)entrada.readObject();
            Scanner scanPergunta = new Scanner(System.in);
            System.out.println(recebePergunta.toString());
            this.getSaida().flush();
            resposta = scanPergunta.nextInt();
            this.continuaGame = resposta;
            this.getSaida().writeObject(resposta.toString());
            System.out.println("\n");

// -----------------Recebe pergunta do servidor--------------------------
        } catch(Exception e) {
            System.out.println("Erro -> continuaGame" + e.getMessage());
        }
    }
}
