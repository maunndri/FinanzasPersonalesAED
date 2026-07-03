@echo off
javac -d bin src\Principal.java src\estructuras\*.java src\modelo\*.java src\servicio\*.java
java -cp bin Principal

