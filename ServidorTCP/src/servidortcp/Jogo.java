/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidortcp;

import clientetcp.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
// imports para arquivos 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author gabriel && Santana
 */

public class Jogo {
    private Cliente master;
    private Cliente daVez;
    private Cliente players[] = new Cliente[10];
    private int qtdPlayer;
    private int masterDaVez;
    private int jogadorDaVez;
    private int breakGame;
    private int continuaGame;
    private Integer score;
    private BufferArquivo bufferArq[] = new BufferArquivo[10];
    private String dica;
    private String resposta;
    private String ganhador;
    
    private class BufferArquivo {
        private String nome;
        private Integer score;

        public BufferArquivo() {
            this.score = 0;
        }
        
       
        public String getNome() {
            return nome;
        }

        public Integer getScore() {
            return score;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public void setScore(Integer score) {
            this.score = score;
        }
        
            
    }
    
    public Jogo(Cliente[] players, int qtdPlayers) {
        this.players = players;
        this.masterDaVez = 0;
        this.jogadorDaVez = 0;
        this.breakGame = 0;
        this.continuaGame = 0;
        this.score = 1000;
        this.qtdPlayer = qtdPlayers;
        for (int i = 0; i < 10; i++){
                this.bufferArq[i] = new BufferArquivo();
            }
    }
    
    public Integer getScore() {
        return this.score;
    }
    
    public void setScore() {
        this.score = 1000;
    }
    
    public void leitor(String path) throws IOException {
        try{
            BufferedReader buffRead = new BufferedReader(new FileReader(path));
            String linha = "";
            String parcialBuffer[];
            int i = 0;
            
            System.out.println("classe criada: " + this.bufferArq[0].getScore());
            while (i < 10) {
                linha = buffRead.readLine();
                
                if (linha != null) {
                    parcialBuffer = linha.split(" ");
                    System.out.println("parcialBuffer: " + parcialBuffer[0]);
                    this.bufferArq[i].setNome(parcialBuffer[0]);
                    System.out.println("parcialBuffer" + Integer.parseInt(parcialBuffer[1]));
                    this.bufferArq[i].setScore(Integer.parseInt(parcialBuffer[1]));
                }
                i++;
            }
            buffRead.close();
            this.bubbleSort(this.bufferArq);
        } catch(IOException e){
            System.out.println("Erro -> Leitor: " + e.getMessage());
        }
    }
   
    public void escritor(String path) throws IOException {
        try{
            BufferedWriter buffWrite = new BufferedWriter(new FileWriter(path));
            Integer linha = 0;
            linha = this.score;
            for (int i = 0; i < 10; i++){
                buffWrite.append(this.bufferArq[i].getNome() + " " + this.bufferArq[i].getScore() +"\n");
            }
            
            buffWrite.close();
            
        }catch(IOException e){
            System.out.println("Erro -> Escritor: " + e.getMessage());
        }
    }
    
    public void arrumaArquivo() {
        BufferArquivo novoBuffer = new BufferArquivo();
        novoBuffer.setNome(this.ganhador + ":");
        novoBuffer.setScore(this.score);
        BufferArquivo bufferAux[] = new BufferArquivo[11];
        for (int i = 0; i < 10; i++) {
                bufferAux[i] = this.bufferArq[i];
        }
       
        bufferAux[10] = novoBuffer;
        
        this.bubbleSort(bufferAux);
        
        for (int i = 0; i < 10; i++) {
                this.bufferArq[i] = bufferAux[i];
        }
        
        for (int i = 0; i < bufferArq.length; i++) {
            System.out.println("nome: " + bufferArq[i].getNome());
        }
        
    }
    
    private void bubbleSort(BufferArquivo vetor[]){
        boolean troca = true;
        BufferArquivo aux;
        while (troca) {
            troca = false;
            for (int i = 0; i < vetor.length - 1; i++) {
                if (vetor[i].getScore() < vetor[i + 1].getScore()) {
                    aux = vetor[i];
                    vetor[i] = vetor[i + 1];
                    vetor[i + 1]= aux;
                    troca = true;
                }
            }
        }
    }
    
