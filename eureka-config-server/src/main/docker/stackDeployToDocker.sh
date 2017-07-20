#!/bin/bash
echo '======start docker stack======='
docker stack deploy -c eureka-config-server-$1.yml cloud-platform-$1
