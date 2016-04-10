How to make a release
=====================

Weka package
------------

* Run the following command to generate the package archive for version `1.0.0`:

  ```
  ant -f build_package.xml -Dpackage=command-to-code-1.0.0 clean make_package
  ``

* Create a release tag on github (v1.0.0)
* add release notes
* upload package archive from `dist`


Maven
-----

* Run the following command to deploy the artifact:

  ```
  mvn release:clean release:prepare release:perform
  ```

* After successful deployment, push the changes out:

  ```
  git push
  ````

