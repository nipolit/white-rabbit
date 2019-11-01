# Usage info
## Build
To build an executable jar file run:
```
mvn clean install
```
## Run
A sample command to run the application:
```
java -Xmx4g -jar white-rabbit.jar -p "poultry outwits ants" -l 4 665e5bcb0c20062fe8abaaf4628bb154 23170acc097c24edb98fc5488ab033fe e4820b45d2277f3844eac66c903e84be
```
For more details on usage you can run:
```
java -jar white-rabbit.jar --help
```
#### Note
A meet-in-the-middle algorithm used by the application stores a lot of objects in memory. So you may need to grant a permission for claiming it to the JVM. 
E.g. add a parameter `-Xmx4g` to set heap size to 4GB.
