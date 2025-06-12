import java.util.Objects;

public abstract class Zona implements Comparable<Zona> {
    private String nome;

    public Zona(String nome) {
        this.nome = nome;
    }

    public String getNome() { return nome; }

    public abstract String relatorio();

    @Override
    public int compareTo(Zona outra) {
        return this.nome.compareTo(outra.nome);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zona zona = (Zona) o;
        return Objects.equals(nome, zona.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }
}