    public void defineMaster() {
 //---------------------defini master da vez---------------------------
        this.master = this.players[this.masterDaVez];
        this.masterDaVez ++;
        try {
            for (int i = 0; i < this.qtdPlayer; i++){
                this.players[i].getSaida().flush();
                if(this.players[i] == this.master && this.master != this.daVez ) {
                    this.players[i].getSaida().writeObject(1);
                } else if(this.master == this.daVez){
                    i = (i+1)%3;
                } else {
                    this.players[i].getSaida().writeObject(0);
                }
            }
//            for (int i = 0; i < this.qtdPlayer; i++){
//                this.players[i].getSaida().flush();
//                if(this.players[i] == this.master ) {
//                    this.players[i].getSaida().writeObject(1);
//                } else {
//                    this.players[i].getSaida().writeObject(0);
//                }
//            }
 //---------------------defini master da vez---------------------------
 
 //---------------------define jogador da vez--------------------------           
            for (int i = 0; i < this.qtdPlayer; i++){
                this.players[i].getSaida().flush();
                if(this.players[i] == this.master) {
                    if(this.players[i] == this.players[this.jogadorDaVez]){
                        this.jogadorDaVez = (this.jogadorDaVez + 1)%3;
                    }
                    this.players[i].getSaida().writeObject(0);
                } else {
                    if(this.players[i] == this.players[this.jogadorDaVez]) {
                        this.players[i].getSaida().writeObject(1);
                        this.daVez = this.players[i];
                    } else {
                        this.players[i].getSaida().writeObject(0);
                    }
                    
                }   
            }
            this.jogadorDaVez = (this.jogadorDaVez + 1)%3;
//            this.jogadorDaVez++;
 //---------------------define jogador da vez--------------------------
        } catch(Exception e) {
            System.out.println("Erro -> Jogo defineMaster " + e.getMessage());
        }
        System.out.println("master definido como: " + this.master.getNome());
    }
    
    public void defineDaVez() {
        try{
//---------------------define jogador da vez-------------------------- 
            this.ganhador = this.daVez.getNome();
            int i = this.jogadorDaVez;
            while( i < this.qtdPlayer ){
                if(this.players[i] == this.master){
                    i = (i + 1)%3;
                } else {
                    this.players[i].getSaida().flush();
                    this.players[i].getSaida().writeObject(1);
                    this.jogadorDaVez = i;
                    this.daVez = this.players[this.jogadorDaVez];
                    break;
                }
            }
            for(int j = 0; j < this.qtdPlayer; j++){
                if(j != this.jogadorDaVez){
                    this.players[j].getSaida().flush();
                    this.players[j].getSaida().writeObject(0);
                }
            }
            this.jogadorDaVez = (this.jogadorDaVez + 1)%3;
 //---------------------define jogador da vez--------------------------
        } catch(Exception e) {
            System.out.println("Erro -> Jogo: defineDavez " + e.getMessage());
        }
    
    }
    
    public void iniciaJogo(){
        try {
            String listaNome = "";
            for (int i = 0; i < this.qtdPlayer; i++){
                listaNome += "\t[" + this.players[i].getNome() + "]\n";
            }
            for (int i = 0; i < this.qtdPlayer; i++){
                this.players[i].getSaida().flush();
                this.players[i].getSaida().writeObject(
                        "-------------------------------------------------\n"
                      + "-----------------INICIANDO PARTIDA---------------\n"
                      + "-------------------------------------------------\n"
                      + "Jogadores conectados atualmente: " + this.qtdPlayer +"\n"
                      + listaNome + "\n"
                      + "MESTRE da rodada: [" + this.master.getNome() + "]\n"
                      + "Aguardando definição de dica e resposta pelo MESTRE...\n ");
            }
        } catch(Exception e) {
            System.out.println("Erro -> Jogo: iniciaJogo" + e.getMessage());
        }
    }
    
