
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TP1 {

    private static MyCommand interC;
    static final int MAX_ALUNOS = 35;
    private static int alunosLidos=0;
    private static int notaMax = 0;
    private static int notaMin = 0;
    private static int notaAvg = 0;

    private static String[] nomeAlunos = new String[MAX_ALUNOS];
    private static int[] notasAlunos = new int[MAX_ALUNOS];

    public static void main(String[] args) {
        boolean querSair=false;

        interC=new MyCommand();

        do {
            interC.limparEcra();
            interC.showPrompt();
            String[] cmdEscrito = interC.lerComando();
            ArrayList<String> cmd = interC.validarComando(cmdEscrito);

            if (cmd == null) {
                interC.showMsg("Comando inválido. Digite help para ajuda");

            } else {
                if  ( cmd.get(0).equalsIgnoreCase("carregar") ) {
                    alunosLidos = loadData(nomeAlunos, "turmaLeit.txt");
                    int notA = loadData(notasAlunos);
                    if ( alunosLidos != notA ) {
                        System.out.println("alunos = " + alunosLidos);
                        System.out.println("notaA = " + notA);
                        interC.showMsg("Erro carregando dados");
                    }

                    else

                        interC.showMsg("Dados carregados OK!");
                } else if (cmd.get(0).equalsIgnoreCase("listar") ) {
                    mostrarAlunos();

                } else if (cmd.get(0).equalsIgnoreCase("paginar") ) {
                    String input = JOptionPane.showInputDialog("Nũmeros estudantes por pãgina :");
                    int numeroU = Integer.parseInt(input);
                    mostrarAlunos(numeroU);

                } else if (cmd.get(0).equalsIgnoreCase("mostrarp") ) {
                    mostrarPauta();


                } else if (cmd.get(0).equalsIgnoreCase("mostrarr") ) {
                    mostraResumo();

                } else if (cmd.get(0).equalsIgnoreCase("top") ) {
                    mostrarTop();

                } else if (cmd.get(0).equalsIgnoreCase("pesquisarnome") ) {
                    String nomePesq = JOptionPane.showInputDialog("O que procura  :");
                    pesquisar(nomePesq);

                } else if (cmd.get(0).equalsIgnoreCase("pesquisarnota") ) {
                    String vaPesq = JOptionPane.showInputDialog("O que procura  :");
                    int notaPesq = Integer.parseInt(vaPesq);
                    pesquisar(notaPesq);
                } else if (cmd.get(0).equalsIgnoreCase("help") ) {
                    interC.showHelp();

                } else if (cmd.get(0).equalsIgnoreCase("terminar") ) {
                    querSair = true;
                }
            }

        } while (!querSair);

    }

    /**
     * Método implementado por Prof. Não devem alterar. Este método recebe
     * como parâmetros um array e um ficheiro
     * Lẽ cada linha do ficheiro e guarda no array. Retorna o número
     * de linhas que forma lidas do ficheiro.
     * @param lAlunos
     * @param nomeFicheiro
     * @return quantos nomes foram lidos do ficheiro -1 se não possível ler ficheiro
     */
    public static int loadData(String[] lAlunos, String nomeFicheiro) {
        Scanner in = null;
        File inputFile = new File(nomeFicheiro);
        //PrintWriter out = new PrintWriter(outputFileName);
        try {
            in = new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int i = 0;
        while (in.hasNextLine()) {
            String nomeAl = in.nextLine();
            if ( (nomeAl != null) && !(nomeAl.isBlank()) && !(nomeAl.isEmpty() ) ) {
                lAlunos[i] = nomeAl;
                i++;
            }

        }
        in.close();
        return i;
    }

    /**
     * Método implementado por Prof. Não devem alterar. Este método recebe
     * como parâmetros um array de inteiros e vai gerar aleatoriamente valores inteiros entre
     * 0 e 20 que representam a nota de cada aluno.
     * @param lNotas
     * @return how much name was read from the files -1 if was not able to read the file
     */
    public static int loadData(int[] lNotas) {
        Random rand = new Random();
        int cont = 0;
        for (cont=0; cont < alunosLidos; cont++) {
            int randomNum = rand.nextInt(20) + 1;
            notasAlunos[cont] = randomNum;
        }
        return cont;
    }

    /**
     * Método a ser implementando no TP1.
     * O método deverá listar todos os nomes dos alunos guardados no array nomesAlunos.
     * O método deverá verificar se já foi carregado os dados para o array. Se sim mostra os
     * nomes dos alunos. Senão deve mostrar a mensagem "Não há dados"
     * @param
     * @return
     */
    public static void mostrarAlunos() {
        if (nomeAlunos[0] == null) {
            System.out.println("N�o h� dados!");
        } 
        else {
            for (String nome:nomeAlunos) {
            	if(nomeAlunos != null){
                    System.out.println(nome);
                }
            }
        }
        Le.umChar();
        
    }

    /**
     * Método a ser implementando no TP1
     * O método deverá listar todos os nomes dos alunos guardados no array nomesAlunos.
     * O método deverá verificar se já foi carregado os dados para o array. Se sim mostra os
     * nomes dos alunos. Senão deve mostrar a mensagem "Não há dados".
     * Neste método os dados não são mostrados todos de uma só vez. Devem ser apresentados até encher a tela.
     * Vamos supor que 10 nomes enchem a tela. Então deverá ser apresentados de 10 em 10. Esse número
     * que indica quantos nomes enchem a tela é um parâmetro do método.
     * @param tela é um inteiro que indica quantos alunos são mostrados.
     */
    public static void mostrarAlunos(int tela) {
    	boolean mudarPagina = false;
        int pagina = 0;
        if (nomeAlunos[0] == null) {
            interC.showMsg("N�o h� dados!");
        } else {
            int contador=1;
            for (String nome : nomeAlunos) {
                if (nome != null && nome.length() > 0) {
                    if (mudarPagina) {
                        mudarPagina=false;
                    }
                    System.out.printf(contador++, nome.trim().strip());
                    pagina++;
                    System.out.print("\n");
                } else {
                    continue;
                }
                if (pagina == tela) {
                    interC.showMsg("\nEnter para continuar ... ");
                    pagina = 0;
                    mudarPagina = true;
                }
            }
        }
        Le.umChar();

        
    }


    /**
     * Método a ser implementando no TP1.
     * O método deverá percorrer o array de notas, calcular o valor da média aritmética de notas, a nota máximo e
     * a nota mínima.
     * Os valores calculados devem ser guaraddos na variáveis notaAVG (média),
     * notaMax (nota máxima) e notaMin(nota mínima)
     * Devem validar se o array de notas tem elementos. Se estiver vazio devem somente apresentar
     * a mensagem "Não há dados"
     */
    private static void calcularMaxMinAvg() {
        notaAvg = 0;
        int soma = 0;
	        if(nomeAlunos == null){
	        	System.out.println("N�o h� dados!");
	        }
	        else{
	        	notaMin = notasAlunos[0];
		        for(int i = 0; i < notasAlunos.length; i++) {
		        	if(notasAlunos[i] < notaMin)
		        		notaMin = notasAlunos[i];
		        	if(notasAlunos[i] > notaMax)
		        		notaMax = notasAlunos[i];
		        }
		        
		         for(int j = 0; j < notasAlunos.length; j++) {
		        	soma += notasAlunos[j];
		        	notaAvg = soma / notasAlunos.length;
		        }
        
        }
        Le.umChar();

    }

    /**
     * Método a ser implementando no TP1.
     * O método deverá apresentar um resumo da avaliação;
     * Nota máxima, Nota mínima, Nota média. Número de alunos com nota superior a média e número de alunos com
     * nota inferior a média.
     * a mensagem "Não há dados"
     */
    public static void mostraResumo() {
    	int cont = 0;
    	int contador = 0;
    	
    	calcularMaxMinAvg();
    	if(nomeAlunos[0] == null){
    		System.out.println("N�o h� dados!");
    	}
    	else{
	        System.out.println("Total de alunos = " + notasAlunos.length);
	        System.out.println("Nota M�xima = " + notaMax);
	        System.out.println("Nota M�nima = " + notaMin);
	        System.out.println("Nota M�dia = " + notaAvg);
	        
	        for(int i = 0; i < notasAlunos.length; i++){
	        	if(notasAlunos[i] > notaAvg && notasAlunos[i] != 0){
	        		cont++;
	        		
	        	}
	        }
	        System.out.println("N�mero de elementos com nota maior que a m�dia = " + cont);
	        
	        for(int j = 0; j < notasAlunos.length; j++){
	        	if(notasAlunos[j] < notaAvg){
	        		contador++;
	        		
	        	}
	        }
	        System.out.println("N�mero de elementos com nota menor que a m�dia = " + contador);
			
    	}
    	Le.umChar();
    }

    /**
     * Método a ser implementando no TP1.
     * O método deverá apresentar o nome dos três alunos que têm as melhores notas.
     */
    public static void mostrarTop() {
        int m = 20;
        int cont = 0;
        int num = 3;
        
        if(nomeAlunos[0] == null){
    		System.out.println("N�o h� dados!");
    	}
    	else{
    		while(num > 0){
    			if(cont >= notasAlunos.length){
    			cont = 0;
    			--m;
    			}
    			if(notasAlunos[cont] == m){
    				System.out.println(nomeAlunos[cont-1] + " " + notasAlunos[cont]);
    				--num;
    			}
    		++cont;
    		}
    	}
		    
		   
    	Le.umChar();

    }
    /**
     * Método a ser implementando no TP1.
     * Apresentar a pauta com nomes dos alunos e á frente cada nome a respectiva nota obtida.
     */
    public static void mostrarPauta() {
        if(nomeAlunos[0] == null){
    		System.out.println("N�o h� dados!");
    	}
    	else{
	        for(int i = 0; i < 10; i++){
	        	System.out.println(nomeAlunos[i] + " " + notasAlunos[i]);
	        }
	        Le.umChar();
	        for(int j = 10; j < 20; j++){
	        	System.out.println(nomeAlunos[j] + " " + notasAlunos[j]);
	        }
	        Le.umChar();
	        for(int k = 21; k < 32; k++){
	        	System.out.println(nomeAlunos[k] + " " + notasAlunos[k]);
	        }
	        Le.umChar();
	        for(int l = 32; l < nomeAlunos.length; l++){
	        	System.out.println(nomeAlunos[l] + " " + notasAlunos[l]);
	        }
	        Le.umChar();
    	}
    }
    /**
     * Método a ser implementando no TP1
     * Apresentar para um aluno específico em que o nome é dado como parâmetro a nota de avaliação
     * @param nome é uma string contendo o nome do aluno que queremos apresentar a sua nota
     * @return
     */
    public static void mostrarDetalhesAluno(String nome) {
        interC.showMsg("A ser implementado ...");

    }

    /**
     * Método a ser implementando no TP1
     * O método deverá pedir um nome e pesquisar o array de nomes. Caso existir ou caso existem nomes
     * parecidos apresentar a lista de nomes. Nomes parecidos são nomes que iniciam com as mesmas duas ou três
     * primeiras letras. Ou apelidos iguais.
     */
    public static void pesquisar(String nome) {
    	if(nomeAlunos[0] != null)
        {
            boolean encontrado = false; 

            for (String nomes : nomeAlunos)
            {
                if(nomes != null && nome.length() > 3)
                {                
                    String string = nomes;
                    String[] divisao = nomes.split(" ");
                    if(nome.equalsIgnoreCase(divisao[0]) || nome.equalsIgnoreCase(divisao[divisao.length -1]) )
                    {
                        System.out.println(nomes);
                        encontrado = true;
                    }

                    else
                    {
                        if(nome.substring(0,3).equalsIgnoreCase(divisao[0].substring(0,3)) || nome.substring(0,3).equalsIgnoreCase(divisao[divisao.length -1].substring(0,3)))
                        {
                            System.out.println("Nome parecido com " + nome + ": " + nomes);
                            encontrado = true;
                        }
                    }
                } 
            }
       
    }



    /**
     * Método a ser implementando no TP1
     * O método deverá pedir um nome e pesquisar o array de nomes. Caso existir ou caso existem nomes
     * parecidos apresentar a lista de nomes. Nomes parecidos são nomes que iniciam com as mesmas duas ou três
     * primeiras letras. Ou apelidos iguais.
     */
    public static void pesquisar(int nota) {    
		if(nomeAlunos[0] != null)
        {
            boolean encontrado = false;
            int i = -1;

            for(int k : notasAlunos)
            {
                if(nota == k && nomeAlunos[i] != null) 
                {
                    encontrado = true;
                    System.out.println(nomeAlunos[i]);
                }
                ++i;
            }

            if(!encontrado)
                interC.showMsg("N�o encontrado!\n");    
        }

        else
            System.out.println("N�o h� dados!");

    }

    private String[] searchByName(String nome) {
        return null;
    }

}
