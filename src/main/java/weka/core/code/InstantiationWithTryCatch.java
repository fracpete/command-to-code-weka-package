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
 * InstantiationWithTryCatch.java
 * Copyright (C) 2016 University of Waikato, Hamilton, NZ
 */

package weka.core.code;

/**
 * Instantiates the object and sets its options inside a try-catch block.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class InstantiationWithTryCatch
  extends Instantiation {

  /**
   * Returns the name of the conversion scheme.
   *
   * @return the name
   */
  @Override
  public String getName() {
    return super.getName() + " (with TryCatch)";
  }

  /**
   * Converts the command.
   *
   * @param cmd the command to convert.
   * @param quiet suppresses exceptions if true
   * @return the generated code or null in case of an error
   */
  protected String convert(String cmd, boolean quiet) {
    String		result;
    StringBuilder	full;
    String[]		lines;
    int			i;

    result = super.convert(cmd, quiet);

    if (result != null) {
      lines = result.split("\n");
      full = new StringBuilder();
      full.append(lines[0]);
      full.append("\n");
      full.append("try {\n");
      for (i = 1; i < lines.length; i++) {
	full.append("  ");
	full.append(lines[i]);
	full.append("\n");
      }
      full.append("} catch (Exception e) {\n");
      full.append("  System.err.println(\"Failed to instantiate!\");\n");
      full.append("  e.printStackTrace();\n");
      full.append("}");
      result = full.toString();
    }

    return result;
  }
}
