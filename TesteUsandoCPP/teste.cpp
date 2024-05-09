#include <iostream>
#include <fstream> //biblioteca para manipular arquivos
#include <string>  //biblioteca com funções que facilitam a manipulação de strings
#include <limits>  //Usada para evitar bugs

using namespace std;



string dec_para_unario(int decimal){ //Converte um numero int para seu equivalente em unário
    string unario = "";
    if(decimal == 0){
        return unario;
    }
    for(int i = 1; i <= decimal; i++){
        unario += '1';
    }
    return unario;
}

ifstream& jumpLinha(ifstream& arquivo, int linha){  //Leva o ponteiro do arquivo para uma linha específica do input
    arquivo.clear();
    arquivo.seekg(0, ios::beg); //Voltamos ao começo do arquivo

    for(int i = 0; i < linha; i ++){
        arquivo.ignore(numeric_limits<streamsize>::max(), '\n');    //Sugerido pela documentação da biblioteca ifstream
    }
    
    return arquivo;
}

string getCode(ifstream& arquivo, string simbolo){                  //Recebe um símbolo, retorna sua representação unária.
    string linha;
    int contador = 1;   //Armazena a representação decimal do símbolo
                        //É inicializado como 1 em vez de 0, pois o símbolo 1 é o $.
    
    //Primeiro, vamos checar se é um símbolo de entrada
    jumpLinha(arquivo, 1);
    getline(arquivo, linha);
    for(int i = 0; i < linha.length(); i++){    //Iteramos por todos os caracteres da linha
        if(linha[i] != ' '){
            contador++;
            if(linha[i] == simbolo[0]){         //Encontramos o caractere!
                goto exitLoop;
            }
        }
    }

    //Depois, vamos checar se é um símbolo de fita
    getline(arquivo, linha);
    for(int i = 0; i < linha.length(); i++){    //Iteramos por todos os caracteres da linha
        if(linha[i] != ' ' && linha[i] != '$'){
            contador++;
            if(linha[i] == simbolo[0]){         //Encontramos o caractere!
                goto exitLoop;
            }
        }
    }

    exitLoop:
    return dec_para_unario(contador);     //Convertemos para unário, e retornamos a representação do símbolo!

}




