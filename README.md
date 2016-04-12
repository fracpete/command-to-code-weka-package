command-to-code-weka-package
============================

Adds a menu item to the GUIChooser's main menu for converting command-lines
into code snippets (under **Extensions**).


Available converters
--------------------

* **Instantiation** -- instantiates an object specified by the command-line
  and sets its options (if a `weka.core.OptionHandler`).
* **Instantiation (with TryCatch)** -- same as **Instantiation**, but guards
  the setting of the options with a try-catch block.
* **Java String** -- simply escapes the command-line and generates a statement 
  using a String.
* **String Array** -- fills a String array with the options from the command.
* **Test class** -- generates a little test class that instantiates the command
  including the necessary imports.


Releases
--------

Click on one of the following links to download the corresponding Weka package:

* [2016.4.13](https://github.com/fracpete/command-to-code-weka-package/releases/download/v2016.4.13/command-to-code-2016.4.13.zip)
* [2016.4.11](https://github.com/fracpete/command-to-code-weka-package/releases/download/v2016.4.11/command-to-code-2016.4.11.zip)
* [2016.4.10](https://github.com/fracpete/command-to-code-weka-package/releases/download/v2016.4.10/command-to-code-2016.4.10.zip)


How to use packages
-------------------

For more information on how to install the package, see:

http://weka.wikispaces.com/How+do+I+use+the+package+manager%3F

