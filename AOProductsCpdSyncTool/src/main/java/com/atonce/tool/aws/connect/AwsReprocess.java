package com.atonce.tool.aws.connect;

import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.GetQueueUrlRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;

public class AwsReprocess {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	public String publishMessage(String message, String queueName) {
		AmazonSQS sqs = AmazonSQSClient.builder().withRegion("us-east-2")
				// .withCredentials((AWSCredentialsProvider) new AWSStaticCredentialsProvider(creds))
				.build();
		log.info("Starting with Amazon SQS Standard Queues");
		try {
			//Create Queue
			sqs.createQueue(new CreateQueueRequest(queueName)).getQueueUrl();
			log.info("Queue with name {} created", queueName);
			
			// get Queue URL
			GetQueueUrlRequest getQueueUrlRequest = new GetQueueUrlRequest(queueName);
			String myTestQueueUrl = sqs.getQueueUrl(getQueueUrlRequest).getQueueUrl();
			log.info("Test Queue url {} ", myTestQueueUrl);

			// Send a message.
			log.info("Publishing message {} to Queue {}", message, queueName);
			sqs.sendMessage(new SendMessageRequest(myTestQueueUrl, message));

			// Receive messages.
			log.info("Receiving messages from {}",queueName);
			final ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(myTestQueueUrl);
			final List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
			for (final Message receivedMessages : messages) {
				System.out.println("Message");
				log.info("Message");
				log.info(" MessageId {} ",receivedMessages.getMessageId());
				log.info(" Body {} ",receivedMessages.getBody());
				for (final Entry<String, String> entry : receivedMessages.getAttributes().entrySet()) {
					log.info("Attribute");
					log.info("  Name {}  ", entry.getKey());
					log.info("  Value{} ", entry.getValue());
				}
			}
			return "success";

			// Delete the message.
			//System.out.println("Deleting a message.\n");
			//final String messageReceiptHandle = messages.get(0).getReceiptHandle();
			//sqs.deleteMessage(new DeleteMessageRequest(myTestQueueUrl, messageReceiptHandle));

			// Delete the queue.
			//System.out.println("Deleting the test queue.\n");
			//sqs.deleteQueue(new DeleteQueueRequest(myTestQueueUrl));
		} catch (final AmazonServiceException ase) {
			log.error(
					"Caught an AmazonServiceException, which means " + "your request made it to Amazon SQS, but was "
							+ "rejected with an error response for some reason.");
			log.info("Error Message:    " + ase.getMessage());
			log.info("HTTP Status Code: " + ase.getStatusCode());
			log.info("AWS Error Code:   " + ase.getErrorCode());
			log.info("Error Type:       " + ase.getErrorType());
			log.info("Request ID:       " + ase.getRequestId());
			return "fail";
		} catch (final AmazonClientException ace) {
			log.info("Caught an AmazonClientException, which means "
					+ "the client encountered a serious internal problem while "
					+ "trying to communicate with Amazon SQS, such as not " + "being able to access the network.");
			log.info("Error Message: " + ace.getMessage());
			return "fail";
		}
	}
}
