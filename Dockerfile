# 基础镜像
FROM openjdk:11
#  openjdk:11-jre
VOLUME /tmp

ADD target/sundial-0.0.1-SNAPSHOT.jar app.jar

# 让dockers容器和宿主机时间保持一致
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo "Asia/Shanghai" > /etc/timezone

EXPOSE 9091

ENTRYPOINT ["java", "-jar", "app.jar"]