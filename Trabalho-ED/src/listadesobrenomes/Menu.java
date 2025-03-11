package listadesobrenomes;

// Importações de bibliotecas necessárias
import javax.swing.*;
import java.io.*;
import java.util.*;

// Definição da classe Menu
public class Menu {

    // Instância de ListaSenhas para manipular a lista de pessoas
    private ListaSobrenome listaPessoas = new ListaSobrenome();

    // Método para exibir o menu principal
    public void exibirMenu() {
        int opcao;
        do {
            // Construção do menu em formato de string
            String menu = "Menu:\n"
                    + "1. Incluir pessoa na LISTA\n"
                    + "2. Deletar pessoa da LISTA\n"
                    + "3. Mostrar LISTA de pessoas\n"
                    + "4. Inserir lista de pessoas numa FILA (ordenada por idade)\n"
                    + "5. Criar N PILHAS para pessoas com o mesmo sobrenome\n"
                    + "6. Inserir lista de pessoas numa ARVORE (ordenada por idade)\n"
                    + "7. Salvar Lista\n"
                    + "0. Sair\n"
                    + "Escolha uma opção:";
            // Obtenção da opção escolhida pelo usuário
            opcao = Integer.parseInt(exibirInput(menu));

            // Executar a opção escolhida
            executarOpcao(opcao);
        } while (opcao != 0);
    }

