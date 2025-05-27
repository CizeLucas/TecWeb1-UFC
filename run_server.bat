@echo off
REM Executar o servidor
java --enable-native-access=ALL-UNNAMED -cp "backend/bin;backend/lib/*" Server.Server

pause
