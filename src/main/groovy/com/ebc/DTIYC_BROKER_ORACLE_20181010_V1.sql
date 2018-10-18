REM ***************************************************************************************
PROMPT Nombre del Script: DTIYC_BROKER_ORACLE_20181010_V1.sql
PROMPT Proyecto: Broker Oracle
PROMPT RBVM 10 de Octubre del 2018
PROMPT Descripcion: Se crean alter en tablas cuotas
REM ***************************************************************************************

SET ECHO OFF
SET VERIFY OFF

SPOOL DTIYC_BROKER_ORACLE_20180626_V1.log

ACCEPT BANINST1_PASSWORD PROMPT 'BANINST1 Password: ' HIDE
CONNECT BANINST1/&&BANINST1_PASSWORD

PROMPT *************************************************************************************

SELECT 'Fecha de ejecuci�n: ' || TO_CHAR(SYSDATE,'DD-MON-YYYY HH24:MI') Fecha_Ejecucion FROM DUAL;

PROMPT *Se crea nueva tabla PRN_TEACHER_PROFILE*

@ca_script.sql

SHOW ERRORS

COMMIT;

PROMPT Close output file.
SPOOL OFF

EXIT;
