/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;
import model.TextEditorModel;
import view.TextEditorView;

public class TextEditorController {
    private TextEditorModel model;
    private TextEditorView view;

    public TextEditorController(TextEditorModel model, TextEditorView view) {
        this.model = model;
        this.view = view;

        view.addSaveButtonListener((ActionEvent e) -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnVal = fileChooser.showSaveDialog(view);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String fileName = fileChooser.getSelectedFile().getAbsolutePath();
                if (model.saveToFile(fileName)) {
                    JOptionPane.showMessageDialog(view, "File đã được lưu thành công!", "Lưu File", JOptionPane.INFORMATION_MESSAGE);
                    openFile(fileName);
                } else {
                    JOptionPane.showMessageDialog(view, "Đã xảy ra lỗi khi lưu file!", "Lưu File", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        view.addLoadButtonListener((ActionEvent e) -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnVal = fileChooser.showOpenDialog(view);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String fileName = fileChooser.getSelectedFile().getAbsolutePath();
                if (model.loadFromFile(fileName)) {
                    view.updateTextArea(model.getLines());
                } else {
                    JOptionPane.showMessageDialog(view, "Đã xảy ra lỗi khi tải file!", "Tải File", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        view.addClearButtonListener((ActionEvent e) -> {
            model.clearLines();
            view.clearTextArea();
        });
    }

    private void openFile(String fileName) {
        try {
            File file = new File(fileName);
            Desktop.getDesktop().open(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}