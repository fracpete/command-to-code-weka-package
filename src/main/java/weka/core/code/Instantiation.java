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
 * Instantiation.java
 * Copyright (C) 2016 University of Waikato, Hamilton, NZ
 */

package weka.core.code;

import weka.core.Utils;

/**
 * Instantiates the object and sets its options.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class Instantiation
  extends AbstractConverter {

  private static final long serialVersionUID = -4724000142023362577L;

  /**
   * Returns the name of the conversion scheme.
   *
   * @return the name
   */
  @Override
  public String getName() {
    return "Instantiation";
  }

  /**
   * Checks whether the command can be handled.
   *
   * @param cmd the command to check
   * @return true if converter handles the command
   */
  @Override
  public boolean handles(String cmd) {
    return (convert(cmd, true) != null);
  }

  /**
   * Converts the command.
   *
   * @param cmd the command to convert.
   * @param quiet suppresses exceptions if true
   * @return the generated code or null in case of an error
   */
  protected String convert(String cmd, boolean quiet) {
    String	result;
    String	cmdOptions;
    Class	cls;

    result = null;

    try {
      cls        = CodeHelper.classFromCommand(cmd);
      cmdOptions = CodeHelper.optionsStringFromCommand(cmd);
      result     = cls.getName() + " " + cls.getSimpleName().toLowerCase()
	+ " = new " + cls.getName() + "();";
      if (CodeHelper.isOptionHandler(cmd)) {
	if (cmdOptions.trim().length() > 0)
	  result += "\n" + cls.getSimpleName().toLowerCase() + ".setOptions(weka.core.Utils.splitOptions(\"" + Utils.backQuoteChars(cmdOptions) + "\"));";
      }
    }
    catch (Exception e) {
      if (!quiet) {
	System.err.println("Failed to process command-line: " + cmd);
	e.printStackTrace();
      }
    }

    return result;
  }

  /**
   * Converts the command.
   *
   * @param cmd the command to convert.
   * @return the generated code or null in case of an error
   */
  @Override
  public String convert(String cmd) {
    return convert(cmd, false);
  }
}
