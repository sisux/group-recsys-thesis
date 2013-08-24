@ECHO OFF
REM - Batch for distribute all documentations in IntegraLite Project
:BEGIN
CLS
ECHO Batch for distribute all documentations in IntegraLite Project

SET ANT_BUILD=C:\md_tool\build
SET PROJECT_BUILD_DIR=D:\gitRepos\group-recsys-thesis\build
SET PROJECT_BUILD_DOC_DIR=%PROJECT_BUILD_DIR%\doc
SET COMMON_PROPERTIES_FILE=%PROJECT_BUILD_DOC_DIR%\common.properties
SET TARGET_PROPERTIES_FILE=%PROJECT_BUILD_DOC_DIR%\design\M04-Design-DinamitasGC.properties

REM - Changing directory where build files are
SET OLDDIR=%CD%
CHDIR /d %ANT_BUILD%

REM - TODO> For all files in the PROJECT_BUILD_DIR, DO
ECHO 1. Distribute documents
CALL ant -v -propertyfile=%COMMON_PROPERTIES_FILE% -propertyfile=%TARGET_PROPERTIES_FILE% pdf
REM -quiet 
GOTO END
:END
REM - restore current directory
CHDIR /d %OLDDIR% 
