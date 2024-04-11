package notepad;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.font.TextAttribute;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.filechooser.*;

public class Notepad extends JFrame implements ActionListener {

    private JTextArea area;
    private JScrollPane scpane;
    String text = "";
    public Notepad() {
        super("Text Editor Using Java. Developed by Yogesh Verma");
        setSize(1950, 1050);

        setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar(); //menubar

        JMenu file = new JMenu("File"); //file menu

        JMenuItem newdoc = new JMenuItem("New");
        newdoc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        newdoc.addActionListener(this);

        JMenuItem open = new JMenuItem("Open");
        open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        open.addActionListener(this);

        JMenuItem save = new JMenuItem("Save");
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        save.addActionListener(this);

        JMenuItem print = new JMenuItem("Print");
        print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        print.addActionListener(this);

        JMenuItem exit = new JMenuItem("Exit");
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
        exit.addActionListener(this);

        JMenu edit = new JMenu("Edit");

        JMenuItem copy = new JMenuItem("Copy");
        copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        copy.addActionListener(this);

        JMenuItem paste = new JMenuItem("Paste");
        paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        paste.addActionListener(this);

        JMenuItem cut = new JMenuItem("Cut");
        cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        cut.addActionListener(this);

        JMenuItem selectall = new JMenuItem("Select All");
        selectall.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        selectall.addActionListener(this);

        //Text Format
        JMenu textFormat = new JMenu("Text Format");

        JMenuItem bold = new JMenuItem("Bold");
        bold.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
        bold.addActionListener(this);

        JMenuItem italic = new JMenuItem("Italic");
        italic.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
        italic.addActionListener(this);

        JMenuItem underline = new JMenuItem("Underline");
        underline.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
        underline.addActionListener(this);

        JMenu about = new JMenu("Help");

        JMenuItem notepad = new JMenuItem("About Notepad");
        notepad.addActionListener(this);

        area = new JTextArea();
        area.setFont(new Font("SAN_SERIF", Font.PLAIN, 20));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        scpane = new JScrollPane(area);
        scpane.setBorder(BorderFactory.createEmptyBorder());

        setJMenuBar(menuBar);
        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(textFormat); // Added the Text Format menu
        menuBar.add(about);

        file.add(newdoc);
        file.add(open);
        file.add(save);
        file.add(print);
        file.add(exit);

        edit.add(copy);
        edit.add(paste);
        edit.add(cut);
        edit.add(selectall);

        //Text Format Menu Items
        textFormat.add(bold);
        textFormat.add(italic);
        textFormat.add(underline);

        about.add(notepad);

        add(scpane, BorderLayout.CENTER);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("New")) {
            area.setText("");

        } else if (ae.getActionCommand().equals("Open")) {
            JFileChooser chooser = new JFileChooser("D:/Java");
            chooser.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter restrict = new FileNameExtensionFilter("Only .txt files", "txt");
            chooser.addChoosableFileFilter(restrict);

            int result = chooser.showOpenDialog(this);
            if(result == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();

                try{
                    System.out.println("HEki");
                    FileReader reader = new FileReader(file);
                    BufferedReader br = new BufferedReader(reader);
                    area.read( br, null );
                    br.close();
                    area.requestFocus();
                }catch(Exception e){
                    System.out.print(e);
                }
            }
        } else if(ae.getActionCommand().equals("Save")){
            final JFileChooser SaveAs = new JFileChooser();
            SaveAs.setApproveButtonText("Save");
            int actionDialog = SaveAs.showOpenDialog(this);
            if (actionDialog != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File fileName = new File(SaveAs.getSelectedFile() + ".txt");
            BufferedWriter outFile = null;
            try {
                outFile = new BufferedWriter(new FileWriter(fileName));
                area.write(outFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(ae.getActionCommand().equals("Print")){
            try{
                area.print();
            }catch(Exception e){}
        }else if (ae.getActionCommand().equals("Exit")) {
            System.exit(0);
        }else if (ae.getActionCommand().equals("Copy")) {
            text = area.getSelectedText();
        }else if (ae.getActionCommand().equals("Paste")) {
            area.insert(text, area.getCaretPosition());
        }else if (ae.getActionCommand().equals("Cut")) {
            text = area.getSelectedText();
            area.replaceRange("", area.getSelectionStart(), area.getSelectionEnd());
        }else if (ae.getActionCommand().equals("Select All")) {
            area.selectAll();
        }else if (ae.getActionCommand().equals("Bold")) {
            Font currentFont = area.getFont();
            Font newFont;
            if (currentFont.isBold()) {
                newFont = new Font(currentFont.getFontName(), Font.PLAIN, currentFont.getSize());
            } else {
                newFont = new Font(currentFont.getFontName(), Font.BOLD, currentFont.getSize());
            }
            area.setFont(newFont);
        } else if (ae.getActionCommand().equals("Italic")) {
            Font currentFont = area.getFont();
            Font newFont;
            if (currentFont.isItalic()) {
                newFont = new Font(currentFont.getFontName(), Font.PLAIN, currentFont.getSize());
            } else {
                newFont = new Font(currentFont.getFontName(), Font.ITALIC, currentFont.getSize());
            }
            area.setFont(newFont);
        } else if (ae.getActionCommand().equals("Underline")) {
            Font currentFont = area.getFont();
            Font newFont;
            Map<TextAttribute, Object> attributes = new HashMap<>(currentFont.getAttributes());
            if (attributes.containsKey(TextAttribute.UNDERLINE)) {
                attributes.put(TextAttribute.UNDERLINE, -1); // -1 means no underline
            } else {
                attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            }
            newFont = new Font(attributes);
            area.setFont(newFont);
        } else if (ae.getActionCommand().equals("Normal")) {
            Font currentFont = area.getFont();
            Font newFont = new Font(currentFont.getFontName(), Font.PLAIN, currentFont.getSize());
            area.setFont(newFont);
        }
        else if (ae.getActionCommand().equals("About Notepad")) {
            new About().setVisible(true);
        }
    }
    public static void main(String[] args) {
        new Notepad();
    }
}
