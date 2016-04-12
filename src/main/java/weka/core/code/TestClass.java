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
 * TestClass.java
 * Copyright (C) 2016 University of Waikato, Hamilton, NZ
 */

package weka.core.code;

import weka.core.Utils;

/**
 * Creates a test class for the command instantiation.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class TestClass
  extends AbstractConverter {

  private static final long serialVersionUID = -4724000142023362577L;

  /**
   * Returns the name of the conversion scheme.
   *
   * @return the name
   */
  @Override
  public String getName() {
    return "Test class";
  }

  /**
   * Checks whether the command can be handled.
   *
   * @param cmd the command to check
   * @return true if converter handles the command
   */
  @Override
  public boolean handles(String cmd) {
    return new Instantiation().handles(cmd);
  }

  /**
   * Converts the command.
   *
   * @param cmd the command to convert.
   * @param quiet suppresses exceptions if true
   * @return the generated code or null in case of an error
   */
  protected String convert(String cmd, boolean quiet) {
    StringBuilder	result;
    String		cmdOptions;
    Class		cls;

    result = new StringBuilder();

    try {
      cls        = CodeHelper.classFromCommand(cmd);
      cmdOptions = CodeHelper.optionsStringFromCommand(cmd);
      result.append("import " + cls.getName() + ";\n");
      result.append("import weka.core.Utils;\n");
      result.append("\n");
      result.append("public class OptionsTest {\n");
      result.append("\n");
      result.append("  public static void main(String[] args) throws Exception {\n");
      result.append("    ");
      result.append(cls.getSimpleName() + " " + cls.getSimpleName().toLowerCase()
	+ " = new " + cls.getName() + "();");
      if (CodeHelper.isOptionHandler(cmd)) {
	if (cmdOptions.trim().length() > 0)
	  result.append("\n    " + cls.getSimpleName().toLowerCase() + ".setOptions(Utils.splitOptions(\"" + Utils.backQuoteChars(cmdOptions) + "\"));");
      }
      result.append("\n  }\n");
      result.append("}\n");
    }
    catch (Exception e) {
      result = null;
      if (!quiet) {
	System.err.println("Failed to process command-line: " + cmd);
	e.printStackTrace();
      }
    }

    if (result == null)
      return null;
    else
      return result.toString();
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
