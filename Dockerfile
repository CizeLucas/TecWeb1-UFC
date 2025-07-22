FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY backend/ ./backend/
COPY frontend/ ./frontend/

RUN mkdir -p backend/bin

RUN javac -cp "backend/lib/*:backend/bin" -d backend/bin backend/src/**/*.java

EXPOSE 8080

CMD ["java", "-cp", "backend/bin:backend/lib/*", "Server.Server"]
