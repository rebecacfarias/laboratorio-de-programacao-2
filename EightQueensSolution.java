package atividade01;

import java.security.SecureRandom;

public class TestingQ2{ 

    //VARIAVEL GLOBAL PARA CONTROLAR AS SOLUÇÕES ENCONTRADAS
    public static boolean solution = false;

    //FUNÇÃO PARA INSERIR RAINHAS NO TABULEIRO 
    public static void queensOnBoard(int board[][]){
        int placedQueens = 0; //controle de quantas rainhas foram inseridas
        int lastLine = 0, lastColumn = 0;  // guardar a última posição colocada 
        boolean inBacktracking = false; // variável para controlar o retorno à última posição caso não haja mais possibilidades
        int j = 0; //coluna
        int k = 0 ; //controle de fluxo das colunas
        int count = 0; //controle para não entrar em loop infinito
        SecureRandom random = new SecureRandom(); //randomizar posição da coluna;

        //LAÇO PARA INSERIR RAINHAS 
        for(int i = 0; i<8;i++){
            for(k = 0; k<8; k++){
                j = random.nextInt(8); //randomiza a coluna

                //se estiver no backtracking, reseta a variável e pula caso a posição seja a mesma que não deu certo
                if(inBacktracking && i == lastLine && j == lastColumn){
                    inBacktracking = false;
                    
                    continue;
                }
                //teste para inserir rainha (1)
                if(board[i][j] == 0 && emptyLine(board,i,j) && freeDiagonal(board,i,j) && emptyColumn(board,i,j) &&freeOppositeDiagonal(board, i , j)){
                          board[i][j] = 1;
                          placedQueens++;
                          //guarda a posição da ultima rainha inserida
                          lastLine = i;
                          lastColumn = j;
                }
            }
            //bloco de execução para quando chegar no final do laço
            if(i == 7 && k == 8){
                //caso o numero de rainhas ainda seja inferior a 8, volta e muda a posição da última colocada
                if(placedQueens < 8){
                    inBacktracking = true;
                    i = lastLine;
                    j = lastColumn;  
                    count++; 
                    //caso entre em backtracking muitas vezes, considera-se que não encontrou uma solução 
                    if(count>1000){
                        solution = false;
                        break;
                    }
                }
                else{
                    solution = true;
                }
            }

        }
    }



    //checa se a linha está vazia, com exceção da coluna atual
    public static boolean emptyLine(int board[][],int line, int currentColumn){
        for(int i = 0; i<8; i++){
            if(i == currentColumn) continue;
            if(board[line][i]>0) return false;
        }
        return true;
    }

    //checa se a diagonal está livre
    public static boolean freeDiagonal(int board[][], int line, int column){
        if(line == 0){
            for(int i = 0; i<=8; i++){
                if(line+i<8 && column+i<8){
                   if(board[line+i][column+i] > 0){
                        return false;
                    }
                }
            } 
        }
        else{
            //diagonal superior
            for(int i = line, j = column; i>=0 && j>=0; i--, j--){
                    if(board[i][j]>0) return false;
            }
            //diagonal inferior
            for(int i = line, j = column; i<8 && j<8; i++, j++){
                if(board[i][j]>0) return false;
            }
        }
        return true;
        
    }

    //checa se a diagonal oposta está livre
    public static boolean freeOppositeDiagonal(int board[][], int line, int column) {
        if (line == 7) {
            for (int i = 7; i >= 0; i--) {
                if (line - i >= 0 && column + i < 8) {
                    if (board[line - i][column + i] > 0) {
                        return false;
                    }
                }
            }
        } else {
            // diagonal superior
            for (int i = line, j = column; i >= 0 && j < 8; i--, j++) {
                if (board[i][j] > 0)
                    return false;
            }
            // diagonal inferior
            for (int i = line, j = column; i < 8 && j >= 0; i++, j--) {
                if (board[i][j] > 0)
                    return false;
            }
        }
        return true;

    }

    //checa se a coluna está livre
    public static boolean emptyColumn(int board[][],int currentLine, int column){
        for(int i = 0; i<8; i++){
            if(i == currentLine) continue;
            if(board[i][column]>0) return false;
        }
        return true;
    }

    //mostra o tabuleiro
    public static void displayBoard(int board[][]){
        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){
                System.out.printf(" %d ",board[i][j]);
            }
            System.out.println();
        }
    }


    //MAIN
    public static void main(String args[]){
        int[][] board = new int[8][8];
        int[][] solutionBoard = new int[8][8];
        int dim = 1000;
        int solutions = 0;
        boolean[] testArray = new boolean[dim];

        for(int i = 0; i<100; i++){
            queensOnBoard(board);
            board = new int[8][8];
            testArray[i] = solution;
            if(solution)
                solutions++; 
        }
        


        System.out.printf("\n%-20s %-20s\n", "Iteracao", "Solucao Encontrada");
        for(int i = 0; i<dim; i++){
            System.out.printf("%-20d %-20s\n", i+1, testArray[i] ? "verdadeiro" : "falso");
        }
        System.out.printf("%-20s %-20d", "Solucoes encontradas: ", solutions);

        

        
    }
}
