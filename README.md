# Getting Started

This is a code-test project base on springframework, and build with maven.

### requirement
jdk8 mvn3

### build the jar 
follow the below line, and the jar file will build to target/code-test-0.0.1.jar
```shell
mvn clean install
```
or use the pre-build jar code-test-0.0.1.jar within the project root directory directly.

### run the jar
use java command to run the jar file
```shell
java -jar /path/to/code-test-0.0.1.jar
```
enter the payment records with CurrencyCode Amount like format. CurrencyCode SHOULD be 3 upper case letters. Otherwise, a warning will be prompt in the console. This is a sample input
```shell
input: HKD 100.10
```
optional, you can run jar file by providing an input file with following command. 
```shell
java -jar /path/to/code-test-0.0.1.jar input.txt
```
The file should be one or more lines with CurrencyCode Amount like the input format. There is a sample file input.txt within the project root directory.

enjoy.