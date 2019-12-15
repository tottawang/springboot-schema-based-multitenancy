# springboot-schema-based-multitenancy

./gradlew clean build
java -jar build/libs/sample-0.0.1-SNAPSHOT.jar or run in debug mode:
java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=9001,suspend=n -jar build/libs/sample-0.0.1-SNAPSHOT.jar

## I addressed a couple of issues to make the schema based tenant work
1. Table name must be lower case otherwise relation cannot be found with schema specified
2. Rest endpoints not invoked
3. Test with JdbcTemplate
4. Test with different schemas