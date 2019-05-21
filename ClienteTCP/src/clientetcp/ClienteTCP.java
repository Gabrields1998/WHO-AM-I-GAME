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
 * @author gabriel && Santana
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
                cli.dica(); // master define as dicas
                cli.instrucoes(); // as intruções são exibidas para todos os players
                System.out.println("\nAguardando Pergunta do Jogador da vez...");
              while (true) {
                cli.recebe(); // print -> 'jogo iniciado'
                cli.recebe(); //espera a sugestão do player
                cli.resposta(); // diz se a sugestão está certa ou não
                cli.recebe(); // a mensagem se está certa ou não é exibida para todos os players
                System.out.println("Aguardando Tentativa..."); // espera uma tentativa.
                cli.recebe(); // recebe a tentativa de acerto do player
                cli.acertou();// diz se o player acertou ou não
                cli.recebe(); // recebe a mensagem que acabou de enviar "se está certo ou não"
                cli.recebeInt(); 
                if(cli.getBreakGame() == 0){ // caso o player tenha errado, continua o a partida
                    cli.setDaVez(); // seta o próximo player da Vez
                } else { // caso tenha acertado : 
                    cli.setDaVez(); // seta o player da vez
                    System.out.println("Acertou"); 
                    cli.continuaGame(); // o mestre vai definir se continua ou não continua o jogo.
                    cli.recebe(); // recebe a mensagem -> jogo vai continuar ou não.
                    cli.setContinuaGame(); // seta a variável responsável pelo controle da continuação do jogo
                    if( cli.getContinuaGame() == 1){ // caso o jogo continue.
//                        cli.recebe();
                        cli.setMaster(); // troca de mestre.
//                        cli.recebeInt();
                        break;
                    } else {
                        continua = 0; // caso o jogo não coninue, acaba com o laço de fora
                        break; //acaba com o laço de dentro.
                    }
                }
            }
          } else {
            cli.instrucoes();
            while (true) {
                cli.recebe(); // recebe a dica definida pelo mestre
                if(cli.getDaVez() == 1){ // caso esse player seja o da vez
                  cli.pergunta(); // ele pergunta.
                } else { // se não
                    System.out.println("\nAguardando Resposta..."); // aguarda 
                }
                cli.recebe(); // recebe a pergunta do player da vez
                cli.recebe(); // recebe a resposta do master
                
                if(cli.getDaVez() == 1){ // caso esse player seja o da vez
                  cli.pergunta(); // ele tem direito ao a uma tentativa de chute.
                }else { // caso contrario
                    System.out.println("Aguardando Tentativa..."); // espera uma tentativa.
                }
                cli.recebe(); // recebe a tentativa do player da vez
                cli.recebe(); // recebe a resposta do mestre
                cli.recebeInt(); 
                if(cli.getBreakGame() == 0){ // caso o player da vez tenha errado: 
                    cli.setDaVez(); // segue a pertida
                } else { // caso tenha acertado: 
                    System.out.println("----------Acertou!!!---------");
                    cli.setDaVez(); // seta o da vez
                    cli.recebe(); // recebe mendagem -> o jogo vai continuar ou não
                    cli.setContinuaGame(); // seta a variável responsável pelo controle da continuação do jogo
                    if(cli.getContinuaGame() == 1){ // caso o jogo vá continuar:
//                        cli.recebe();
                        cli.setMaster(); // seta quem é o master para cada cliente.
//                        cli.recebeInt();
                        break; // reinicia o algoritmo para reestruturar as posições de mestre e players
                    } else { // caso o jogo vá acabar
                        continua = 0; // quebra o laço de fora
                        break; // quebra o laço de dentro
                    }
                }
            }
          }
        }

    }
}
