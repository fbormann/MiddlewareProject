#!/bin/bash
set -e

if [ "$appoption" = "server" ] ; then
    java -cp ./Main.jar:./sqlite-jdbc-3.23.1.jar:./postgresql-42.2.5.jre7.jar main.java.aplicacao.Server;
fi

if [ "$appoption" = "client" ] ; then
    java -cp ./Main.jar:./sqlite-jdbc-3.23.1.jar:./postgresql-42.2.5.jre7.jar main.java.aplicacao.AppClient;
 fi

if [ "$appoption" = "naming_server" ] ; then
    java -cp ./Main.jar:./sqlite-jdbc-3.23.1.jar:./postgresql-42.2.5.jre7.jar main.java.naming.NamingServer;
 fi