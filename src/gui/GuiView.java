package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.swing.*;

import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.MenuKeyListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.Document;

/* This class demos both the file chooser and Java's ability to display HTML
 * Use the file chooser to open an an HTML object.  The rendered version of the
 * HTML should appear in the window.
 */

public class GuiView extends JFrame {

    XMLModel model; // the parent
    JFileChooser fc;
    JEditorPane pane; // the actual page (HTML)

    JMenu filters;
    static final Dimension TOTAL_SIZE = new Dimension(800, 600);

    public GuiView(XMLModel modeller) {
        model = modeller;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(TOTAL_SIZE);

        fc = (JFileChooser) createFileChooser();
        // html
        pane = new JEditorPane();
        pane.setAutoscrolls(true);
        // autoscroll
        JScrollPane scroll = new JScrollPane(pane);
        scroll.setAutoscrolls(true);
        // layout
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(createMenu(), BorderLayout.NORTH);
        getContentPane().add(scroll, BorderLayout.CENTER);
        pack();

        promptFiles();
        setVisible(true);
    }
    
    private JComponent createMenu() {
        JMenuBar jm = new JMenuBar();
        jm.add(createMenuFileChooser());
        jm.add(createFilterMenu());
        jm.add(createPreviewMenu());
        return jm;
    }

    private JComponent createMenuFileChooser() {
        JButton jb = new JButton("Choose Files");
        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                promptFiles();
            }
        });
        return jb;
    }

    private JComponent createFileChooser() {
        JFileChooser f = new JFileChooser();
        f.setCurrentDirectory(new File("resources"));
        f.setFileFilter(new XMLFileFilter());
        f.setMultiSelectionEnabled(true);
        return f;
    }

    private JMenu createPreviewMenu() {
        JMenu outputfiles = new JMenu("Preview webpages");
        RadioListener r = new RadioListener();

        ButtonGroup rb = new ButtonGroup();
        JRadioButtonMenuItem summaryList = new JRadioButtonMenuItem("Summary");
        summaryList.setActionCommand("Summary");
        summaryList.addActionListener(r);

        JRadioButtonMenuItem conflicts = new JRadioButtonMenuItem(
                "Conflicts");
        conflicts.setActionCommand("Conflicts");
        conflicts.addActionListener(r);
        
        JRadioButtonMenuItem calendarMonth = new JRadioButtonMenuItem(
                "Month View");
        calendarMonth.setActionCommand("Month View");
        calendarMonth.addActionListener(r);

        JRadioButtonMenuItem calendarDay = new JRadioButtonMenuItem(
                "Day View");
        calendarDay.setActionCommand("Calendar Day");
        calendarDay.addActionListener(r);
        
        rb.add(summaryList);
        rb.add(conflicts);
        rb.add(calendarMonth);
        rb.add(calendarDay);
        outputfiles.add(summaryList);
        outputfiles.add(conflicts);
        outputfiles.add(calendarMonth);
        outputfiles.add(calendarDay);

        return outputfiles;
    }

    private JMenu createFilterMenu() {
        filters = new JMenu("Filters/Sort");
        JMenuItem title = new JMenuItem("filter by title");
        title.setActionCommand("Filter By Title");
        title.addActionListener(new FilterListener());

        JMenuItem range = new JMenuItem("filter by range of time");
        range.setActionCommand("Filter By Range of Time");
        range.addActionListener(new FilterListener());
        
        JMenuItem detail = new JMenuItem("filter by details");
        detail.setActionCommand("Filter By Details");
        detail.addActionListener(new FilterListener());
        
        JMenuItem timestamp = new JMenuItem("Filter By Timestamp");
        timestamp.setActionCommand("Filter By Timestamp");
        timestamp.addActionListener(new FilterListener());

        JMenuItem exclude = new JMenuItem("Exclude keyword");
        exclude.setActionCommand("Exclude Keyword");
        exclude.addActionListener(new FilterListener());   
        
        JMenuItem tag = new JMenuItem("Filter By Tag");
        tag.setActionCommand("Filter By Tags");
        tag.addActionListener(new FilterListener());   
        
        JMenuItem specific = new JMenuItem(
                "Filter By XML File, Keyword in specific tag");
        specific.setActionCommand(
                "Filter By XML File, Keyword in specific tag");
        specific.addActionListener(new FilterListener()); 
        
        JMenuItem titlesort = new JMenuItem("Sort by title");
        titlesort.setActionCommand("Sort by title");
        titlesort.addActionListener(new SortListener());  
        
        JMenuItem startsort = new JMenuItem("Sort by starttime");
        startsort.setActionCommand("Sort by start time");
        startsort.addActionListener(new SortListener());  
        
        JMenuItem endsort = new JMenuItem("Sort by endtime");
        endsort.setActionCommand("Sort by end time");
        endsort.addActionListener(new SortListener());  
        
        JMenuItem reset = new JMenuItem("Reset all filters");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetCheckBoxes();
                model.resetAllFilters();
            }
        });

        filters.add(title);
        filters.add(range);
        filters.add(detail);
        filters.add(timestamp);
        filters.add(exclude);
        filters.add(tag);
        filters.add(specific);
        filters.add(titlesort);
        filters.add(startsort);
        filters.add(endsort);
        filters.add(reset);
        return filters;

    }

    private class RadioListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            try {
                model.showFileOfType(arg0.getActionCommand());
            } catch (Exception e) {
                showError(e.getMessage());
            }
        }
    }

    private class FilterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String term = showInputDialog("please enter search term");
            String[] t = term.split(",");
            for (String words : t) {
                words = words.trim();
            }
            model.applyFilter(e.getActionCommand(), t);
        }
    }

    private class SortListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String term = showInputDialog("Ascending or descending?");
            model.applySort(e.getActionCommand(), term);
        }
    }

    private void promptFiles() {
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File[] files = fc.getSelectedFiles();
            model.parseFiles(files);
        }
    }

    public void updateView(String uri) {
        try {
            Document doc = pane.getDocument();
            doc.putProperty(Document.StreamDescriptionProperty, null);

            pane.setPage(uri);

        } catch (IOException e) {
            showError("Page " + uri + " is invalid");
        }
    }

    protected void resetCheckBoxes() {
        for (Component i : filters.getMenuComponents()) {
            ((JMenuItem) i).setSelected(false);
        }

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
        String input = JOptionPane.showInputDialog(message);
        return input;
    }
}
