# Por favor faça um Fork desse projeto!

## Está em dúvida de como fazer um Fork? Não tem problema! [Aqui tem uma explicação do que entendemos que você deve considerar!](https://docs.github.com/en/github/getting-started-with-github/fork-a-repo)

## ===================== REST =====================
-> Buildar para gerar artefato
-> sh gradlew build

-> Testar aquivo
-> java -jar build/libs/key-manager-rest-0.1-all.jar

-> Buildar o arquivo Dockerfile
-> docker build -t key-manager-rest:latest .

-> Listar imagem criada
-> docker image ls

-> Rodar com docker
-> docker run -d -p 8077:8077 key-manager-rest

-> Verificar imagem
-> docker ps

-> Verificar logs
-> docker logs -f 0877317d878a

##OBS: Foi usado Windows Terminal e Git Bash
