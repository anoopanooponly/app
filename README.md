Refer architecture diagram for flow(add-custom-attribute-to-user.PNG)

Step 1: Spin keycloak - with postgres

docker-compose -f keycloak.yml up

***** Do only for the first time ***********

  KEYCLOAK_USER: admin
  KEYCLOAK_PASSWORD: Pa55w0rd

create a realm in keycloak with name: demo
create a client under demo realm (refer images)
optionally create custom attribute in realm mapper(refer image)
create user - set password -remove temporary
optionally add custom attribute to user(refer image)

*********************************************

Step 2: start gateway and application-service
 - using spring STS to start required module as spring-boot or use the 'maven clean install' to build the module and start the jar using 
 java -jar <jar-name>.jar

Step 3: Start the appollo server:

Step 4: Start the UI




