package ArvoreTeste;
import javax.swing.JOptionPane;

public class Aluno implements Comparable{
    private String nome;
    private int idade;
    private float nota;
    private String turma;
    
    public  Aluno(String no, int id)
    {
      this.nome = no;
      this.idade = id;
    } 
   
    @Override
    public int compareTo(Object outro)
    {
        if(this.idade < ((Aluno)outro).getIdade())
            return -1;
        
        if(this.idade > ((Aluno)outro).getIdade())
            return 1;
        
        return 0;
    }
     
    public String toString()
    {
         return this.getNome() + " - " + this.idade;
    }

    public boolean equals(Aluno outro)
    {
        return this.nome.equals( outro.getNome() );
    }
   
    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }
    
    public int getIdade() {
        return idade;
    }
    
    public void setIdade(int idade) {
        this.idade = idade;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }
}