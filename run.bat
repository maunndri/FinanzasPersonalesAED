@echo off
javac -d bin src\Principal.java src\view\*.java src\estructuras\*.java src\model\*.java src\servicio\*.java
java -cp bin Principal