    public void enviaInstrucoes(){
        try {
            for (int i = 0; i < this.qtdPlayer; i++){
                this.players[i].getSaida().flush();
                this.players[i].getSaida().writeObject(
                        "-------------------------------------------------\n"
                      + "------------------PARTIDA INICIADA---------------\n"
                      + "-------------------------------------------------\n"
                      + "MESTRE: " + "[" + this.master.getNome() + "]\n"
                      + "DICA: " + this.dica + "\n\n"
                      + "Instruções:\n"
                      + "> Somente são permitidas perguntas com respostas do tipo SIM/NÃO\n"
                      + "> Perguntas inadequadas serão invalidadas pelo MESTRE\n"
                      + "JOGADOR perde a vez se fizer pergunta inadequada\n");
            }
        } catch(Exception e) {
            System.out.println("Erro -> Jogo: enviaInstrucoes " + e.getMessage());
        }
    }
    
    public void defineRegras() {
        try {
            this.master.getSaida().flush();
            this.master.getSaida().writeObject(
                "-------------------------------------------------\n" +  
                "MESTRE Informe a dica: "
            );
            
            String dicaRecebida = (String)this.master.getEntrada().readObject();
            this.dica = dicaRecebida;
            System.out.println("dica recebida e: " + this.dica + "\n");
            
            this.master.getSaida().flush();
            this.master.getSaida().writeObject(
                "-------------------------------------------------\n" +
                "Informe a resposta: ");
            
            String respostaRecebida = (String)this.master.getEntrada().readObject();
            this.resposta = respostaRecebida;
            System.out.println(
                "-------------------------------------------------\n" +
                 "Resposta recebida e: " + this.resposta + "\n");
        } catch(Exception e) {
            System.out.println("Erro -> Jogo: defineRegras" + e.getMessage());
        }
    }
    
    public void pergunta() {
         try {
            for (int i = 0; i < this.qtdPlayer; i++){
                this.players[i].getSaida().flush();
                this.players[i].getSaida().writeObject(
                    "jogador da vez: " + "[" + this.daVez.getNome() + "]\n");
            }
            
            this.daVez.getSaida().flush();
            this.daVez.getSaida().writeObject(
                      "[" + this.daVez.getNome() + "] "
                    + "Informe sua pergunta: ");
            
            String perguntaRecebida = (String)this.daVez.getEntrada().readObject();
            String pergunta = perguntaRecebida;
            System.out.println("pergunta recebida: " + pergunta + "\n");
            
            for (int i = 0; i < this.qtdPlayer; i++){
                this.players[i].getSaida().flush();
                this.players[i].getSaida().writeObject(
                    "[" + this.daVez.getNome() + "]: " + pergunta);
            }
        } catch(Exception e) {
            System.out.println("Erro -> Jogo: pergunta" + e.getMessage());
        }
    }
    
    public void resposta() {
        try {

            this.master.getSaida().flush();
            this.master.getSaida().writeObject("Acertou a resposta?:");
            
            String respostaRecebida = (String)this.master.getEntrada().readObject();
            String resposta = respostaRecebida;
            System.out.println("Sim ou Nao?:" + resposta + "\n");
            
            for (int i = 0; i < this.qtdPlayer; i++){
                this.players[i].getSaida().flush();
                this.players[i].getSaida().writeObject(
                    "[" + this.master.getNome() + "]: " + resposta);
            }
        } catch(Exception e) {
            System.out.println("Erro -> Jogo: resposta " + e.getMessage());
        }
    }
    
    public void tentativa() {
        try {
            this.daVez.getSaida().flush();
            this.daVez.getSaida().writeObject("Informe sua tentativa: \n");
            String tentativaRecebida = (String)this.daVez.getEntrada().readObject();
            String tentativa = tentativaRecebida;
            System.out.println("tentativa recebida: " + tentativa + "\n");
            
            for (int i = 0; i < this.qtdPlayer; i++){
                this.players[i].getSaida().flush();
                this.players[i].getSaida().writeObject(
                     "TENTATIVA -" +
                    " [" + this.daVez.getNome() + "]: " + tentativa);
            }
        } catch(Exception e) {
            System.out.println("Erro -> Jogo: tentativa" + e.getMessage());
        }
    }
    
