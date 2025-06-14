import java.time.LocalDate;
import java.util.Scanner;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TreeSet<Zona> zonas = new TreeSet<>();
        boolean executando = true;

        while (executando) {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Registrar Zona");
            System.out.println("2. Adicionar Sensor");
            System.out.println("3. Imprimir Relatório");
            System.out.println("4. Finalizar");
            System.out.print("Escolha uma opção: ");
            String opcao = sc.nextLine();

            switch (opcao) {
                case "1":
                    System.out.print("Nome da zona: ");
                    String nome = sc.nextLine();
                    System.out.print("Tipo (urbana/rural): ");
                    String tipo = sc.nextLine().trim().toLowerCase();
                    if (tipo.equals("urbana")) {
                        zonas.add(new Urbana(nome));
                    } else if (tipo.equals("rural")) {
                        zonas.add(new Rural(nome));
                    } else {
                        System.out.println("Tipo inválido.");
                    }
                    break;

                case "2":
                    System.out.print("Nome da zona: ");
                    String nomeZona = sc.nextLine();
                    Zona z = buscarZona(zonas, nomeZona);
                    if (z instanceof Urbana) {
                        System.out.print("ID do sensor: ");
                        String id = sc.nextLine();
                        System.out.print("Data da coleta (AAAA-MM-DD): ");
                        String data = sc.nextLine();
                        System.out.print("Valor AQI: ");
                        double aqi = Double.parseDouble(sc.nextLine());
                        Sensor s = new Sensor(id, LocalDate.parse(data), aqi);
                        ((Urbana) z).adicionarSensor(s);
                    } else if (z == null) {
                        System.out.println("Zona não encontrada.");
                    } else {
                        System.out.println("Não é possível adicionar sensor a uma zona rural.");
                    }
                    break;

                case "3":
                    System.out.print("Nome da zona: ");
                    String nomeRel = sc.nextLine();
                    Zona zona = buscarZona(zonas, nomeRel);
                    if (zona == null) {
                        System.out.println("Zona não encontrada.");
                    } else {
                        System.out.println("\n=== RELATÓRIO DE EMERGÊNCIA AMBIENTAL ===");
                        System.out.println(zona.relatorio());
                        if (zona instanceof Urbana) {
                            if (((Urbana) zona).calcularMedia() > 300) {
                                System.out.println(">>> ALERTA EXTREMO: Média crítica ultrapassada!");
                            }
                        }
                    }
                    break;

                case "4":
                    executando = false;
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        }
        sc.close();
    }

    private static Zona buscarZona(TreeSet<Zona> zonas, String nome) {
        for (Zona z : zonas) {
            if (z.getNome().equalsIgnoreCase(nome)) return z;
        }
        return null;
    }
}
