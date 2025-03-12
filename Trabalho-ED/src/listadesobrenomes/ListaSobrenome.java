// Pacote onde a classe está localizada
package listadesobrenomes;

// Importações de bibliotecas necessárias
import javax.swing.*;
import java.io.*;
import java.util.*;

// Definição da classe ListaSenhas que implementa Serializable
public class ListaSobrenome implements Serializable {

    // Lista encadeada de pessoas
    private LinkedList<Pessoa> listaPessoas = new LinkedList<>();

    // Método para incluir uma nova pessoa na lista
    public void incluirPessoa(String nome, String sobrenome, int idade) {
        Pessoa novaPessoa = new Pessoa(nome, sobrenome, idade);
        listaPessoas.addLast(novaPessoa);
    }

    // Método para deletar a última pessoa da lista, se existir
    public void deletarPessoa() {
        if (!listaPessoas.isEmpty()) {
            listaPessoas.removeLast();
        }
    }

    // Método para mostrar todas as pessoas na lista
    public void mostrarListaPessoas() {
        // Construção de uma representação da lista em formato de string
        StringBuilder output = new StringBuilder();
        for (Pessoa pessoa : listaPessoas) {
            output.append(pessoa).append("\n");
        }
        // Exibir a lista de pessoas em uma mensagem gráfica
        exibirMensagem(output.toString(), "Lista de Pessoas");
    }

    // Método para salvar a lista de pessoas em um arquivo
    public void salvarListaEmArquivo(String nomeArquivo) {
        // Janela de seleção de arquivo para salvar
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar Lista de Pessoas");
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            // Obter o arquivo selecionado pelo usuário
            File arquivoSelecionado = fileChooser.getSelectedFile();
            String caminhoArquivo = arquivoSelecionado.getAbsolutePath();

            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(caminhoArquivo))) {
                // Escrever a lista de pessoas no arquivo
                outputStream.writeObject(listaPessoas);
                exibirMensagem("Lista salva!", "Sucesso");
            } catch (IOException e) {
                exibirMensagem("Erro ao salvar a lista: " + e.getMessage(), "Erro");
            }
        }
    }

    // Método para carregar a lista de pessoas de um arquivo
    public void carregarListaDeArquivo() {
        // Janela de seleção de arquivo para carregar
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Carregar Lista de Pessoas");
        int userSelection = fileChooser.showOpenDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            // Obter o arquivo selecionado pelo usuário
            File arquivoSelecionado = fileChooser.getSelectedFile();
            String caminhoArquivo = arquivoSelecionado.getAbsolutePath();

            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(caminhoArquivo))) {
                // Ler a lista de pessoas do arquivo
                listaPessoas = (LinkedList<Pessoa>) inputStream.readObject();
                exibirMensagem("Lista carregada!", "Sucesso");
            } catch (IOException | ClassNotFoundException e) {
                exibirMensagem("Erro ao carregar a lista " + e.getMessage(), "Erro");
            }
        }
    }

    // Método para obter a lista de pessoas
    public LinkedList<Pessoa> getListaPessoas() {
        return listaPessoas;
    }

    // Método privado para exibir uma mensagem gráfica
    private static void exibirMensagem(String mensagem, String titulo) {
        JOptionPane.showMessageDialog(null, mensagem, titulo, JOptionPane.INFORMATION_MESSAGE);
    }
}