    public int acertou() {
         try {

            this.master.getSaida().flush();
            this.master.getSaida().writeObject("\nAcertou?: \n"
                    + "0: Errado \n"
                    + "1: Correto \n");
            
            String respostaRecebida = (String)this.master.getEntrada().readObject();
            String resposta = respostaRecebida;
            System.out.println("correto ou falso:" + resposta + "\n");
            
            if(resposta.equals("0")) {
                this.breakGame = 0;
                for (int i = 0; i < this.qtdPlayer; i++){
                    this.players[i].getSaida().flush();
                    this.players[i].getSaida().writeObject(
                        "[" + this.master.getNome() + "]: Errado");
                }
                this.score -= 10;
                System.out.println("Score atual: " + this.score);
            } else if(resposta.equals("1")) {
                this.breakGame = 1;
                for (int i = 0; i < this.qtdPlayer; i++){
                    this.players[i].getSaida().flush();
                    this.players[i].getSaida().writeObject(
                        "[" + this.master.getNome() + "]: Correto");
                }
                
            } else {
                this.breakGame = 0;
                for (int i = 0; i < this.qtdPlayer; i++){
                    this.players[i].getSaida().flush();
                    this.players[i].getSaida().writeObject(
                        "[" + this.master.getNome() + "]: Opcao invalida");
                }
            }
            
            for (int i = 0; i < this.qtdPlayer; i++){
                this.players[i].getSaida().flush();
                this.players[i].getSaida().writeObject(this.breakGame);
            }
        } catch(Exception e) {
            System.out.println("Erro -> Jogo: acertou" + e.getMessage());
        }
        return this.breakGame;
    }
    
    public int continuaGame() {
        try {
            this.master.getSaida().flush();
            this.master.getSaida().writeObject("Quer continuar o jogo?: \n"
                    + "0: Não \n"
                    + "1: Sim \n");
            
            String respostaRecebida = (String)this.master.getEntrada().readObject();
            String resposta = respostaRecebida;
            System.out.println("Continua ou Não continua:" + resposta + "\n");
            
            if(resposta.equals("0")) {
                this.continuaGame = 0;
                for (int i = 0; i < this.qtdPlayer; i++){
                    this.players[i].getSaida().flush();
                    this.players[i].getSaida().writeObject(
                        "[" + this.master.getNome() + "]: Jogo Encerrado");
                }
            } else if(resposta.equals("1")) {
                this.continuaGame = 1;
                for (int i = 0; i < this.qtdPlayer; i++){
                    this.players[i].getSaida().flush();
                    this.players[i].getSaida().writeObject(
                        "[" + this.master.getNome() + "]: Acertou, mas vamos continuar jogando");
                }
            } else {
                this.continuaGame = 0;
                for (int i = 0; i < this.qtdPlayer; i++){
                    this.players[i].getSaida().flush();
                    this.players[i].getSaida().writeObject(
                        "[" + this.master.getNome() + "]: Opcao invalida, jogo encerrado");
                }
            }
            
            for (int i = 0; i < this.qtdPlayer; i++){
                this.players[i].getSaida().flush();
                this.players[i].getSaida().writeObject(this.continuaGame);
            }
        } catch(Exception e) {
            System.out.println("Erro -> Jogo: continuaGame " + e.getMessage());
        }
        return this.continuaGame;
    }
    
    public void defineNewMaster(){
    //---------------------defini master da vez---------------------------
        int breaker = 0;
        try {
            int i = this.masterDaVez;
            while( i < this.qtdPlayer ){
                if(this.players[i] == this.daVez){
                    i = (i + 1)%3;
                } else {
                    this.players[i].getSaida().flush();
                    this.players[i].getSaida().writeObject(1);
                    this.masterDaVez = i;
                    this.master = this.players[this.masterDaVez];
                    break;
                }
            }
            for(int j = 0; j < this.qtdPlayer; j++){
                if(j != this.masterDaVez){
                    this.players[j].getSaida().flush();
                    this.players[j].getSaida().writeObject(0);
                }
            }
            this.masterDaVez = (this.masterDaVez + 1)%3;
 //---------------------defini master da vez---------------------------
        } catch(Exception e) {
            System.out.println("Erro -> Jogo: defineNewMaster " + e.getMessage());
        }
    }
}
