#!/bin/bash
echo '======start docker stack======='
docker stack deploy -c gateway-server-$1.yml cloud-platform-$1
