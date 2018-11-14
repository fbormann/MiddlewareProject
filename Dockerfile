FROM java:8
WORKDIR /
COPY . /
RUN javac -cp ./src/main/java ./src/main/java/com/cin/*.java -d ./out/
RUN jar cvfm Main.jar ./src/main/resources/META-INF/MANIFEST.MF -C ./out/ .
RUN java -jar ./Main.jar