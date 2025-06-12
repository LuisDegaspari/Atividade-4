public class Rural extends Zona {
    public Rural(String nome) {
        super(nome);
    }

    @Override
    public String relatorio() {
        return getNome() + "\n>>> Zona sem sensores instalados. Monitoramento indireto via satélite.";
    }
}