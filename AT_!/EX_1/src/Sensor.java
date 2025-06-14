import java.time.LocalDate;

public class Sensor {
    private String id;
    private LocalDate dataColeta;
    private double valorAQI;

    public Sensor(String id, LocalDate dataColeta, double valorAQI) {
        this.id = id;
        this.dataColeta = dataColeta;
        this.valorAQI = valorAQI;
    }

    public String getId() {
        return id;
    }

    public LocalDate getDataColeta() {
        return dataColeta;
    }

    public double getValorAQI() {
        return valorAQI;
    }

    @Override
    public String toString() {
        return "Sensor ID: " + id + ", Data: " + dataColeta + ", AQI: " + valorAQI;
    }
}
