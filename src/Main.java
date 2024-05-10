import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String concat = "1";
        String concat2 = "1";
        String concat3 = "1";

        int numero_estados = 0;
        int int_user_input2 = 0;
        int int_user_input3 = 0;
        int int_user_input4 = 0;
        int tamanho_alfabetos = 0;

        List<String> lista_transicoes = new ArrayList<>();
        List<String> list_alfabeto_sigma = new ArrayList<>();
        List<String> list_alfabeto_gama = new ArrayList<>();

        HashMap<String, String> codifica_fita = new HashMap<>();
        HashMap<String, String> codifica_simbolos_fita = new HashMap<>();
        HashMap<String, String> codifica_direcao = new HashMap<>();
        HashMap<String, String> codifica_estado = new HashMap<>();
        HashMap<String, String> codifica_estados_finais = new HashMap<>();

        codifica_direcao.put("D", "1");
        codifica_direcao.put("E", "11");

        File file = new File("C:\\Users\\fkzza\\Documents\\Trabalho_TC\\untitled\\src\\entrada.txt");
        try {
            Scanner sc = new Scanner(file);

            String user_input = sc.nextLine();
            numero_estados = Integer.parseInt(user_input);

            for (int i = 1; i <= numero_estados; i++) {
                String string_i = String.valueOf(i);
                codifica_estado.put(string_i, concat);
                concat += "1";
            }

            String user_input2 = sc.nextLine();
            String[] alfabeto_sigma = user_input2.split(" ");
            list_alfabeto_sigma.addAll(Arrays.asList(alfabeto_sigma));

            String user_input3 = sc.nextLine();
            String[] alfabeto_gama = user_input3.split(" ");
            list_alfabeto_gama.addAll(Arrays.asList(alfabeto_gama));

            // Adicionando o símbolo $ no início do alfabeto Γ
            if (list_alfabeto_gama.contains("$")) {
                list_alfabeto_gama.remove("$");
                list_alfabeto_gama.add(0, "$");
            }

            // Unindo os alfabetos Σ e Γ
            list_alfabeto_sigma.addAll(list_alfabeto_gama);

            // Ordenando os símbolos de acordo com a regra
            Collections.sort(list_alfabeto_sigma);

            for (String symbol : list_alfabeto_sigma) {
                codifica_fita.put(symbol, concat2);
                concat2 += "1";
            }

            String user_input4 = sc.nextLine();
            int_user_input2 = Integer.parseInt(user_input4);

            String user_input5 = sc.nextLine();
            String[] estados_finais = user_input5.split(" ");
            for (String estado : estados_finais) {
                codifica_estados_finais.put(estado, concat);
            }

            String user_input6 = sc.nextLine();
            int num_transicoes = Integer.parseInt(user_input6);

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

            // Transições
            for (String transicao : lista_transicoes) {
                finalRepresentation.append(transicao).append("00");
            }

            if (finalRepresentation.length() >= 2) {
                finalRepresentation.setLength(finalRepresentation.length() - 2);
            }

            System.out.println(finalRepresentation);

            sc.close(); // Fechando o scanner

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado: " + e.getMessage());
        }
    }
}
