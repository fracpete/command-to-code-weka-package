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
 * AbstractConvert.java
 * Copyright (C) 2016 University of Waikato, Hamilton, NZ
 */

package weka.core.code;

import weka.core.ClassDiscovery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

/**
 * Ancestor for converters.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public abstract class AbstractConverter
  implements Converter {

  private static final long serialVersionUID = -6961593275957244187L;

  /**
   * Just uses the name to compare the conversion schemes.
   *
   * @param o the other conversion scheme
   * @return less than 0, equal to 0 or greater than 0 if the name
   *         is less than, equal to or greater than this one
   */
  @Override
  public int compareTo(Converter o) {
    return getName().compareTo(o.getName());
  }

  /**
   * Returns the name of the converter.
   *
   * @return the name
   */
  @Override
  public String toString() {
    return getName();
  }

  /**
   * Returns all conversion schemes.
   *
   * @return all conversion schemes
   */
  public static Converter[] getConverters() {
    List<Converter>	result;
    Vector<String>	classes;
    Converter conv;

    result  = new ArrayList<Converter>();
    classes = ClassDiscovery.find(Converter.class, Converter.class.getPackage().getName());
    for (String cls: classes) {
      try {
	conv = (Converter) Class.forName(cls).newInstance();
	result.add(conv);
      }
      catch (Exception e) {
	System.err.println("Failed to instantiate: " + cls);
	e.printStackTrace();
      }
    }

    Collections.sort(result);

    return result.toArray(new Converter[result.size()]);
  }
}
