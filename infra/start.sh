#!/bin/bash
sleep 50

cd /home/ec2-user/picpay

docker-compose up -d

docker run -d --name picpay-app --network picpay-network --env-file .env -p 80:8080 danielkappa/picpay