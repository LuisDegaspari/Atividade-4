public class Rural extends Zona {

    public Rural(String nome) {
        super(nome);
    }

    @Override
    public String relatorio() {
        return "Zona: " + getNome() +
               "\n>>> Zona com nenhum sensor instalado.";
    }
}
