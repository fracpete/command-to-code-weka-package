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

/*
 * Converter.java
 * Copyright (C) 2016 University of Waikato, Hamilton, New Zealand
 */

package weka.core.code;

public interface Converter
  extends Comparable<Converter> {

  /**
   * Returns the name of the conversion scheme.
   *
   * @return the name
   */
  public String getName();

  /**
   * Checks whether the command can be handled.
   *
   * @param cmd the command to check
   * @return true if converter handles the command
   */
  public boolean handles(String cmd);

  /**
   * Converts the command.
   *
   * @param cmd the command to convert.
   * @return the generated code or null in case of an error
   */
  public String convert(String cmd);
}
