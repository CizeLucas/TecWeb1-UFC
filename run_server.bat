@echo off
REM filepath: c:\Users\user\Documents\UFC\tecweb 2025.1\TecWeb1-UFC\run_server.bat
REM Compila e executa o Server.java

REM Ajuste o caminho do javac e java se necessário

REM Compilar todos os arquivos .java do src
javac -cp ".;lib/*" -d bin src\model\*.java src\controller\*.java

REM Executar o servidor
java -cp ".;bin;lib/*" controller.Server

pause