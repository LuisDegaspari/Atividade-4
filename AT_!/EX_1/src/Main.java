import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.TreeSet;

public class Main {
    private static TreeSet<Zona> zonas = new TreeSet<>();

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
        } catch (Exception ex) {
            System.err.println("Falha ao carregar");
        }

        JFrame frame = new JFrame("Sistema de Monitoramento");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("Sistema de Monitoramento", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        String[] columnNames = {"Zona", "Tipo", "Sensores", "Última Leitura", "Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable zonasTable = new JTable(tableModel);
        zonasTable.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(zonasTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JButton btnRegistrar = new JButton("Criar Zona");
        btnRegistrar.setPreferredSize(new Dimension(180, 40));
        btnRegistrar.addActionListener(e -> {
            CriarZona(frame, tableModel);
        });
        buttonPanel.add(btnRegistrar);

        JButton btnAdicionar = new JButton("Adicionar Sensor");
        btnAdicionar.setPreferredSize(new Dimension(180, 40));
        btnAdicionar.addActionListener(e -> {
            adicionarSensor(frame);
        });
        buttonPanel.add(btnAdicionar);

        JButton btnRelatorio = new JButton("Relatório");
        btnRelatorio.setPreferredSize(new Dimension(180, 40));
        btnRelatorio.addActionListener(e -> {
            Relatorio(frame);
        });
        buttonPanel.add(btnRelatorio);

        JButton btnSair = new JButton("Sair");
        btnSair.setPreferredSize(new Dimension(180, 40));
        btnSair.addActionListener(e -> System.exit(0));
        buttonPanel.add(btnSair);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static void CriarZona(JFrame parent, DefaultTableModel tableModel) {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        
        JTextField nomeField = new JTextField();
        JComboBox<String> tipoCombo = new JComboBox<>(new String[]{"Urbana", "Rural"});
        
        panel.add(new JLabel("Nome da Zona:"));
        panel.add(nomeField);
        panel.add(new JLabel("Tipo:"));
        panel.add(tipoCombo);
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        
        int result = JOptionPane.showConfirmDialog(
            parent, panel, "Registrar Nova Zona", 
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            String nome = nomeField.getText().trim();
            String tipo = (String) tipoCombo.getSelectedItem();
            
            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(parent, "O nome da zona não pode estar vazio!", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Zona zona;
            if (tipo.equals("Urbana")) {
                zona = new Urbana(nome);
            } else {
                zona = new Rural(nome);
            }
            
            zonas.add(zona);
            tableModel.addRow(new Object[]{nome, tipo, "0", "-", "Ativa"});
            JOptionPane.showMessageDialog(parent, "Zona registrada com sucesso!", 
                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static void adicionarSensor(JFrame parent) {
        if (zonas.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Não há zonas cadastradas!", 
                "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Urbana zonaUrbana = selecionarZonaUrbana(parent);
        if (zonaUrbana == null) return;
        
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        
        JTextField idField = new JTextField();
        JTextField dataField = new JTextField();
        JTextField aqiField = new JTextField();
        
        panel.add(new JLabel("ID do Sensor:"));
        panel.add(idField);
        panel.add(new JLabel("Data (DD/MM/AAAA):"));
        panel.add(dataField);
        panel.add(new JLabel("Valor AQI:"));
        panel.add(aqiField);
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));
        
        int result = JOptionPane.showConfirmDialog(
            parent, panel, "Adicionar Sensor à Zona " + zonaUrbana.getNome(), 
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                String id = idField.getText().trim();
                String data = dataField.getText().trim();
                double aqi = Double.parseDouble(aqiField.getText().trim());
                
                if (id.isEmpty() || data.isEmpty()) {
                    throw new IllegalArgumentException("Campos não podem estar vazios");
                }
                
                Sensor sensor = new Sensor(id, data, aqi);
                zonaUrbana.adicionarSensor(sensor);
                JOptionPane.showMessageDialog(parent, "Sensor adicionado com sucesso!", 
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(parent, "Valor AQI inválido!", 
                    "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(parent, e.getMessage(), 
                    "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static Urbana selecionarZonaUrbana(JFrame parent) {
        java.util.List<Urbana> urbanas = new java.util.ArrayList<>();
        for (Zona zona : zonas) {
            if (zona instanceof Urbana) {
                urbanas.add((Urbana) zona);
            }
        }
        
        if (urbanas.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Não há zonas urbanas cadastradas!", 
                "Erro", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        
        String[] opcoes = new String[urbanas.size()];
        for (int i = 0; i < urbanas.size(); i++) {
            opcoes[i] = urbanas.get(i).getNome();
        }
        
        String selecionada = (String) JOptionPane.showInputDialog(
            parent, "Selecione a zona urbana:", "Selecionar Zona",
            JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[0]);
        
        if (selecionada == null) return null;
        
        for (Urbana zona : urbanas) {
            if (zona.getNome().equals(selecionada)) {
                return zona;
            }
        }
        
        return null;
    }

    private static void Relatorio(JFrame parent) {
        if (zonas.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "Não há zonas cadastradas!", 
                "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String[] opcoes = new String[zonas.size()];
        int i = 0;
        for (Zona zona : zonas) {
            opcoes[i++] = zona.getNome();
        }
        
        String selecionada = (String) JOptionPane.showInputDialog(
            parent, "Selecione a zona:", "Relatório",
            JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[0]);
        
        if (selecionada == null) return;
        
        for (Zona zona : zonas) {
            if (zona.getNome().equals(selecionada)) {
                JTextArea textArea = new JTextArea(15, 40);
                textArea.setEditable(false);
                textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
                
                textArea.append("=== RELATÓRIO DE EMERGÊNCIA AMBIENTAL ===\n\n");
                textArea.append(zona.relatorio() + "\n\n");
                
                if (zona instanceof Urbana) {
                    Urbana urbana = (Urbana) zona;
                    if (urbana.calcularMedia() > 300) {
                        textArea.append(">>> ALERTA EXTREMO: Média crítica ultrapassada!\n");
                    }
                }
                
                JScrollPane scrollPane = new JScrollPane(textArea);
                JOptionPane.showMessageDialog(parent, scrollPane, 
                    "Relatório - " + zona.getNome(), JOptionPane.PLAIN_MESSAGE);
                return;
            }
        }
    }
}