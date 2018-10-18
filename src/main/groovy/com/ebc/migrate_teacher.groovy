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

def pass_docentes_to_teacher(Sql sql){
  List docentes = []
  sql.eachRow('SELECT * FROM horaprof.DOCENTE') { row ->
    docentes << [id: row.id, id_docente: row.ID_DOCENTE, primer_nombre: row.PRIMER_NOMBRE, apellidos: row.APELLIDOS, segundo_nombre: row.SEGUNDO_NOMBRE, pdim: row.PIDM]
  }
  print "Docentes consultados"
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
  print "Docentes Migrados"
}

def pass_cuotas_to_nomina(Sql sql) {
  List cuotas = []
  sql.eachRow('SELECT * FROM horaprof.CUOTA') { row ->
    cuotas << [id: row.id, id_empresa: row.ID_EMPRESA, id_nivel: row.ID_NIVEL, id_campus: row.ID_CAMPUS, id_horario: row.ID_HORARIO,
    cuota: row.CUOTA, fecha_ingreso: row.FECHA_INGRESO, fecha_alta: row.FECHA_ALTA, fecha_prenomina: row.FECHA_PRENOMINA,
    estatus_cuota: row.ESTATUS_CUOTA, estatus_docente: row.ESTATUS_DOCENTE, fecha_estatus_docente: row.FECHA_ESTATUS_DOCENTE,
    usuario: row.USUARIO, id_docente: row.ID_DOCENTE, empresa: row.EMPRESA, id_forma_de_pago: row.ID_FORMA_DE_PAGO, forma_de_pago: row.FORMA_DE_PAGO,
    nivel: row.NIVEL, campus: row.CAMPUS, horario: row.HORARIO, justificacion: row.JUSTIFICACION]
  }
  print "Cuotas consultados"
  cuotas.each(){ row ->
    str_string = """
    INSERT INTO NOMINA.CUOTA (ID, ID_EMPRESA, ID_NIVEL, ID_CAMPUS, ID_HORARIO, CUOTA, FECHA_INGRESO, FECHA_ALTA, FECHA_PRENOMINA,
    ESTATUS_CUOTA, ESTATUS_DOCENTE, FECHA_ESTATUS_DOCENTE, USUARIO, ID_DOCENTE, DATE_CREATED, LAST_UPDATED, EMPRESA, ID_FORMA_DE_PAGO,
    FORMA_DE_PAGO, NIVEL, CAMPUS, HORARIO, JUSTIFICACION)
    VALUES (
      ${row.id},
      ${row.id_empresa},
      ${row.id_nivel},
      ${row.id_campus},
      ${row.id_horario},
      ${row.cuota},
      ${valid_date(row.fecha_ingreso)},
      ${valid_date(row.fecha_alta)},
      ${valid_date(row.fecha_prenomina)},
      ${row.estatus_cuota},
      ${row.estatus_docente},
      ${valid_date(row.fecha_estatus_docente)},
      ${row.usuario},
      ${row.id_docente},
      sysdate,
      sysdate,
      ${row.empresa},
      ${row.id_forma_de_pago},
      ${row.forma_de_pago},
      ${row.nivel},
      ${row.campus},
      ${row.horario},
      ${row.justificacion}
    )
    """
    sql.execute(str_string)
  }
  print "Cuotas migradas"
}
def valid_date(date){
  date ? date : null
}
pass_cuotas_to_nomina(sql)
println "Migracion terminada :)"
