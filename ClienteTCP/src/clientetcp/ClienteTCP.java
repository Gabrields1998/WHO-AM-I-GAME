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
        int continua = 1;
        while(continua == 1) {
            if(cli.getMaster() == 1) {
                cli.dica();
                cli.instrucoes();
                System.out.println("\nAguardando Resposta...");
              while (true) {
                cli.recebe();
                cli.recebe();
                cli.resposta();
                cli.recebe();
                cli.recebe();
                cli.acertou();
                cli.recebe();
                cli.recebeInt();
                if(cli.getBreakGame() == 0){
                    cli.setDaVez();
                } else {
                    cli.setDaVez();
                    System.out.println("Acertou");
                    cli.continuaGame();
                    cli.recebe();
                    cli.setContinuaGame();
                    if( cli.getContinuaGame() == 1){
//                        cli.recebe();
                        cli.setMaster();
//                        cli.recebeInt();
                        break;
                    } else {
                        continua = 0;
                        break;
                    }
                }
            }
          } else {
            cli.instrucoes();
            while (true) {
                cli.recebe();
                if(cli.getDaVez() == 1){
                  cli.pergunta();
                } else {
                    System.out.println("\nAguardando Resposta...");
                }
                cli.recebe();
                cli.recebe();
                
                if(cli.getDaVez() == 1){
                  cli.pergunta(); //tentativa
                }else {
                    System.out.println("a"
                            + "\nAguardando Tentativa...");
                }
                cli.recebe();
                cli.recebe();
                cli.recebeInt();
                if(cli.getBreakGame() == 0){
                    cli.setDaVez();
                } else {
                    cli.setDaVez();
                    System.out.println("Acertou");
                    cli.recebe();
                    cli.setContinuaGame();
                    if(cli.getContinuaGame() == 1){
//                        cli.recebe();
                        cli.setMaster();
//                        cli.recebeInt();
                        break;
                    } else {
                        continua = 0;
                        break;
                    }
                }
            }
          }
        }

    }
}
