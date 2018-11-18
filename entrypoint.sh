#!/bin/bash
set -e

if [ "$appoption" = "server" ] ; then
    java -cp ./Main.jar main.java.aplicacao.Server;
else
    java -cp ./Main.jar main.java.aplicacao.AppClient;
 fi