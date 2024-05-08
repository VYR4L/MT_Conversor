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

        HashMap<String, String> codifica_direcao = new HashMap<>();
        codifica_direcao.put("D", "1");
        codifica_direcao.put("E", "11");

        HashMap<String, String> codifica_estado = new HashMap<>(); // Iterável

        Scanner sc = new Scanner(System.in);

        // Entrada:
        System.out.println("Inisira as informações:");

        System.out.println("Número de Estados:");
        String user_input = sc.nextLine();
        int_user_input = Integer.parseInt(user_input);

        // chave valor (estado, codificação):
        for (int i = 1; i <= int_user_input; i++) {
            String string_i = String.valueOf(i);
            codifica_estado.put(string_i, concat);
            concat += concat;
        }

        System.out.println("Alfabeto Σ: (0 a 9 e 'a' até 'z', separados por espaço)");
        String user_input2 = sc.nextLine();
        String[] alfabeto_sigma = user_input2.split(" ");
        List<String> list_alfabeto_sigma = Arrays.asList(alfabeto_sigma);

        System.out.println("Alfabeto Γ ('A' até 'Z', separados por espaço):");
        String user_input3 = sc.nextLine();
        String[] alfabeto_gama = user_input3.split(" ");
        List<String> list_alfabeto_gama_c = Arrays.asList(alfabeto_gama);
        list_alfabeto_gama.addAll(list_alfabeto_gama_c);

        tamanho_alfabetos = list_alfabeto_gama.size() + list_alfabeto_gama_c.size();
        dois_alfabetos.addAll(list_alfabeto_gama);
        dois_alfabetos.addAll(list_alfabeto_sigma);

        for (int i = 0; i < tamanho_alfabetos; i++) {
            if (dois_alfabetos.get(i).equals("$")) {
                codifica_fita.put(dois_alfabetos.get(i), "0");
            }
            else {
                codifica_fita.put(dois_alfabetos.get(i), concat2);
                concat2 += concat2;
            }
        }

        System.out.println("Estado inicial:");
        String user_input4 = sc.nextLine();
        int_user_input2 = Integer.parseInt(user_input);

        System.out.println("Estado(s) final(is):");
        String user_input5 = sc.nextLine();
        int_user_input3 = Integer.parseInt(user_input);

        System.out.println("Número de transições:");
        String user_input6 = sc.nextLine();
        int_user_input4 = Integer.parseInt(user_input);

        for (int i = 0; i <= int_user_input4; i++) {
            String transicoes = sc.nextLine();
            lista_transicoes.add(transicoes);
        }

        // TODO Saída:

    }
}