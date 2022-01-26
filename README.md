Перед запуском необходимо запустить PostgreSQL:
docker run --name account-postgres -p 5433:5432 -e POSTGRES_USER=account -e POSTGRES_PASSWORD=account -e POSTGRES_DB=account -d postgres:14

В gateway переопределить генератор токена и добавить в него audience для корректной генерации токена. 

Для понимания, изучить TokenRelayGatewayFilterFactory. 