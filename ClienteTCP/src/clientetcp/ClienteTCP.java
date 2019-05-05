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
              if(cli.getMaster() == 1) {
                    cli.dica();
                    cli.instrucoes();
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
                          System.out.println("Se e o bixao memo doido");
                          break;
                      }
                  }
              } else {
                  cli.instrucoes();
                  while (true) {
                      cli.recebe();
                      System.out.println("get a vez: "+ cli.getDaVez());
                      if(cli.getDaVez() == 1){
                        cli.pergunta();
                      }
                      cli.recebe();
                      cli.recebe();
                      if(cli.getDaVez() == 1){
                        cli.pergunta(); //tentativa
                      }
                      cli.recebe();
                      cli.recebe();
                      cli.recebeInt();
                      if(cli.getBreakGame() == 0){
                          cli.setDaVez();
                      } else {
                          System.out.println("Se e o bixao memo doido");
                          break;
                      }
                  }
              }
    }
}
