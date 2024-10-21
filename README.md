# Secret Santa

## Description

This project is a service that allow us to gift exchange yearly. Imagine your extended family does a "Secret Santa" gift exchange yearly. For this gift
exchange, each person randomly draws another person and gets a gift for them. After the third year of doing the Secret Santa gift exchange, you hear complaints about having
the same Secret Santa for multiple years. As your extended family has grown, members have married and had children. Those families
usually get gifts for their immediate family members, so they would like to have their immediate
family excluded from the Secret Santa.

## Tech stack

* Java 17
* Spring boot 3.3.4
* Maven
* Lombok
* Rest API

## Installation

As this service is using maven, in order to be ready to up and running this service we need to do the following

* You can fork the repository to add this into your own repo.
* Download the project from git using the git clone command:

```
git clone repo_path
```

* Execute the following maven command to create the java artifact:

```
mvn clean package
```

* The above command will create a jar artifact named `secret-santa-0.0.1-SNAPSHOT.jar` so this is the service packaged 
with all necessary to run. Execute the following command to up and run the service:

```
java -jar secret-santa-0.0.1-SNAPSHOT.jar
```

* You can check if ths service is up and running check the health endpoint using the following endpoint:

```
http://localhost:8080/actuator/health
```

The endpoint to use this service is:
```
POST http://localhost:8080/api/v1/secret-santa/assign
```
This service need a JSON payload like this

```
{
"participants": [],
"familyRestrictions": [],
"pastAssignments": []
}
```
NOTE: Check the open API documentation review the API contract.

```
http://localhost:8080/swagger-ui/index.html
```

## Usage

The following is a JSON request example for this service

```yaml
{
"participants": [
{"name": "Alice"},
{"name": "Bob"},
{"name": "Charlie"},
{"name": "David"},
{"name": "Eve"},
{"name": "Frank"}
],
"familyRestrictions": [
{
"person": "Alice",
"immediateFamilyMembers": ["Bob", "Charlie"]
},
{
"person": "Bob",
"immediateFamilyMembers": ["Alice", "Charlie"]
},
{
"person": "Charlie",
"immediateFamilyMembers": ["Bob", "Alice"]
},
{
"person": "David",
"immediateFamilyMembers": ["Eve", "Frank"]
},
{
"person": "Eve",
"immediateFamilyMembers": ["David", "Frank"]
},
{
"person": "Frank",
"immediateFamilyMembers": ["Eve", "David"]
}
],
"pastAssignments": {
"2021":{
"Bob": "David",
"Eve": "Alice",
"Alice": "Frank",
"Charlie": "Eve",
"David": "Bob",
"Frank": "Charlie"
}
}
}
```

