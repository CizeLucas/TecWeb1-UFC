@echo off
REM Compila e executa o Server.java

REM Compilar todos os arquivos .java do src
javac -cp "backend/lib/*" -d backend/bin backend/src/model/*.java backend/src/controller/*.java

REM Executar o servidor
java --enable-native-access=ALL-UNNAMED -cp "backend/bin;backend/lib/*" controller.Server

pause
REM filepath: c:\Users\user\Documents\UFC\tecweb 2025.1\TecWeb1-UFC\run_server.bat