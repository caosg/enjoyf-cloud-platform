#!/bin/bash
ssh docker@192.168.99.100 docker-compose /usr/local/bin/docker-compose -v
ssh docker@192.168.99.100 docker-compose /usr/local/bin/docker-compose -f app-test.yml up -d
