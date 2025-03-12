// Pacote onde a classe está localizada
package listadesobrenomes;

// Importação de bibliotecas necessárias
import javax.swing.*;
import java.io.*;
import java.util.*;

// Definição da classe Pessoa que implementa Serializable
class Pessoa implements Serializable {

    // Atributos da classe
    String nome; // Nome da pessoa
    String sobrenome; // Sobrenome da pessoa
    int idade; // Idade da pessoa

    // Construtor da classe Pessoa
    public Pessoa(String nome, String sobrenome, int idade) {
        this.nome = nome; // Inicializa o atributo nome
        this.sobrenome = sobrenome; // Inicializa o atributo sobrenome
        this.idade = idade; // Inicializa o atributo idade
    }

    // Formatação da String
    @Override
    public String toString() {
        return nome + " " + sobrenome + ", " + idade + " anos";
    }
}
