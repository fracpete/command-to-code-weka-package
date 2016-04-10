/*
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * CommandToCodePanel.java
 * Copyright (C) 2016 University of Waikato, Hamilton, NZ
 */

package weka.gui;

import weka.core.code.AbstractConverter;
import weka.core.code.Converter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The user interface for converting command-lines to code snippets.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class CommandToCodePanel
  extends JPanel {

  /** the available conversion schemes. */
  protected Converter[] m_Converters;

  /** the paste button. */
  protected JButton m_ButtonPaste;

  /** the copy button. */
  protected JButton m_ButtonCopy;

  /** the panel for the command. */
  protected JPanel m_PanelCommand;

  /** the panel for the code. */
  protected JPanel m_PanelCode;

  /** the text area for the command. */
  protected JTextArea m_TextCommand;

  /** the text area for the code. */
  protected JTextArea m_TextCode;

  /** the label for the command. */
  protected JLabel m_LabelCommand;

  /** the label for the code. */
  protected JLabel m_LabelCode;

  /** the label for the converters. */
  protected JLabel m_LabelConverters;

  /** the combobox for the converters. */
  protected JComboBox m_ComboBoxConverters;

  /** the button for the converters .*/
  protected JButton m_ButtonConverters;

  /**
   * Configures the panel.
   */
  public CommandToCodePanel() {
    super();
    initialize();
    initGUI();
    finishInit();
  }

  /**
   * Initializes the members.
   */
  protected void initialize() {
    m_Converters = AbstractConverter.getConverters();
  }

  /**
   * Configures the widgets.
   */
  protected void initGUI() {
    JPanel	panel;
    JPanel	panel2;

    setLayout(new GridLayout(2, 1));

    // command
    m_PanelCommand = new JPanel(new BorderLayout());
    m_PanelCommand.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    add(m_PanelCommand);
    panel = new JPanel(new BorderLayout(0, 0));
    m_PanelCommand.add(panel);
    m_LabelCommand = new JLabel("Command-line");
    m_TextCommand = new JTextArea();
    m_TextCommand.setFont(new Font("monospaced", Font.PLAIN, 12));
    panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    panel.add(panel2, BorderLayout.NORTH);
    panel2.add(m_LabelCommand);
    panel.add(new JScrollPane(m_TextCommand), BorderLayout.CENTER);
    panel2 = new JPanel(new BorderLayout());
    panel.add(panel2, BorderLayout.EAST);
    m_ButtonPaste = new JButton(ComponentHelper.getImageIcon("weka/gui/images/paste.gif"));
    m_ButtonPaste.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
	String s = pasteStringFromClipboard();
	if (s != null)
	  m_TextCommand.setText(s);
      }
    });
    panel2.add(m_ButtonPaste, BorderLayout.NORTH);

    // converters
    m_ComboBoxConverters = new JComboBox<Converter>(m_Converters);
    m_LabelConverters = new JLabel("Converter");
    m_LabelConverters.setLabelFor(m_ComboBoxConverters);
    m_ButtonConverters = new JButton("Execute");
    m_ButtonConverters.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
	convert();
      }
    });
    panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    panel.add(m_LabelConverters);
    panel.add(m_ComboBoxConverters);
    panel.add(m_ButtonConverters);
    m_PanelCommand.add(panel, BorderLayout.SOUTH);

    // code
    m_PanelCode = new JPanel(new BorderLayout());
    m_PanelCode.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    add(m_PanelCode);
    panel = new JPanel(new BorderLayout(0, 0));
    m_PanelCode.add(panel);
    m_LabelCode = new JLabel("Generated code");
    m_TextCode = new JTextArea();
    m_TextCode.setFont(new Font("monospaced", Font.PLAIN, 12));
    m_TextCode.setEditable(false);
    panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
    panel.add(panel2, BorderLayout.NORTH);
    panel2.add(m_LabelCode);
    panel.add(new JScrollPane(m_TextCode), BorderLayout.CENTER);
    panel2 = new JPanel(new BorderLayout());
    panel.add(panel2, BorderLayout.EAST);
    m_ButtonCopy = new JButton(ComponentHelper.getImageIcon("weka/gui/images/copy.gif"));
    m_ButtonCopy.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
	if (m_TextCode.getText().length() > 0)
	  copyToClipboard(m_TextCode.getText());
      }
    });
    panel2.add(m_ButtonCopy, BorderLayout.NORTH);
  }

  /**
   * Finishes up the initialization.
   */
  protected void finishInit() {
    m_ComboBoxConverters.setSelectedIndex(0);
    m_TextCommand.setText("");
    m_TextCode.setText("");
  }

  /**
   * Applies the current converter to the command, if possible.
   */
  protected void convert() {
    String 	cmd;
    Converter	conv;

    cmd = m_TextCommand.getText();
    if (cmd.trim().length() == 0) {
      ComponentHelper.showMessageBox(
	this, "Error", "No command line to convert!",
	JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
      return;
    }

    conv = (Converter) m_ComboBoxConverters.getSelectedItem();
    if (!conv.handles(cmd)) {
      ComponentHelper.showMessageBox(
	this, "Error", "Command-line cannot be processed by the '" + conv.getName() + "' converter!",
	JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
      return;
    }

    m_TextCode.setText(conv.convert(cmd));
  }

  /**
   * Obtains a string from the clipboard.
   *
   * @return		the obtained string, null if not available
   */
  protected static String pasteStringFromClipboard() {
    Clipboard clipboard;
    String		result;
    Transferable content;

    result = null;

    try {
      clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
      content   = clipboard.getContents(null);
      if ((content != null) && (content.isDataFlavorSupported(DataFlavor.stringFlavor)))
	result = (String) content.getTransferData(DataFlavor.stringFlavor);
    }
    catch (Exception e) {
      result = null;
    }

    return result;
  }

  /**
   * Copies the given string to the system's clipboard.
   *
   * @param s		the string to copy
   */
  protected static void copyToClipboard(String s) {
    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new TransferableString(s), null);
  }

  public static void main(String[] args) {
    CommandToCodePanel panel = new CommandToCodePanel();
    JFrame frame = new JFrame("Command to code");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setIconImage(ComponentHelper.getImage("weka/gui/weka_icon_new_small.png"));
    frame.getContentPane().setLayout(new BorderLayout());
    frame.getContentPane().add(panel, BorderLayout.CENTER);
    frame.setSize(600, 400);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}
