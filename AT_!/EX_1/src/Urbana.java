import java.util.ArrayList;
import java.util.List;

public class Urbana extends Zona implements Emergencia {
    private List<Sensor> sensores;

    public Urbana(String nome) {
        super(nome);
        this.sensores = new ArrayList<>();
    }

    public void adicionarSensor(Sensor sensor) {
        sensores.add(sensor);
    }

    public double calcularTotal() {
        return sensores.stream().mapToDouble(Sensor::getValorAQI).sum();
    }

    public double calcularMedia() {
        if (sensores.isEmpty()) return 0;
        return calcularTotal() / sensores.size();
    }

    @Override
    public String classificarNivelEmergencia() {
        double media = calcularMedia();
        
        if (media <= 50) return "Sem risco";
        if (media <= 100) return "Monitoramento intensificado";
        if (media <= 150) return "Alerta para grupos sensíveis";
        if (media <= 200) return "Alerta Amarelo";
        if (media <= 300) return "Alerta Laranja";
        return "Alerta Vermelho (emergência total)";
    }

    @Override
    public String relatorio() {
        StringBuilder sb = new StringBuilder();
        sb.append("Zona: ").append(getNome()).append("\n");
        
        if (sensores.isEmpty()) {
            sb.append(">>> Zona sem dados de sensores disponíveis.");
        } else {
            sb.append("Total semanal: ").append(String.format("%.2f", calcularTotal())).append("\n");
            sb.append("Média semanal: ").append(String.format("%.2f", calcularMedia())).append("\n");
            sb.append("Nível de emergência: ").append(classificarNivelEmergencia());
            
            if (calcularMedia() > 300) {
                sb.append("\n>>> Ação imediata recomendada: evacuação ou restrição de atividades externas.");
            }
        }
        
        return sb.toString();
    }
}