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
 * CodeHelper.java
 * Copyright (C) 2016 University of Waikato, Hamilton, NZ
 */

package weka.core.code;

import weka.core.ClassDiscovery;
import weka.core.OptionHandler;
import weka.core.Utils;

/**
 * Helper class for code related operations.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class CodeHelper {

  /**
   * Extracts the classname from the command.
   *
   * @param cmd		the command to use
   * @return		the extracted classname
   * @throws Exception	if failed to split command
   */
  public static String classnameFromCommand(String cmd) throws Exception {
    String[]	options;

    options = Utils.splitOptions(cmd);

    return options[0];
  }

  /**
   * Extracts the options from the command.
   *
   * @param cmd		the command to use
   * @return		the extracted options
   * @throws Exception	if failed to split command
   */
  public static String[] optionsFromCommand(String cmd) throws Exception {
    String[]	result;
    String[]	options;

    options = Utils.splitOptions(cmd);
    result = new String[options.length - 1];

    if (result.length > 0)
      System.arraycopy(options, 1, result, 0, options.length - 1);

    return result;
  }

  /**
   * Extracts the options from the command as single string.
   *
   * @param cmd		the command to use
   * @return		the extracted options
   * @throws Exception	if failed to split command
   */
  public static String optionsStringFromCommand(String cmd) throws Exception {
    return Utils.joinOptions(optionsFromCommand(cmd));
  }

  /**
   * Extracts the class from the command.
   *
   * @param cmd		the command to use
   * @return		the extracted class
   * @throws Exception	if failed to instantiate class or failed to split command
   */
  public static Class classFromCommand(String cmd) throws Exception {
    return Class.forName(classnameFromCommand(cmd));
  }

  /**
   * Returns the whether class in the commandline implements {@link OptionHandler}.
   *
   * @param cmd		the command to use
   * @return		true if an OptionHandler
   * @throws Exception	if failed to split command or instantiate class
   */
  public static boolean isOptionHandler(String cmd) throws Exception {
    Class	cls;

    cls = classFromCommand(cmd);

    return ClassDiscovery.hasInterface(OptionHandler.class, cls);
  }
}
