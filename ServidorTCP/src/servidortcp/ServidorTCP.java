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
public class ServidorTCP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Servidor server = new Servidor();
            server.LigaServidor();
            while(server.getQtdeArray() < 3){
                server.connectPlayer();     
            }
            
            Jogo game = new Jogo(server.getClientes(), server.getQtdeArray());
            game.defineMaster();
            
            // Instancia o ServerSocket ouvindo a porta 12345
//            ServerSocket servidor = new ServerSocket(1800);
//            System.out.println("Servidor ouvindo a porta 1800");
//            while(true) {
                // o método accept() bloqueia a execução até que
                // o servidor receba um pedido de conexão
                // ----------------------conexao cliente e servidor----------------------------
//                ServerSocket servidor = new ServerSocket(1800);
//                System.out.println("Servidor ouvindo a porta 1800");
//                Socket cliente = servidor.accept();
//                System.out.println("Cliente1 conectado: " + cliente.getInetAddress().getHostAddress());
//                
//                // ----------------------conexao cliente e servidor----------------------------
//                
//                ObjectInputStream Entrada = new ObjectInputStream(cliente.getInputStream());
//                
//                Cliente cli = new Cliente((String)Entrada.readObject(), cliente.getInetAddress().getHostAddress());
//                
//                System.out.println(cli.getNome() + cli.getIp());
//                
////                ObjectOutputStream Saida = new ObjectOutputStream(cliente.getOutputStream());
////                Saida.flush();
////                
////                Saida.writeObject("Iniciando o game!");
////                
////                Saida.close();
//                
//                cliente.close();
////            }  
        } catch(Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        finally {
        
        }
    }
    
}
