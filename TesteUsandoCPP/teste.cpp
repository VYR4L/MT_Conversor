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

ifstream& JumpLinha(ifstream& arquivo, int linha){  //Leva o ponteiro do arquivo para uma linha específica do input
    arquivo.clear();
    arquivo.seekg(0, ios::beg); //Voltamos ao começo do arquivo

    for(int i = 0; i < linha; i ++){
        arquivo.ignore(numeric_limits<streamsize>::max(), '\n');    //Sugerido pela documentação da biblioteca ifstream
    }
    
    return arquivo;
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
    JumpLinha(arquivo, 5); //LEMBRETE - Devemos pular para a linha 5, pois o getlina vai ler a linha seguinte.
    getline(arquivo, linha);
    cout << endl << "Linha 6 eh" << endl << linha << endl;





    //E fechamos o arquivo
    arquivo.close();
    return 0;
}