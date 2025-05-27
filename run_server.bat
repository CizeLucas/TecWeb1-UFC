@echo off
REM Compila e executa o Server.java

REM Compilar todos os arquivos .java do src
for /R backend/src %%f in (*.java) do (
    javac -cp "backend/lib/*;backend/bin" -d backend/bin "%%f"
)

REM Executar o servidor
java --enable-native-access=ALL-UNNAMED -cp "backend/bin;backend/lib/*" Server.Server

pause
REM filepath: c:\Users\user\Documents\UFC\tecweb 2025.1\TecWeb1-UFC\run_server.bat