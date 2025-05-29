@echo off
setlocal

set OUT_DIR=out

echo Nettoyage du dossier de sortie...
if exist %OUT_DIR% rmdir /s /q %OUT_DIR%
mkdir %OUT_DIR%

echo.
echo === Compilation des modules ===

echo [1/4] Compilation du module core...
javac -d %OUT_DIR% ^
  com.m2i.m2ibank.core\src\module-info.java ^
  com.m2i.m2ibank.core\src\com\m2i\m2ibank\core\*.java
if errorlevel 1 pause

echo [2/4] Compilation du module clients...
javac -d %OUT_DIR% --module-path %OUT_DIR% ^
  com.m2i.m2ibank.clients\src\module-info.java ^
  com.m2i.m2ibank.clients\src\com\m2i\m2ibank\clients\*.java
if errorlevel 1 pause

echo [3/4] Compilation du module transactions...
javac -d %OUT_DIR% --module-path %OUT_DIR% ^
  com.m2i.m2ibank.transactions\src\module-info.java ^
  com.m2i.m2ibank.transactions\src\com\m2i\m2ibank\transactions\*.java
if errorlevel 1 pause

echo [4/4] Compilation du module app...
javac -d %OUT_DIR% --module-path %OUT_DIR% ^
  com.m2i.m2ibank.app\src\module-info.java ^
  com.m2i.m2ibank.app\src\com\m2i\m2ibank\app\*.java
if errorlevel 1 pause

echo.
echo === Lancement de JShell avec le module core ===
jshell --module-path %OUT_DIR% --add-modules com.m2i.m2ibank.core

endlocal
