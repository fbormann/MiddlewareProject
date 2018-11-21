FROM java:8
WORKDIR /
COPY . /
RUN javac -cp ./src/main/java ./src/main/java/*/*.java ./src/main/java/*/*/*.java -d ./out/
RUN jar cvfm Main.jar ./src/main/resources/META-INF/MANIFEST.MF -C ./out/ .
RUN chmod +x ./entrypoint.sh
ARG appoption
ENTRYPOINT ["./entrypoint.sh"]
CMD $appoption