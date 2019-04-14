/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientetcp;

import java.io.ObjectInputStream;
import java.util.*;
import java.net.*;
import java.io.*;
import javax.swing.*;
/**
 *
 * @author gabriel
 */
public class ClienteTCP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {   
        Cliente cli = new Cliente(args[0]);
              cli.pegaNome();
              cli.iniciaSessao();
              cli.IniciaGame();
//            Scanner scan = new Scanner(System.in);
//            System.out.println("Digite seu nome: ");
//            String nome = scan.nextLine();
//            Cliente cli = new Cliente(nome, args[0]);
//            
//            
//            System.out.println("IP :" + cli.getIp()); 
//            cli.setMySocket(new Socket( cli.getIp() ,1800));
//            
//            ObjectOutputStream Saida = new ObjectOutputStream(cli.getMySocket().getOutputStream());
//            Saida.flush();
//                
//            Saida.writeObject(cli.getNome());
//            
//            Saida.close();
            
            
            
            while(true);
//            ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
//           
//            String retorno = (String)entrada.readObject();
//            System.out.println("UHUUUUU:" + retorno.toString());
//            entrada.close();
//            System.out.println("Conexão encerrada");

    }
}
