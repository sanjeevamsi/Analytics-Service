# Check budget limit
1. first check the budget limit of current month
2. now also check the expenses of the current month till today
3. if expenses exceeds monthly budget limit then we need to send this to message Queue.
4. We need to send the thing to producer

# RabbitMQ
1. First we need to get the docker running for rabbitMQ to run
2. Now we need to create Queues
3. Also, we need to bind the queues with binding key
 
## Converting Java objects to JSON
1. Before spring boot 4 we use to Jackson2JsonMessageConverter
2. but after 4 we will start using via json mapper
3. In spring boot we can inject the bean as a argument to another bean
4. Inside the queue we can only send POJO 

### Records in dto
1. Java records can also be used for DTOs.
2. Record is a data carrier.
3. its only job to hold the data , not to manage the complex behavior

### Records
A Record is special type of class that is immutable (once set we cant change them again).
When define anything with the record
a. private fields will be set
b. constructor will intializes these fields.
c. but instead of get we get the method name as variable() , like userId to userId() instead of getUserId()
d. equals() and hashcode() is used to compare the records
e. toString() is also be used.

### How is it helpful in microservices
Once the message is sent to the user,the message shouldn't change.
Its a ready only by default.
Records have a clear structure we can convert the messages to JSON object very efficiently.

### Use of records
used for stoing just the message no hassel like storing all the data no hassel of lombok and all

### Consumer
1. so when the event is sent to the queue (rabbitMQ) 
2. Consumer here means the server has to pull the event 
3. DeliverTag : Whene a broker sends a event to consumer it will send a unique number for that specific channel
4. what is the use of this : 
   a. RabbitMQ doesnt delete the message as soon it sends to the consumer
   b. it waits for you to say "hey i got it"
   c. We use the deliveryTag to tell broker "i got the message you can delete it now".

### RabbitMQ
Think rabbitMQ is a high end satellite TV
1. RabbitMQ broker is the service provider
2. producer is the movie studio sending the content
3. consumer is television at home

### Channel 
Opening a physical wire(TCP Connection) between the java app and rabbitMQ is expense
A channel is virtual connection inside the one physical wire.
it is lightweight independent "pipe" through which data flows.
We can have thousands of channels inside single TCP connection
why ? if one channel crashes other keeps on working.

### Consumer 
It's a stateful process that is constantly talking to the broker.

@RabbitListener tells the broker : "hey i am consumer 1 please send me any message that lands in budget-queue"
Delivery : broker pushes a message into the channel.
Processing : Java code that runs inside the consumer.
Ack/Nack : "Handshake to close the loop".

### Acknowledgement
Since we are using manual acknowledgement we are controlling the safety of a data
ACK (Acknowledge) : Telling the broker that "I have finished this job you can safely delete it from the Queue"
NACK (Negative Ack) : "Something went wrong , I couldn't finish it. Please put it back to the Queue"
Reject: "This means is corrupted i can never able to process this put it in 'Dead letter' bin";

Message contains the meta data about the payload (delivery-tag, content-type, timestamp etc..)
Channel channel : Is the remote controller. To send the commands back to the MQ (like acknowledge)

### Flow from consumer to User
1. Receive the event 
2. check the db (Idempotency) : Check if the notification for this specific userId and month was already sent in the last hour.
3. if not send the notification/email
4. then save to the db as "SUCCESS" in the table.
5. ACK : tell the rabbitMQ to delete the message.
