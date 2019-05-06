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
    private Cliente listaEspera[] = new Cliente[3];
    private ServerSocket servidor;
    private volatile int qtdeArray;
    private volatile int qtdeListaEspera;
    private ObjectOutputStream Saida;
    private ObjectInputStream Entrada;

    public int getQtdeArray() {
        return qtdeArray;
    }
    
    public int getQtdeListaEspera() {
        return qtdeListaEspera;
    }
    
    public void zeraListaEspera() {
        this.listaEspera = new Cliente[3];
        this.qtdeListaEspera = 0;
    }
    
    public Cliente[] getClientes() {
        return clientes;
    }
    
    public Cliente[] getListaEspera() {
        return listaEspera;
    }
    
    public ObjectOutputStream getSaida() {
        return Saida;
    }

    public ObjectInputStream getEntrada() {
        return Entrada;
    }

    public Servidor() {
        this.qtdeArray = 0;
        this.qtdeListaEspera = 0;
    }
    
    public void LigaServidor() {
        try {
            
            this.servidor = new ServerSocket(1800);
            System.out.println("Servidor ouvindo a porta 1800");
            
        } catch(Exception e) {
            System.out.println(" Erro -> Servidor: LigaServidor " + e.getMessage());
        }
    }
    public void apresentaPlayers(Cliente cliente) {
        try {
                cliente.setSaida(new ObjectOutputStream(cliente.getMySocket().getOutputStream()));
                cliente.getSaida().flush();
                
                cliente.getSaida().writeObject("-------------------------------------------------\n"
                                + "------------BEM VINDO AO (WHO-I-AM?)-------------\n"
                                + "-------------------------------------------------\n"
                                + "Atualmente ha: " + this.qtdeListaEspera + "conectados\n"
                                + "Seu jogo ira iniciar quando houver 3 ou mais players!!! Aguarde.\n");
        } catch(Exception e) {
            System.out.println("Erro -> Servidor: apresentaPlayers " + e.getMessage());
        }
        
    }
    
    public void connectPlayer() {
        try {
            Socket cliente = servidor.accept();
            
            
            
            Cliente cli = new Cliente(cliente.getInetAddress().getHostAddress());
            cli.setEntrada(new ObjectInputStream(cliente.getInputStream()));
            cli.setNome((String) cli.getEntrada().readObject());
            
            cli.setMySocket(cliente);
            this.clientes[this.qtdeArray] = cli;
            this.listaEspera[this.qtdeListaEspera] = cli;
            this.qtdeArray ++;
            this.qtdeListaEspera ++;
            
            System.out.println("Usuario: " + cli.getNome()+ " Logado com o IP: " + cli.getIp());
            this.apresentaPlayers(cli);
            
        } catch(Exception e) {
            System.out.println("Erro -> Servidor: connectPlayer " + e.getMessage());
        }
        
    }
    
}
