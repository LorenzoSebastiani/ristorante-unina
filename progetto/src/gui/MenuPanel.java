package gui;

import controller.Controller;
import model.Piatto;
import model.PiattoDelGiorno;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MenuPanel extends JPanel {
    private Controller controller;
    private JTable table;
    private DefaultTableModel model;

    public MenuPanel(Controller controller) {
        this.controller = controller;

        // TABLE
        String[] colonne = {"Nome", "Categoria", "Prezzo", "Tipo", "Disponibile"};
        model = new DefaultTableModel(colonne, 0);
        table = new JTable(model);

        for(Piatto p : controller.getPiatti()){
            String tipo = null;
            String isDisponibile = null;

            if(p instanceof PiattoDelGiorno){
                tipo = "Piatto del giorno";
            } else {
                tipo = "Piatto fisso";
            }

            if(p.isDisponibile()){
                isDisponibile = "Sì";
            } else {
                isDisponibile = "No";
            }

            model.addRow(new Object[]{
                    p.getNome(),
                    p.getCategoria(),
                    p.getPrezzo(),
                    tipo,
                    isDisponibile
            });
        }

        JScrollPane scrollPane = new JScrollPane(table);
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }
}
