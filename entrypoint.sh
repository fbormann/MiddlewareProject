#!/bin/bash
set -e

if [ "$appoption" = "server" ] ; then
    java -cp ./Main.jar:./sqlite-jdbc-3.23.1.jar main.java.aplicacao.Server;
else
    java -cp ./Main.jar:./sqlite-jdbc-3.23.1.jar main.java.aplicacao.AppClient;
 fi