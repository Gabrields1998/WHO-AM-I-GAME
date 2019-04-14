/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidortcp;

import clientetcp.*;

/**
 *
 * @author gabriel
 */
public class Jogo {
  private Cliente master;
  private Cliente players[] = new Cliente[10];
  private int qtdPlayer;
  private int masterDaVez;
  private String resposta;

    public Jogo(Cliente[] players, int qtdPlayers) {
        this.players = players;
        this.masterDaVez = 0;
        this.qtdPlayer = qtdPlayers;
    }
    
    public void defineMaster() {
        this.master = this.players[this.masterDaVez];
        this.masterDaVez ++;
        System.out.println("master definido como: " + this.master.getNome());
    }
    public void iniciaJogo(){
        
        System.out.println("-------------------------------------------------");
        System.out.println("------------BEM VINDO AO (WHO-I-AM?)-------------");
        System.out.println("-------------------------------------------------");
        
        System.out.println("O jogo iniciou com: " + this.qtdPlayer + "Players");
        
        
    }
  
}
