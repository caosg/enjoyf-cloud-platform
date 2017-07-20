#!/bin/bash
echo '======start docker stack======='
docker stack deploy -c content-service-$1.yml cloud-platform-$1
