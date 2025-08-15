# Multi-stage build для оптимизации размера образа
FROM eclipse-temurin:17-jdk-alpine AS builder

# Установка рабочей директории
WORKDIR /app

# Копирование файлов Maven
COPY pom.xml .
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn

# Копирование исходного кода
COPY src src

# Сборка приложения
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

# Production stage
FROM eclipse-temurin:17-jre-alpine

# Создание пользователя для безопасности
RUN addgroup -S spring && adduser -S spring -G spring

# Установка рабочей директории
WORKDIR /app

# Копирование jar файла из builder stage
COPY --from=builder /app/target/nuraimed-back-*.jar app.jar

# Изменение владельца файлов
RUN chown spring:spring app.jar

# Переключение на пользователя spring
USER spring

# Открытие порта
EXPOSE 8080

# Настройка JVM для контейнера
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Запуск приложения
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
