/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package who.am.i.game;
import java.net.*;
import java.io.*;
import java.util.*;
/**
 *
 * @author gabriel
 */
public class ClienteTCPBasico {
    public static void main(String[] args){
        try {
            Socket cliente = new Socket("paulo",12345);
            ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
            Date data_atual = (Date)entrada.readObject();
            System.out.println("Data recebida do servidor:" + data_atual.toString());
            entrada.close();
            System.out.println("Conexão encerrada");
        }
        catch(Exception e) {
        System.out.println("Erro: " + e.getMessage());
    }
    }
}
