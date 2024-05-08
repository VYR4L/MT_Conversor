import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String concat = "1";
        String concat2 = "1";

        int int_user_input = 0;
        int int_user_input2 = 0;
        int int_user_input3 = 0;
        int int_user_input4 = 0;
        int tamanho_alfabetos = 0;

        List<String> dois_alfabetos = new ArrayList<>();
        List<String> lista_transicoes = new ArrayList<>();
        List<String> list_alfabeto_gama = new ArrayList<>();
        list_alfabeto_gama.add("$");

        // Isso funciona como se fosse um dict de python {chave: valor}, nesse caso (chave, valor)
        // Preferi trabalhar com String, String porque depois dá pra concatenar e não será necessário converter
        HashMap<String, String> codifica_fita = new HashMap<>(); // Iterável
        HashMap<String, String> codifica_simbolos_fita = new HashMap<>();
        HashMap<String, String> codifica_direcao = new HashMap<>();
        HashMap<String, String> codifica_estado = new HashMap<>(); // Iterável

        codifica_direcao.put("D", "1");
        codifica_direcao.put("E", "11");

        // Lendo do arquivo
        File file = new File("C:\\Users\\fkzza\\Documents\\Trabalho_TC\\untitled\\src\\entrada.txt");
        try {
            Scanner sc = new Scanner(file);

            // Número de Estados
            String user_input = sc.nextLine();
            int_user_input = Integer.parseInt(user_input);

            // chave valor (estado, codificação):
            for (int i = 1; i <= int_user_input; i++) {
                String string_i = String.valueOf(i);
                codifica_estado.put(string_i, concat);
                concat += "1";
            }

            // Alfabeto Σ
            String user_input2 = sc.nextLine();
            String[] alfabeto_sigma = user_input2.split(" ");
            List<String> list_alfabeto_sigma = Arrays.asList(alfabeto_sigma);


            // Alfabeto Γ
            String user_input3 = sc.nextLine();
            String[] alfabeto_gama = user_input3.split(" ");
            List<String> list_alfabeto_gama_c = Arrays.asList(alfabeto_gama);
            list_alfabeto_gama.addAll(list_alfabeto_gama_c);


            tamanho_alfabetos = list_alfabeto_gama.size() + list_alfabeto_gama_c.size();
            dois_alfabetos.addAll(list_alfabeto_gama);
            dois_alfabetos.addAll(list_alfabeto_sigma);

            for (int i = 0; i < tamanho_alfabetos; i++) {
                codifica_fita.put(dois_alfabetos.get(i), concat2);
                concat2 += "1";
            }

            // Estado inicial
            String user_input4 = sc.nextLine();
            int_user_input2 = Integer.parseInt(user_input4);


            // Estado(s) final(is)
            String user_input5 = sc.nextLine();
            int_user_input3 = Integer.parseInt(user_input5);


            // Número de transições
            String user_input6 = sc.nextLine();
            int num_transicoes = Integer.parseInt(user_input6);


            // Saída:
            System.out.println("Representação R<z>:");

            // Estados
            System.out.println("Estados:");
            for (int i = 1; i <= int_user_input; i++) {
                System.out.println(codifica_estado.get(String.valueOf(i)));
            }

            // Símbolos de Σ e Γ
            System.out.println("Símbolos de Σ e Γ:");
            for (String simbolo : dois_alfabetos) {
                if (!codifica_simbolos_fita.containsKey(simbolo)) {
                    codifica_simbolos_fita.put(simbolo, concat2);
                    concat2 += "1";
                }
                System.out.println(codifica_simbolos_fita.get(simbolo));
            }

            // Direções
            System.out.println("Direções:");
            System.out.println(codifica_direcao.get("D"));
            System.out.println(codifica_direcao.get("E"));

            System.out.println("Transições:");
            for (int i = 0; i < num_transicoes; i++) {
                String transicao = sc.nextLine();
                String[] partesTransicao = transicao.split(" ");

                // Codifica cada parte da transição individualmente
                String transicaoCodificada =
                        codifica_estado.get(partesTransicao[0]) + "0" + // Estado atual
                        codifica_fita.get(partesTransicao[1]) + "0" +  // Símbolo lido
                        codifica_estado.get(partesTransicao[2]) + "0" + // Próximo estado
                        codifica_fita.get(partesTransicao[3]) + "0" +  // Símbolo escrito
                        codifica_direcao.get(partesTransicao[4]); // Direção

                lista_transicoes.add(transicaoCodificada.trim());
            }
            System.out.println(lista_transicoes);

            // Estados finais
            System.out.println("Estados Finais:");
            System.out.println(int_user_input3);

            // Representação R<M> final
            System.out.println("Representação R<M> final");

            sc.close(); // Fechando o scanner

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado: " + e.getMessage());
        }
    }
}
