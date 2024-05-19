import java.util.*;

public class Main {
    // Regex para tratar as inserções:
    public static boolean valida_alfabeto_sigma(String input) {
        return input.matches("^[a-z0-9 ]*$");
    }

    public static boolean valida_alfabeto_gama(String input) {
        return input.matches("^[A-Z$ ]*$");
    }

    public static void main(String[] args) {
        // Inicialização do scanner
        Scanner sc = new Scanner(System.in);

        // Strings para fazer a concatenação iterativa das codificações
        String concat = "1";
        String concat2 = "1";
        String concat3 = "1";

        // Ints para conversão de entrada do usuário
        int numero_estados = 0;
        int int_estado_inicial = 0;
        int int_estados_finais = 0;
        int tamanho_alfabeto_sigma = 0;
        int tamanho_alfabeto_gama = 0;

        // Listas
        List<String> lista_transicoes = new ArrayList<>();
        List<String> list_alfabeto_sigma = new ArrayList<>();
        List<String> list_alfabeto_gama = new ArrayList<>();

        // HashMaps (Análogos a dicts de python {chave : valor})
        HashMap<String, String> codifica_fita = new HashMap<>();
        HashMap<String, String> codifica_simbolos_fita = new HashMap<>();
        HashMap<String, String> codifica_direcao = new HashMap<>();
        HashMap<String, String> codifica_estado = new HashMap<>();
        HashMap<String, String> codifica_estados_finais = new HashMap<>();

        // Codificação de esquerda e direita
        codifica_direcao.put("D", "1");
        codifica_direcao.put("E", "11");

        // Entrada 1 - Número de estados
        String input_numero_estados = sc.nextLine();
        numero_estados = Integer.parseInt(input_numero_estados);

        if (numero_estados >= 1 && numero_estados <= 20) {
            for (int i = 1; i <= numero_estados; i++) {
                String string_i = String.valueOf(i);
                codifica_estado.put(string_i, concat);
                concat += "1";
            }
        } else {
            System.out.println("Número de estados fora do intervalo permitido (1-20). Encerrando o programa.");
            System.exit(1);
        }

        // Entrada 2 - Alfabeto Σ
        String input_alfabeto_sigma = sc.nextLine();
        String[] alfabeto_sigma = input_alfabeto_sigma.split(" ");
        list_alfabeto_sigma.addAll(Arrays.asList(alfabeto_sigma));
        if (!valida_alfabeto_sigma(input_alfabeto_sigma)) {
            System.out.println("Caracteres não permitidos. Encerrando o programa.");
            System.exit(1);
        } else {
            tamanho_alfabeto_sigma = list_alfabeto_sigma.size();
        }

        // Entrada 3 - Alfabeto Γ
        String input_alfabeto_gama = sc.nextLine();
        String[] alfabeto_gama = input_alfabeto_gama.split(" ");
        list_alfabeto_gama.addAll(Arrays.asList(alfabeto_gama));
        if (!valida_alfabeto_gama(input_alfabeto_gama)) {
            System.out.println("Caracteres não permitidos. Encerrando o programa.");
            System.exit(1);
        } else {
            tamanho_alfabeto_gama = list_alfabeto_gama.size();
        }

        // Adicionando o símbolo $ no início do alfabeto Γ
        if (list_alfabeto_gama.contains("$")) {
            list_alfabeto_gama.remove("$");
            list_alfabeto_gama.add(0, "$");
        }

        // Unindo os alfabetos Σ e Γ
        list_alfabeto_sigma.addAll(list_alfabeto_gama);

        // Ordenando os símbolos
        Collections.sort(list_alfabeto_sigma);

        for (String symbol : list_alfabeto_sigma) {
            codifica_fita.put(symbol, concat2);
            concat2 += "1";
        }

        // Entrada 4 - Estado inicial
        String input_estado_inicial = sc.nextLine();
        int_estado_inicial = Integer.parseInt(input_estado_inicial);

        // Entrada 5 - Estado(s) final(is)
        String input_estados_finais = sc.nextLine();
        int_estados_finais = Integer.parseInt(input_estados_finais);
        // Caso a parte em que estados_finais precisam ser declarados antes do if para que não ocorra falha
        String[] estados_finais = input_estados_finais.split(" ");
        if (int_estados_finais < 1 || int_estados_finais > numero_estados) {
            System.out.println("Estado(s) final(is) fora do intervalo Q. Encerrando o programa.");
            System.exit(1);
        } else {
            for (String estado : estados_finais) {
                codifica_estados_finais.put(estado, concat);
            }
        }

        // Entrada 6 - Número de Transições
        String input_numero_transicoes = sc.nextLine();
        int num_transicoes = Integer.parseInt(input_numero_transicoes);

        if ((num_transicoes < 1) || (num_transicoes > numero_estados * Math.pow(tamanho_alfabeto_sigma, 1) * Math.pow(tamanho_alfabeto_gama, 2))) {
            System.out.println("Número de transiçoes fora do intervalo permitido. Encerrando o programa.");
            System.exit(1);
        }

        // D Linhas - Transições
        for (int i = 0; i < num_transicoes; i++) {
            String transicao = sc.nextLine();
            String[] partesTransicao = transicao.split(" ");
            String transicaoCodificada =
                    codifica_estado.get(partesTransicao[0]) + "0" + // Estado atual
                            codifica_fita.get(partesTransicao[1]) + "0" +  // Símbolo lido
                            codifica_estado.get(partesTransicao[2]) + "0" + // Próximo estado
                            codifica_fita.get(partesTransicao[3]) + "0" +  // Símbolo escrito
                            codifica_direcao.get(partesTransicao[4]); // Direção

            lista_transicoes.add(transicaoCodificada.trim());
        }

        // Exibição:
        for (int i = 1; i <= numero_estados; i++) {
            System.out.println(codifica_estado.get(String.valueOf(i)));
        }

        for (String symbol : list_alfabeto_sigma) {
            if (!codifica_simbolos_fita.containsKey(symbol)) {
                codifica_simbolos_fita.put(symbol, concat3);
                concat3 += '1';
            }
            System.out.println(codifica_simbolos_fita.get(symbol));
        }

        System.out.println(codifica_direcao.get("D"));
        System.out.println(codifica_direcao.get("E"));
        for (String transicao : lista_transicoes) {
            System.out.println(transicao);
        }

        for (String estado : estados_finais) {
            System.out.println(codifica_estado.get(estado));
        }

        // Representação R<M> final
        StringBuilder finalRepresentation = new StringBuilder();
        for (String estado : estados_finais) {
            finalRepresentation.append(codifica_estado.get(estado));
        }
        finalRepresentation.append("00");

        for (String transicao : lista_transicoes) {
            finalRepresentation.append(transicao).append("00");
        }

        if (finalRepresentation.length() >= 2) {
            finalRepresentation.setLength(finalRepresentation.length() - 2);
        }

        System.out.print(finalRepresentation);

        sc.close(); // Fechando o scanner
    }
}
