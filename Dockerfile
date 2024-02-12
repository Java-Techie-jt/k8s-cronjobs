FROM openjdk:17
ADD target/k8s-jobs.jar k8s-jobs.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","k8s-jobs.jar"]