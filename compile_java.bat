@echo off
REM Compila todos os arquivos .java ao mesmo tempo

echo Compiling Java application...

REM Cria o diretório bin se não existir
if not exist "backend\bin" mkdir "backend\bin"

REM Compila todos os arquivos Java recursivamente
for /r "backend\src" %%f in (*.java) do (
    echo %%f >> temp_files.txt
)

javac -cp "backend/lib/*;backend/bin" -d backend/bin @temp_files.txt

REM Remove o arquivo temporário
del temp_files.txt

if %ERRORLEVEL% == 0 (
    echo Compilation successful!
) else (
    echo Compilation failed with errors.
)

pause