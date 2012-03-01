package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.swing.*;

import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.filechooser.FileFilter;

/* This class demos both the file chooser and Java's ability to display HTML
 * Use the file chooser to open an an HTML object.  The rendered version of the
 * HTML should appear in the window.
 */

public class GuiView extends JFrame {

    XMLModel model;
    JFileChooser fc;
    JMenuBar jm;
    JMenu filters, outputfiles;
    JRadioButtonMenuItem CalendarDay, CalendarWeek, CalendarMonth; // TODO: ETC.
    JCheckBoxMenuItem StartDate, Title, Detail; // TODO:ETC
    JEditorPane pane; // the actual page (HTML)
    static final Dimension TOTAL_SIZE = new Dimension(800, 600);

    public GuiView(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(TOTAL_SIZE);
        createFileChooser();
        createMenu();
        pane = new JEditorPane();
        pane.setEditable(false);

        // layout
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(jm, BorderLayout.NORTH);
        getContentPane().add(pane, BorderLayout.CENTER);
        pack();

        String url = "";
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File[] file = fc.getSelectedFiles();

            System.out.println(file.length);
            url = file[0].toURI().toString();
            // model.parseFiles(file);

        }

        pane.setPage(url);
        setVisible(true);
    }

    private void createFileChooser() {
        fc = new JFileChooser();
        fc.setCurrentDirectory(new File("resources"));
        fc.setFileFilter(new XMLFileFilter());
        fc.setMultiSelectionEnabled(true);

    }

    private void createMenu() {
        jm = new JMenuBar();

        filters = new JMenu("Filters/Sort");
        outputfiles = new JMenu("Preview webpages");
        createMenuButtons();
    }

    private void createMenuButtons() { //TODO: ADD ACTION LISTENERS
        Title = new JCheckBoxMenuItem("filter by title");
        filters.add(Title);

        ButtonGroup rb = new ButtonGroup();
        CalendarDay = new JRadioButtonMenuItem("Day View");
        CalendarMonth = new JRadioButtonMenuItem("Month View");
        rb.add(CalendarDay);
        rb.add(CalendarMonth);
        outputfiles.add(CalendarDay);
        outputfiles.add(CalendarMonth);

        jm.add(filters);
        jm.add(outputfiles);

    }

    /**
     * Display given message as an error in the GUI.
     */
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Browser Error",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Prompt for string input
     */
    public String showInputDialog(String message) {
        String input=
            JOptionPane.showInputDialog(message);
        return input;
    }

    public static void main(String[] args) throws IOException {
        GuiView foo = new GuiView();

    }

}
