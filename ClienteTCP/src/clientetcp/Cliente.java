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
    private String nome;
    private Socket mySocket;
    private String ip;
    private int Score;
    private ObjectOutputStream Saida;
    private ObjectInputStream entrada;

    public Cliente(String ip) {
        this.ip = ip;
        
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
    
    public void iniciaSessao() {
        try {
            
            System.out.println("IP :" +this.ip); 
            this.mySocket = new Socket( this.ip ,1800);
            
            this.Saida = new ObjectOutputStream(this.mySocket.getOutputStream());
            this.Saida.flush();
                
            this.Saida.writeObject(this.nome);
            
            
        } catch(Exception e) {
            System.out.println("Deu erro aqui: " + e.getMessage());
        }
    }
    
    public void IniciaGame() {
        try {
            
            System.out.println("chegou aqui!!!!!!" + this.mySocket);
            this.entrada = new ObjectInputStream(this.mySocket.getInputStream());
            String retorno = (String)entrada.readObject();
            System.out.println(retorno.toString());
            
        } catch(Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        
    }
}
