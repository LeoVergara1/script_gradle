package com.ebc
import groovy.sql.*


def dataSource = [
url: "jdbc:oracle:thin:@10.31.23.34:1521:DEVL8 ",
user: "nomina",
password: "nomina",
driver: "oracle.jdbc.driver.OracleDriver"
]
def dataSource_teacher = [
url: "jdbc:oracle:thin:@10.31.23.34:1521:TEST8",
user: "horaprof",
password: "pl3kut9k",
driver: "oracle.jdbc.driver.OracleDriver"
]

def dataSource_banis = [
url: "jdbc:oracle:thin:@10.31.23.34:1521:DEVL8",
user: "baninst1",
password: "u_pick_it",
driver: "oracle.jdbc.driver.OracleDriver"
]

def sql = Sql.newInstance(dataSource_banis.url, dataSource_banis.user, dataSource_banis.password, dataSource_banis.driver)

def pass_docentes_to_teacher(){
  List docentes = []
  sql.eachRow('SELECT * FROM horaprof.DOCENTE') { row ->
    docentes << [id: row.id, id_docente: row.ID_DOCENTE, primer_nombre: row.PRIMER_NOMBRE, apellidos: row.APELLIDOS, segundo_nombre: row.SEGUNDO_NOMBRE, pdim: row.PIDM]
  }

  docentes.each{ row ->
    str_string = """
    INSERT INTO NOMINA.PRN_TEACHER_PROFILE (ID, ID_DOCENTE, PRIMER_NOMBRE, APELLIDOS, SEGUNDO_NOMBRE, PIDM, DATE_CREATED, LAST_UPDATED)
    VALUES (
      ?.id,
      ?.id_docente,
      ?.primer_nombre,
      ?.apellidos,
      ?.segundo_nombre,
      ?.pdim,
      sysdate,
      sysdate
      )
    """
    sql.executeInsert(str_string, row)
  }
}

println "HOla mundo"