int main(int argc, char* argv[]){ //Argc = n° de parametros + nome do arquivo .cpp     Argv = Matrzi com as strings de entrada. Começa em argv[1] em vez de argv[0].
    if (argc > 2){
        cout << argc;
        cout << endl << "Muitos argumentos! Insira apenas o nome do arquivo de input.txt" << endl;
        exit(1);
    }else if(argc < 2){
        cout << argc;
        cout << endl << "Nenhum argumento encontrado! Insira, como argumento de linha de comando, o nome do arquivo input.txt!";
        exit(1);
    }
    printf(argv[1]);

    //Argumentos lidos com sucesso!

    //Tentamos abrir o arquio, e criamos um ponteiro "arquivo" para ele
    ifstream arquivo(argv[1]);

    //Checamos se a abertura teve sucesso
    if(!arquivo.is_open()){
        cout << endl << "Arquivo nao foi aberto! Veja se o arquivo inserido realmente existe." << endl;
        exit(1);
    }

    //DEBUG -- Printamos o arquivo na tela:
    int nLinhas = 1, i = 0, tamanhoAlfabeto = 0; //Numero de linhas do arquivo      tamanhoAlfabeto = n° de chars diferentes do alfabeto    i = variável auxiliar usada em loops for. Às vezes é utilizado para transmitir dados entre 2 loops diferentes
    string linha, saida;    //Linha = Linhas lidas do input     Saida = output final do programa

    while(getline(arquivo, linha)){ //Vamos lendo cada linha do arquivo
        cout << endl << "linha " << nLinhas << " = " << linha;
        nLinhas++;
    }

    //Agora voltamos para o começo da linha
    arquivo.clear();
    arquivo.seekg(0, ios::beg);



    //Hora de começar o programa!!!!!!!!
    //Input Linha 1 - Inteiro 1 <= Q <= 20; indica o n° de estados da máquina
    getline(arquivo, linha);            
    int Q = stoi(linha);
    if(Q > 20 || Q < 1){                //Tratamento de dados
        cout << endl << "Valor invalido na linha 1! Q é inteira, tal que: {1 <= Q <= 20}" << endl;
    }

    //Output 1: * Q linhas representando cada estado, em unário
    for(i = 1; i <= Q; i++){ 
        saida += dec_para_unario(i);    //Convertemos cada estado Q para um número unário, e escrevemos na saída
        saida += "\n";
    }
    cout << endl << "SAIDA EH " << endl << saida << endl;



    //Input Linha 2 - Alfabeto Σ, constituidos de digitos (0 a 9) e letras (a até z). Separados por um espaço
    //Input Linha 3 - Alfabeto Γ símbolos de fita. Apenas letras maiúsculas (A a Z). Símbolos pertencentes a Σ não estão incluídos. $ representa espaços em branco, e deve estar incluido obrigatoriamente no começo da linha. Separados por um espaço.
    getline(arquivo, linha);    
    for(unsigned long long lint = 0; lint < linha.length(); lint++){    //For linha 2
        if(linha[lint] != ' '){ //Se for um caractere, e nao um espaço em branco...
            tamanhoAlfabeto++;
        }
    }
    getline(arquivo, linha);
    for(unsigned long long lint = 0; lint < linha.length(); lint++){    //For linha 3
        if(linha[lint] != ' '){ //Se for um caractere, e nao um espaço em branco...
            tamanhoAlfabeto++;
        }
    }

    //Output 2: * |Σ|+|Γ|linhas conterão a representação de cada um dos símbolos de Σ e Γ;
    for(i = 1; i <= tamanhoAlfabeto; i++){ 
        saida += dec_para_unario(i);    //Convertemos cada estado Q para um número unário, e escrevemos na saída
        saida += "\n";
    }
    cout << endl << "SAIDA 2 EH " << endl << saida << endl;


    //Output 3: * 2 linhas para as direções, a primeira sendo “Direita” e a segunda sendo “Esquerda”;;
    saida = saida + '1' + "\n" + "11" + "\n";
    cout << endl << "SAIDA 3 EH " << endl << saida << endl;


    //Input Linha 6 - n° de transições da MT.
    //Pulamos pra linha 6:
    jumpLinha(arquivo, 5); //LEMBRETE - Devemos pular para a linha 5, pois o getline vai ler a linha seguinte.
    getline(arquivo, linha);
    int nTransicoes = stoi(linha);
    cout << endl << "Possuimos " << nTransicoes << " transicoes." << endl;


    //Output 4: * t linhas que representarão cada uma das transições;
    //          Formato EA + 0 + SL + 0 + PE + 0 + SE + 0 + Dir
    //          Nota: a ordem dos caracteres: $ + Σ + Γ.
    //          Apesar de $ pertencer a Γ, ele é representado pelo código "1".
    //              Não está definido nas especificações do trabalho, mas isso fica implícito nos exemplos 1 e 2.

    string aux; //String auxiliar
    int pos;    //Vai armazenar a posição de determinados caracteres dentro de uma string

    //Temos n Transições. Loop for para pegar todas elas usando jumpLinha() e getline(), a partir da linha 6:
    for(i = 6; i < (nTransicoes + 6); i++){
        jumpLinha(arquivo, i);
        getline(arquivo, linha);
        cout << endl << "Linha inicial: - " << linha << endl;
        //Leitura do EA (Estado Atual)
            //Os estados vão de 1 a 20, e a sua representação unária é equivalente ao seu número. Logo, só precisamos pegar o nome do estado e converter ele para unário.

        // EA  -  Estado Anterior
        pos = linha.find(" ");
        aux = linha.substr(0, pos);     //Pegamos o EA e colocamos ele na substring aux
        linha.erase(0, pos + 1);            //Apagamos o EA da string original (o +1 serve para apagar o espaço também)

        cout << endl << "EA - " << aux << endl;
        cout << "Linha - " << linha;
        saida += dec_para_unario(stoi(aux));//Colocamos o código unário do EA na saida


        saida += '0';                       //E um 0 para separar
        // SL  -  Símbolo Lido
        pos = linha.find(" ");
        aux = linha.substr(0, pos);     //Pegamos o SL e colocamos ele na substring aux
        linha.erase(0, pos + 1);            //Apagamos o SL da string original
        if(aux == "$"){                 //Caso especial para garantir que $ seja representado como "1".
            saida += '1';
        }else{
            saida += getCode(arquivo, aux); //Obtemos o código de SL, e inserimos na saída
        }











        saida += '0';
        // PE  -  Próximo Estado
        pos = linha.find(" ");
        aux = linha.substr(0, pos);     //Pegamos o PE e colocamos ele na substring aux
        linha.erase(0, pos + 1);            //Apagamos o PE da string original


        saida += '0';
        // SE  -  Símbolo Escrito
        pos = linha.find(" ");
        aux = linha.substr(0, pos);     //Pegamos o SE e colocamos ele na substring aux
        linha.erase(0, pos + 1);            //Apagamos o SE da string original


        saida += '0';
        // Dir  -  Direção
        //Na string linha só sobrou a direção




        saida += "\n";
    }

        cout << endl << "SAIDA" << endl << saida;





    //E fechamos o arquivo
    arquivo.close();
    return 0;
}