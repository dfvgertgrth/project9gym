import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Member {
    int id;
    String name;
    int age;
    String membershipType;

    Member(int id, String name, int age, String membershipType) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.membershipType = membershipType;
    }
}

public class Cedov9 extends JFrame {
    private JTextField txtId, txtName, txtAge;
    private JComboBox<String> cmbMembership;
    private DefaultTableModel model;
    private JTable table;
    private ArrayList<Member> members = new ArrayList<>();

    public Cedov9() {
        setTitle("🏋️ Система управления фитнес-клубом");
        setSize(750, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        
        // Цветовая схема
        Color bgColor = new Color(240, 248, 255);
        Color btnColor = new Color(52, 152, 219);
        Color btnHoverColor = new Color(41, 128, 185);
        getContentPane().setBackground(bgColor);

        // Заголовок
        JLabel titleLabel = new JLabel("🏋️ УПРАВЛЕНИЕ КЛИЕНТАМИ ФИТНЕС-КЛУБА", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(44, 62, 80));
        titleLabel.setBounds(150, 5, 450, 30);
        add(titleLabel);

        // Labels and Inputs
        JLabel lblId = new JLabel("ID клиента:");
        lblId.setFont(new Font("Arial", Font.BOLD, 12));
        lblId.setBounds(20, 50, 100, 25);
        add(lblId);
        txtId = new JTextField();
        txtId.setBounds(120, 50, 150, 25);
        add(txtId);

        JLabel lblName = new JLabel("Имя:");
        lblName.setFont(new Font("Arial", Font.BOLD, 12));
        lblName.setBounds(20, 90, 100, 25);
        add(lblName);
        txtName = new JTextField();
        txtName.setBounds(120, 90, 150, 25);
        add(txtName);

        JLabel lblAge = new JLabel("Возраст:");
        lblAge.setFont(new Font("Arial", Font.BOLD, 12));
        lblAge.setBounds(20, 130, 100, 25);
        add(lblAge);
        txtAge = new JTextField();
        txtAge.setBounds(120, 130, 150, 25);
        add(txtAge);

        JLabel lblMembership = new JLabel("Абонемент:");
        lblMembership.setFont(new Font("Arial", Font.BOLD, 12));
        lblMembership.setBounds(20, 170, 100, 25);
        add(lblMembership);
        String[] memberships = {"Месячный", "Квартальный", "Годовой"};
        cmbMembership = new JComboBox<>(memberships);
        cmbMembership.setBounds(120, 170, 150, 25);
        cmbMembership.setBackground(Color.WHITE);
        add(cmbMembership);

        // Buttons
        JButton btnAdd = createStyledButton("➕ Добавить", btnColor);
        btnAdd.setBounds(20, 220, 110, 35);
        add(btnAdd);
        
        JButton btnUpdate = createStyledButton("✏️ Обновить", new Color(241, 196, 15));
        btnUpdate.setBounds(140, 220, 110, 35);
        add(btnUpdate);
        
        JButton btnDelete = createStyledButton("🗑️ Удалить", new Color(231, 76, 60));
        btnDelete.setBounds(20, 270, 110, 35);
        add(btnDelete);
        
        JButton btnClear = createStyledButton("🔄 Очистить", new Color(149, 165, 166));
        btnClear.setBounds(140, 270, 110, 35);
        add(btnClear);

        // Статистика
        JLabel statsLabel = new JLabel("Статистика:");
        statsLabel.setFont(new Font("Arial", Font.BOLD, 12));
        statsLabel.setBounds(20, 320, 100, 25);
        add(statsLabel);
        
        JTextArea statsArea = new JTextArea();
        statsArea.setEditable(false);
        statsArea.setBackground(new Color(255, 255, 255));
        statsArea.setBounds(20, 350, 230, 80);
        statsArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(statsArea);

        // Table to show members
        String[] columnNames = {"ID", "Имя", "Возраст", "Абонемент"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(52, 73, 94));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(280, 50, 440, 340);
        scrollPane.setBorder(BorderFactory.createTitledBorder("📋 Список клиентов"));
        add(scrollPane);

        // Button actions
        btnAdd.addActionListener(e -> addMember(statsArea));
        btnUpdate.addActionListener(e -> updateMember());
        btnDelete.addActionListener(e -> deleteMember(statsArea));
        btnClear.addActionListener(e -> clearFields());

        // Table row select to fill form
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    txtId.setText(model.getValueAt(selectedRow, 0).toString());
                    txtName.setText(model.getValueAt(selectedRow, 1).toString());
                    txtAge.setText(model.getValueAt(selectedRow, 2).toString());
                    
                    String membership = model.getValueAt(selectedRow, 3).toString();
                    if (membership.equals("Месячный")) cmbMembership.setSelectedIndex(0);
                    else if (membership.equals("Квартальный")) cmbMembership.setSelectedIndex(1);
                    else if (membership.equals("Годовой")) cmbMembership.setSelectedIndex(2);
                    
                    txtId.setEnabled(false);
                }
            }
        });
        
        // Обновление статистики при запуске
        updateStats(statsArea);
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 11));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }

    private void addMember(JTextArea statsArea) {
        try {
            int id = Integer.parseInt(txtId.getText());
            String name = txtName.getText().trim();
            int age = Integer.parseInt(txtAge.getText());
            String membership = cmbMembership.getSelectedItem().toString();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "❌ Введите имя клиента!", 
                    "Ошибка", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            for (Member m : members) {
                if (m.id == id) {
                    JOptionPane.showMessageDialog(this, 
                        "❌ Клиент с таким ID уже существует!", 
                        "Ошибка", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            Member member = new Member(id, name, age, membership);
            members.add(member);
            model.addRow(new Object[]{id, name, age, membership});
            clearFields();
            updateStats(statsArea);
            
            JOptionPane.showMessageDialog(this, 
                "✅ Клиент успешно добавлен!", 
                "Успех", 
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, 
                "❌ Введите корректные числовые значения для ID и возраста!", 
                "Ошибка", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateMember() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                String name = txtName.getText().trim();
                int age = Integer.parseInt(txtAge.getText());
                String membership = cmbMembership.getSelectedItem().toString();
                int id = Integer.parseInt(txtId.getText());

                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(this, 
                        "❌ Введите имя клиента!", 
                        "Ошибка", 
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }

                for (Member m : members) {
                    if (m.id == id) {
                        m.name = name;
                        m.age = age;
                        m.membershipType = membership;
                        break;
                    }
                }

                model.setValueAt(name, selectedRow, 1);
                model.setValueAt(age, selectedRow, 2);
                model.setValueAt(membership, selectedRow, 3);

                JOptionPane.showMessageDialog(this, 
                    "✅ Данные клиента обновлены!", 
                    "Успех", 
                    JOptionPane.INFORMATION_MESSAGE);
                    
                clearFields();
                txtId.setEnabled(true);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, 
                    "❌ Введите корректное числовое значение для возраста!", 
                    "Ошибка", 
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "⚠️ Выберите клиента из списка для обновления.", 
                "Предупреждение", 
                JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteMember(JTextArea statsArea) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Вы действительно хотите удалить этого клиента?", 
                "Подтверждение удаления", 
                JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                int id = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());
                members.removeIf(m -> m.id == id);
                model.removeRow(selectedRow);
                updateStats(statsArea);
                
                JOptionPane.showMessageDialog(this, 
                    "✅ Клиент успешно удален!", 
                    "Успех", 
                    JOptionPane.INFORMATION_MESSAGE);
                    
                clearFields();
                txtId.setEnabled(true);
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "⚠️ Выберите клиента из списка для удаления.", 
                "Предупреждение", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void updateStats(JTextArea statsArea) {
        int total = members.size();
        int monthly = 0, quarterly = 0, yearly = 0;
        
        for (Member m : members) {
            if (m.membershipType.equals("Месячный")) monthly++;
            else if (m.membershipType.equals("Квартальный")) quarterly++;
            else if (m.membershipType.equals("Годовой")) yearly++;
        }
        
        statsArea.setText(
            "Всего клиентов: " + total + "\n" +
            "📅 Месячных: " + monthly + "\n" +
            "📆 Квартальных: " + quarterly + "\n" +
            "📊 Годовых: " + yearly
        );
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtAge.setText("");
        cmbMembership.setSelectedIndex(0);
        txtId.setEnabled(true);
        table.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Cedov9 app = new Cedov9();
            app.setVisible(true);
        });
    }
}