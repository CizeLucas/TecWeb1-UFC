@echo off
REM Executar o servidor

javac -cp "backend/lib/*;backend/bin" -d backend/bin backend/src/SHA1/*.java
javac -cp "backend/lib/*;backend/bin" -d backend/bin backend/src/Usuario/*.java
javac -cp "backend/lib/*;backend/bin" -d backend/bin backend/src/Sessao/*.java
javac -cp "backend/lib/*;backend/bin" -d backend/bin backend/src/HttpHandlers/*.java
javac -cp "backend/lib/*;backend/bin" -d backend/bin backend/src/Server/*.java

pause