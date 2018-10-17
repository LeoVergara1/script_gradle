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

println "Hola mundo"

sql.eachRow('SELECT * FROM horaprof.DOCENTE') { row ->
  println row
}


