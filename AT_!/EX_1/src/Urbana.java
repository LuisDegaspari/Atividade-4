import java.util.ArrayList;
import java.util.List;

public class Urbana extends Zona {
    private List<Sensor> sensores;

    public Urbana(String nome) {
        super(nome);
        this.sensores = new ArrayList<>();
    }

    public void adicionarSensor(Sensor sensor) {
        sensores.add(sensor);
    }

    public double calcularTotal() {
        double total = 0;
        for (Sensor s : sensores) {
            total += s.getValorAQI();
        }
        return total;
    }

    public double calcularMedia() {
        if (sensores.isEmpty()) return 0;
        return calcularTotal() / sensores.size();
    }

    public String classificarNivelEmergencia() {
        double media = calcularMedia();
        if (media <= 50) return "Sem risco";
        else if (media <= 100) return "Monitoramento intensificado";
        else if (media <= 150) return "Alerta para grupos sensíveis";
        else if (media <= 200) return "Alerta Amarelo";
        else if (media <= 300) return "Alerta Laranja";
        else return "Alerta Vermelho (emergência total)";
    }

    @Override
    public String relatorio() {
        double total = calcularTotal();
        double media = calcularMedia();
        String nivel = classificarNivelEmergencia();

        StringBuilder sb = new StringBuilder();
        sb.append("Zona: ").append(getNome());
        sb.append("\nTotal semanal: ").append(String.format("%.2f", total));
        sb.append("\nMédia semanal: ").append(String.format("%.2f", media));
        sb.append("\nNível de emergência: ").append(nivel);

        if (nivel.equals("Alerta Vermelho (emergência total)")) {
            sb.append("\n>>> Ação imediata recomendada: evacuação ou restrição de atividades externas.");
        }

        return sb.toString();
    }
}
