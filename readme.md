### Neo4j AWS Lambda Functions with Java 17

This project deploys a custom Java 17 runtime for an AWS Lambda function using a Docker container and Amazon Elastic Container Registry (ECR).

* The reason we're using a Docker container and ECR is that AWS Lambda currently only supports a limited set of runtimes, and Java 17 is not yet available as a native runtime option. By packaging our custom Java 17 runtime in a Docker container, we can use it as the execution environment for our Lambda function.
* It's worth noting that the latest Neo4j libraries require Java 17, which is not yet available as a native runtime option on AWS Lambda. This is why we need to go through the exercise of creating a custom Java 17 runtime using a Docker container and ECR.


Here's a high-level overview of the steps we've taken:

1. We built and packaged our Lambda function code as an uber jar file that includes all the dependencies needed for our function.
2. We created a Dockerfile that specifies the base image for our container and installs the Java 17 runtime and any other dependencies needed for our Lambda function.
3. We built the Docker image using the docker build command and tagged it with the ECR repository URL and a version number.
4. We authenticated with ECR using the aws ecr get-login-password command to obtain an authentication token and logged in to the ECR registry.
5. We pushed the Docker image to the ECR registry using the docker push command.
6. We created an AWS Lambda function that uses our custom Java 17 runtime by specifying the ECR repository URL and version number in the function configuration.
7. Finally, we tested the Lambda function by invoking it with a sample payload.

###  Step1: Fork project



#### Clean, Build and Package the Java Project locally. Make sure you are using Java 17 with Gradle. The project must be packaged as a shadow/uber jar
* `./gradlew clean`
* `./gradlew build`
* `./gradlew shadowJar`

##### Download & Configure Docker
##### Build the docker container based on the docker file in the project root
* `docker build -t $CONTAINERNAME .`

#### Run the docker container to confirm that the container is healthy
* `docker run $CONTAINERNAME` 

#### Create an AWS Elastic Container Registry (through aws cli or console)
* `aws ecr create-repository --repository-name $ECRREPOSITORYNAME --region us-west-2
  `

#### Tag the docker image with a label for your Amazon Elastic Container Registry 

* ` docker tag aws-lambda-01 $AWSACCOUNTID.dkr.ecr.$REGION.amazonaws.com/$ECRREPOSITORYNAME
  `

#### Authenticate Docker Client to an Amazon ECR private registry:
[Authenticate to a private ECR registry](https://docs.aws.amazon.com/AmazonECR/latest/userguide/docker-push-ecr-image.html)
* `aws ecr get-login-password --region region | docker login --username AWS --password-stdin aws_account_id.dkr.ecr.region.amazonaws.com`



#### Push Docker image to ECR
[Push Docker image to ECR](https://docs.aws.amazon.com/AmazonECR/latest/userguide/docker-push-ecr-image.html)

```
docker push $AWSACCOUNTID.dkr.ecr.$REGION.amazonaws.com/my-repository:tag

```

#### Invoke Function to test



```
aws lambda invoke \
--function-name my-function \
--payload '{}' \
output.txt \
--debug
```