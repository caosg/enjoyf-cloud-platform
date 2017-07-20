#!/bin/bash
echo '======start docker stack======='
docker stack deploy -c profile-service-$1.yml cloud-platform-$1
