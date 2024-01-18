MONGO_IMAGE="mongo:4.4.19"
NETWORK_NAME="mongoCluster"
PORT="27017"
CONTAINER_NAME="mongoReplica1"
REPLICA_SET="rs0"
# Parse named arguments
while [[ $# -gt 0 ]]; do
    case "$1" in
        --image)
            MONGO_IMAGE="$2"
            shift 2
            ;;
        --network)
            NETWORK_NAME="$2"
            shift 2
            ;;
        --port)
            PORT="$2"
            shift 2
            ;;
        --name)
            CONTAINER_NAME="$2"
            shift 2
            ;;
        --replica-set)
            REPLICA_SET="$2"
            shift 2
            ;;
        *)
            echo "Invalid argument: $1"
            exit 1
            ;;
    esac
done
{
  docker -v
} || {
  echo "Error: Docker not installed"
  exit 1
}
if [[ $MONGO_IMAGE != "mongo:"* ]]; then
    echo "Error: Please choose valid mongo image - 'mongo:<version>'"
    exit 1
fi
docker network create "$NETWORK_NAME"
docker stop "$CONTAINER_NAME"
sleep 5
docker rm "$CONTAINER_NAME"
sleep 5
{
  docker run -d -p "$PORT":27017 --name "$CONTAINER_NAME" --network "$NETWORK_NAME" "$MONGO_IMAGE" mongod --replSet "$REPLICA_SET" --bind_ip localhost,"$CONTAINER_NAME"
} || {
  echo "Error: Docker container run failed"
  exit 1
}
sleep 5
{
  docker exec -it "$CONTAINER_NAME" mongo --eval "rs.initiate({
   _id: \"$REPLICA_SET\",
   members: [
     {_id: 0, host: \"$CONTAINER_NAME\"}
   ]
  })"
} || {
  docker exec -it "$CONTAINER_NAME" mongosh --eval "rs.initiate({
   _id: \"$REPLICA_SET\",
   members: [
     {_id: 0, host: \"$CONTAINER_NAME\"}
   ]
  })"
}