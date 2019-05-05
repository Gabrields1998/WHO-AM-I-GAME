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
import java.lang.Thread;

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
            new Thread() {
                @Override
                public void run() {
                    while(true){
                        server.connectPlayer();
                    }
                }
            }.start();
            
            while(true) {
                
                if(server.getQtdeListaEspera() >= 3) {
                    Cliente clientes[] = server.getListaEspera();
                    server.zeraListaEspera();
                    new Thread() {
                        @Override
                        public void run() {
                            Jogo game = new Jogo(clientes, 3);
                            game.defineMaster();
                            game.iniciaJogo();
                            game.defineRegras();
                            game.enviaInstrucoes();
                            while(true) {
                                game.pergunta();
                                game.resposta();
                                game.tentativa();
                                if(game.acertou() == 1){
                                    break;
                                }
                                game.defineDaVez();
                            }
                        }
                    }.start();
                }
            }
            
        } catch(Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        finally {
        
        }
    }
    
}
