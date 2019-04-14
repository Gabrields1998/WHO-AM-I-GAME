/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidortcp;
import clientetcp.*;
import java.net.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author gabriel
 */
public class Servidor{
    private Cliente clientes[] = new Cliente[100];
    private ServerSocket servidor;
    private int qtdeArray;
    private ObjectOutputStream Saida;
    private ObjectInputStream Entrada;

    public int getQtdeArray() {
        return qtdeArray;
    }

    public Cliente[] getClientes() {
        return clientes;
    }

    
    public Servidor() {
        this.qtdeArray = 0;
    }
    public void LigaServidor() {
        try {
            
            this.servidor = new ServerSocket(1800);
            System.out.println("Servidor ouvindo a porta 1800");
            
        } catch(Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    public void apresentaPlayers(Cliente cliente) {
        try {
                this.Saida = new ObjectOutputStream(cliente.getMySocket().getOutputStream());
                this.Saida.flush();
                
                this.Saida.writeObject("-------------------------------------------------\n"
                                + "------------BEM VINDO AO (WHO-I-AM?)-------------\n"
                                + "-------------------------------------------------\n"
                                + "Atualmente ha: " + this.qtdeArray + "conectados\n"
                                + "Seu jogo ira iniciar quando houver 3 ou mais players!!! Aguarde.\n");
        } catch(Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        
    }
    
    public void connectPlayer() {
        try {
            Socket cliente = servidor.accept();
            
            this.Entrada = new ObjectInputStream(cliente.getInputStream());
            Cliente cli = new Cliente(cliente.getInetAddress().getHostAddress());
            cli.setNome((String)this.Entrada.readObject());
            
            cli.setMySocket(cliente);
            this.clientes[this.qtdeArray] = cli;
            this.qtdeArray ++;
            
            System.out.println("Usuario: " + cli.getNome()+ " Logado com o IP: " + cli.getIp());
            this.apresentaPlayers(cli);
            
        } catch(Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        
    }
    
}
