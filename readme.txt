Para rodar o backend:

1 - Caminhe para AvaliacaoPraticaDS/backend/bank

2 - Execute o script:

export $(cat .env | xargs) -> Carrega as variaveis de ambiente ( ou coloque hardcoded )


3 - execute mvn spring-boot:run 


(O banco mysql deve estar rodando em localhost:3306 com o nome db_bank, user = root e password = root ( ou conforme DB_URL))


Para rodar o frontend:

1 - Caminhe para AvaliacaoPraticaDS/frontend/avaliacaods

2 - npm install para instalar as dependencias do package.json

3 - ng serve


A aplicação estará rodando por padrão em localhost:4200