    // Método para executar a opção escolhida pelo usuário
    private void executarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                incluirPessoa();
                break;
            case 2:
                listaPessoas.deletarPessoa();
                break;
            case 3:
                listaPessoas.mostrarListaPessoas();
                break;
            case 4:
                inserirListaFila();
                break;
            case 5:
                criarPilhasSegundoNome();
                break;
            case 6:
                inserirListaArvore();
                break;
            case 7:
                salvarLista();
                break;
            case 0:
                exibirMensagem("Saindo do programa. Obrigado!", "Saída");
                break;
            default:
                exibirMensagem("Opção inválida", "Erro");
        }
    }

    // Método para incluir uma pessoa na lista
    private void incluirPessoa() {
        String nome = exibirInput("Digite o nome:");
        String sobrenome = exibirInput("Digite o sobrenome:");
        int idade = Integer.parseInt(exibirInput("Digite a idade:"));
        listaPessoas.incluirPessoa(nome, sobrenome, idade);
        exibirMensagem("Pessoa inserida!", "Sucesso");
    }

    // Método para inserir a lista de pessoas em uma fila, ordenada por idade (maior idade na frente)
    private void inserirListaFila() {
        LinkedList<Pessoa> listaPessoas = new LinkedList<>(this.listaPessoas.getListaPessoas());
        FilaPessoas filaPessoas = new FilaPessoas();
        filaPessoas.inserirListaFilaOrdenadaPorIdade(listaPessoas);
        filaPessoas.mostrarFilaPessoas();
    }

    // Método para criar pilhas de pessoas com o mesmo segundo nome
    private void criarPilhasSegundoNome() {
        LinkedList<Pessoa> listaPessoas = new LinkedList<>(this.listaPessoas.getListaPessoas());
        PilhasPessoas pilhasPessoas = new PilhasPessoas();
        pilhasPessoas.criarPilhas(listaPessoas);
        pilhasPessoas.mostrarPilhas();
    }

    // Método para inserir a lista de pessoas em uma árvore ordenada por idade
    private void inserirListaArvore() {
        LinkedList<Pessoa> listaPessoas = new LinkedList<>(this.listaPessoas.getListaPessoas());
        ArvorePessoas arvorePessoas = new ArvorePessoas();
        arvorePessoas.inserirListaArvoreOrdenadaPorIdade(listaPessoas);
        arvorePessoas.mostrarArvoreEmOrdem();
        arvorePessoas.mostrarArvorePreOrdem();
    }

    // Método para salvar a lista de pessoas em um arquivo
    private void salvarLista() {
        String nomeArquivo = exibirInput("Digite o nome do arquivo para salvar a lista:");
        listaPessoas.salvarListaEmArquivo(nomeArquivo);
    }

    // Método para exibir uma mensagem de entrada e obter a resposta do usuário
    private String exibirInput(String mensagem) {
        return JOptionPane.showInputDialog(null, mensagem);
    }

    // Método para exibir uma mensagem gráfica
    private void exibirMensagem(String mensagem, String titulo) {
        JOptionPane.showMessageDialog(null, mensagem, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    // Método principal para iniciar o programa
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.listaPessoas.carregarListaDeArquivo();  // Carregar a lista de pessoas do arquivo ao iniciar
        menu.exibirMenu();  // Exibir o menu principal
    }

    // Classe FilaPessoas
    public class FilaPessoas {

        private PriorityQueue<Pessoa> filaPessoas = new PriorityQueue<>(Comparator.comparingInt(p -> -p.idade));

        // Método para inserir uma lista de pessoas na fila ordenada por idade (maior idade primeiro)
        public void inserirListaFilaOrdenadaPorIdade(LinkedList<Pessoa> listaPessoas) {
            filaPessoas.addAll(listaPessoas);
        }

        // Método para mostrar todas as pessoas na fila
        public void mostrarFilaPessoas() {
            StringBuilder output = new StringBuilder();
            for (Pessoa pessoa : filaPessoas) {
                output.append(pessoa).append("\n");
            }
            JOptionPane.showMessageDialog(null, output.toString(), "Fila de Pessoas", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Classe PilhasPessoas
    public class PilhasPessoas {

        private Map<String, Stack<Pessoa>> pilhas = new HashMap<>();

        // Método para criar pilhas para pessoas com o mesmo segundo nome
        public void criarPilhas(LinkedList<Pessoa> listaPessoas) {
            for (Pessoa pessoa : listaPessoas) {
                pilhas.computeIfAbsent(pessoa.sobrenome, k -> new Stack<>()).push(pessoa);
            }
        }

        // Método para mostrar as pilhas de pessoas
        public void mostrarPilhas() {
            StringBuilder output = new StringBuilder();
            for (Map.Entry<String, Stack<Pessoa>> entry : pilhas.entrySet()) {
                output.append("Pilha para o sobrenome ").append(entry.getKey()).append(":\n");
                for (Pessoa pessoa : entry.getValue()) {
                    output.append(pessoa).append("\n\n");
                }
            }
            JOptionPane.showMessageDialog(null, output.toString(), "Pilhas de Pessoas", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Classe ArvorePessoas
    public class ArvorePessoas {

        private ArvoreNode root;
        private List<String> nomesNaoInseridos = new ArrayList<>();

        // Método para inserir a lista de pessoas na árvore ordenada por idade
        public void inserirListaArvoreOrdenadaPorIdade(LinkedList<Pessoa> listaPessoas) {
            for (Pessoa pessoa : listaPessoas) {
                if (!inserirArvore(root, pessoa)) {
                    nomesNaoInseridos.add(pessoa.nome);
                }
            }
        }

        // Método para inserir uma pessoa na árvore de forma ordenada por idade
        private boolean inserirArvore(ArvoreNode node, Pessoa pessoa) {
            if (node == null) {
                root = new ArvoreNode(pessoa);
                return true;
            }

            if (pessoa.idade < node.pessoa.idade) {
                if (node.esquerda == null) {
                    node.esquerda = new ArvoreNode(pessoa);
                    return true;
                } else {
                    return inserirArvore(node.esquerda, pessoa);
                }
            } else if (pessoa.idade > node.pessoa.idade) {
                if (node.direita == null) {
                    node.direita = new ArvoreNode(pessoa);
                    return true;
                } else {
                    return inserirArvore(node.direita, pessoa);
                }
            }

            return false;  // Retorna false se o nome for repetido
        }

        // Método para mostrar a árvore em ordem
        public void mostrarArvoreEmOrdem() {
            StringBuilder output = new StringBuilder();
            mostrarArvoreInOrder(root, output);
            JOptionPane.showMessageDialog(null, output.toString(), "Árvore de Pessoas (Em Ordem)", JOptionPane.INFORMATION_MESSAGE);
        }

        // Método para mostrar a árvore em pré-ordem
        public void mostrarArvorePreOrdem() {
            StringBuilder output = new StringBuilder();
            mostrarArvorePreOrder(root, output);
            JOptionPane.showMessageDialog(null, output.toString(), "Árvore de Pessoas (Pré-Ordem)", JOptionPane.INFORMATION_MESSAGE);
        }

        // Método para realizar a travessia em ordem da árvore
        private void mostrarArvoreInOrder(ArvoreNode node, StringBuilder output) {
            if (node != null) {
                mostrarArvoreInOrder(node.esquerda, output);
                output.append(node.pessoa).append("\n");
                mostrarArvoreInOrder(node.direita, output);
            }
        }

        // Método para realizar a travessia em pré-ordem da árvore
        private void mostrarArvorePreOrder(ArvoreNode node, StringBuilder output) {
            if (node != null) {
                output.append(node.pessoa).append("\n");
                mostrarArvorePreOrder(node.esquerda, output);
                mostrarArvorePreOrder(node.direita, output);
            }
        }

        // Classe interna para representar um nó da árvore
        private static class ArvoreNode {
            Pessoa pessoa;
            ArvoreNode esquerda, direita;

            public ArvoreNode(Pessoa pessoa) {
                this.pessoa = pessoa;
                this.esquerda = null;
                this.direita = null;
            }
        }
    }
}
