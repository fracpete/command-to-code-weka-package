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
 * JavaString.java
 * Copyright (C) 2016 University of Waikato, Hamilton, NZ
 */

package weka.core.code;

import weka.core.Utils;

/**
 * Turns the command into a Java string, correctly quoted.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class JavaString
  extends AbstractConverter {

  /**
   * Returns the name of the conversion scheme.
   *
   * @return the name
   */
  @Override
  public String getName() {
    return "Java String";
  }

  /**
   * Checks whether the command can be handled.
   *
   * @param cmd the command to check
   * @return true if converter handles the command
   */
  @Override
  public boolean handles(String cmd) {
    return true;
  }

  /**
   * Converts the command.
   *
   * @param cmd the command to convert.
   * @return the generated code or null in case of an error
   */
  @Override
  public String convert(String cmd) {
    return "String s = \"" + Utils.backQuoteChars(cmd) + "\";";
  }
}
