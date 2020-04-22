# uol-url-shortener

Serviço de encurtamento de URL's disponibilizado via API REST. Trata-se de uma POC. 

# Instruções

## Como executar a aplicação?

`mvn sprint-boot:run`

## Como rodar a suite de testes da aplicação?

`mvn clean test`

# Decisções de Arquitetura

## Técnologias utilizadas e motivações

### Java 8

Linguagem de programação orientada a objetos com plataforma
robusta para aplicações enterprise. Versão 8 da linguagem Java, é LTS e atualmente a mais utilizada.

## Maven

Para build e gerenciamento de dependências da aplicação

### Spring Boot
Para a criação da aplicação API RESTful

#### Spring Data JPA + Hibernate + MySQL (H2 para testes)
Para persistência em banco de dados relacional.
O banco escolhido foi o MySQL por motivos práticos.
Por fazer parte da especificação JPA, podemos migrar para outra solução de RDBS.
*Observação: para fins de testes, foi utilizado o banco relacional em memória H2.*
As conexões com o banco são gerenciadas via pool de conexões com hikariCP.

#### Spring Security
Segurança da aplicação.
Autenticação Oauth com bearer token.
*Obs: como se trata de uma POC, o servidor de autenticação é em memória,
contendo apenas 1 usuário, mas é fácilmente possível extender para soluções prontas
para produção como Keycloak.*

#### Spring Cache + EhCache
Solução de caching para entregar rapidamente as URLs para os clientes.
Não há necessidade de recalcular as URLs todas as vezes,
pois as informações não mudam, uma vez que é feito o cálculo
do hash/alias, ele não mudará, pois o hash é único por URL.

*Obs: como se trata de uma POC, o cache é em disco na instância da aplicação,
mas como segue a especificação JCache, pode ser extendido
para uma solução de cache distribuído como Redis.*

#### hashids
biblioteca para geração de hash ids que atende os requisitos de um encurtador de URLs:
- Hashes curtos
- Hashes únicos
- Rápido de ser calculado
- Fácil de fazer encode/decode
- Evita palavras ofensivas.

[https://hashids.org/java/](https://hashids.org/java/)

#### logback
Solução de logging, flexibiliza a customização de appenders.
*Obs: como se trata de uma POC, foram configurados appenders
do console. Por seguir a especificação SL4FJ, pode ser fácilmente extendido para utilizar soluções de logs centralizados como ElasticSearch e Kibana.*

#### Swagger
documentação da API RESTful

#### Postman
Para testar os endpoints da API RESTful

#### JUnit + Mockito
Testes unitários
#### RestAssured
Testes de integração

## Design do código
O código da aplicação está dividido em camadas, cada uma com suas responsabilidades
bem definidas, seguindo os principios SOLID e injeção de dependências.

application -> entry point (main) da aplicação

config -> configurações da aplicação (cache, segurança, etc)

controller -> mapeia os endpoints da API RESTful,
recebe as requisições e devolve as respostas de sucesso/erro.

exception -> exceções de negócio da aplicação (e.g. URL não encontrada,
falha ao encurtar URL etc).

generator -> classes utilitárias para geração de hash/alias
de URL curta.

logger -> configurações de log da aplicação

service -> regras de negócio da aplicação.
encurtar URL, validar URL, verificar se URL existe, etc.

repository -> padrão Repository para persistência
dos dados em banco relacional utilizando JPA e Hibernate.

model.dto -> DTO (Data Transfer Objects) objetos imutáveis e serializaveis
para trafegar na rede. Serve como payload de requisição e resposta da API,
de maneira que fique desacoplada das entidades do domínio da aplicação.

model.entity -> Entidades de domínio da aplicação (e.g. url curta, dados
estatisticos) que são persistidas e a partir das quais são
aplicadas as regras de negócio na camada de serviço.

Entidades:
ShortUrl -> Armazena a url curta, mapeando o id com a url original

ShortUrlDomain -> dominio ao qual a url curta pertence.
Util para caso a aplicação esteja em domínios diferentes.

Expor o ip da máquina diretamente é arriscado, pois a mesma pode estar em rede/cloud privada, ou por trás de uma API gateway ou um load balancer. Ou seja, pode não ser acessível diretamente.

Não é uma boa ideia persistir a url curta completa para
cada registro da entidade ShortUrl pois em caso de mudança de domínio,
dará mais trabalho atualizar milhares de colunas no banco de dados.
Desta forma, basta atualizar 1 registro de domínio para que todas as urls curtas
associadas sejam refletidas sem impacto na aplicação.

ShortUrlStatistics -> dados estatísticos da URL encurtada como
quantas vezes essa url foi acessada? quando foi a ultima vez em que
ela foi acessada?

Observação: as estatísticas são computadas sempre quando o usuário
acessar a url curta, ao ser redirecionado para a url original, porém de forma assíncrona (com thread pool configurada
para suportar multiplas requisições), para que o cliente
não precise esperar terminar o processamento dos dados, ele pode
ser redirecionado para a URL original rapidamente, e consultar os dados estatísticos posteriormente.

## Arquitetura da aplicação

Artefato gerado no formato .war compatível com diversos servidores de aplicação java do mercado.

O servidor Java escolhido foi o Tomcat 8.5 por ser um servidor conhecido,
rápido e de fácil configuração e deploy de aplicações Java.

Foi utilizado o elastic beanstalk (free-tier) da aws para deploy na núvem. Desta forma
pode ser configurado posteriormente clusterirzação e balanceamento de carga, bem como efetuar monitoramento.

Para o banco de dados, foi utilizado uma instância RDS (free-tier)  da AWS, com MySQL.

## Pipeline de CI/CD

Foi configurado um pipeline simples de CI/CD: 

Github (Actions) -> CodePipeline (AWS) -> Sonar Cloud + Coveralls -> Elastic Beanstalk

