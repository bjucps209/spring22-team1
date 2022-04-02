@echo off
echo Make sure that Visual Studio Code is closed, then
pause
echo Cleaning project... 
del .project .classpath gradlew* 2>NUL:
rd /s/q bin build .settings .gradle .idea gradle 2>NUL:
echo Project is cleaned
pause
