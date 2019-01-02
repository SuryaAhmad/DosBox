@ECHO off

REM DELETE Files that are updated by Maven
if exist .\bin        rmdir .\bin       /s /q
if exist .\target     rmdir .\target    /s /q
if exist .\lib        rmdir .\lib       /s /q
if exist .\.settings  rmdir .\.settings / s/q
if exist .\.classpath del .\.classpath  /f /q
if exist .\.project   del .\.project    /f /q

REM REBUILD using maven
REM CREATE Eclipse project files
call mvn eclipse:eclipse
call mvn compile
call mvn package

REM COPY LIBRARIES to this path
mkdir .\lib
copy %USERPROFILE%\.m2\repository\junit\junit\4.7\*.jar .\lib\ /y
pause