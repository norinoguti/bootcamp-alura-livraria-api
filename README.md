# Projeto módulo 2 - Livraria-api
## Api para gestão livraria - Bootcamp Java Alura

Projeto de uma Api REST para gestão de livraria contendo cadastro e listagem de autores e livros,
criado utilizando *Spring Boot* a partir do site [Spring Initializr](https://start.spring.io/).</br>
Esse projeto irá sofrer implementações a medida que o bootcamp for avançando.

### Tecnologias utilizadas
**Módulo2**
* Lombok
* ModelMapper
* BeanValidation

**Módulo 3**
* Spring Data JPA
* Flyway

# Projeto módulo 3 - Implementação de novas funcionalidades
## Implementações

* Neste módulo foi alterada a forma de persistência de JDBC para JPA
* A persistência deixou de ser feita em uma lista e passou a ser feita em banco de dados
* Foi adotado o uso de paginação e ordenação dos registros 
* Controle de versionamento do *schema* do banco de dados utilizando o *Flyway*
* Quando o registro é realizado com sucesso foi adotado a boa prática de retonar o código HTTP 201 e o corpo do objeto

### Como testar
Foram criadas as funcionalidades de *Get* (Consulta) e *Post* (Cadastra) que podem ser testadas através
da ferramenta [Postman](https://www.postman.com/). 
