#!/bin/bash
echo '======start docker stack======='
docker stack deploy -c user-service-$1.yml cloud-platform-$1
