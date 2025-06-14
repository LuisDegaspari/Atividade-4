public abstract class Zona implements Comparable<Zona>, Emergencia {
    private String nome;

    public Zona(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public int compareTo(Zona outra) {
        return this.nome.compareToIgnoreCase(outra.nome);
    }

    @Override
    public abstract String relatorio();
}
