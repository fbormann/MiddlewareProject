FROM java:8
WORKDIR /
ADD app.jar app.jar
CMD java - jar app.jar