#!/bin/bash
/usr/bin/sleep 15
/usr/bin/docker ps -a | grep "Exited" | awk '{print $1 }'|xargs docker rm
/usr/bin/docker images| grep none | awk '{print $3 }'|xargs docker rmi
