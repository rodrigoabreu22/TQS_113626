## Notas

No âmbito dos testes de integração, o ficheiro ControllerIT usa base de dados mocked e o ficheiro ControllerV2IT usa a base de dados real.


#### application-integrationtest.properties
```
## note changed port 3306 --> 33060
spring.datasource.url=jdbc:mysql://localhost:33060/tqsdemo
spring.jpa.hibernate.ddl-auto=create-drop
spring.datasource.username=demo
spring.datasource.password=demo


## db
## docker run --name mysql5tqs -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=tqsdemo -e MYSQL_USER=demo -e MYSQL_PASSWORD=demo -p 33060:3306 -d mysql/mysql-server:5.7
```

## c) What could be the advantages and disadvantages of using a real database connection in testing activities?

#### Vantagens: 

- Testagem mais realista: conseguimos garantir que a aplicação interage corretamente com a base de dados.
- Permite detetar aspetos especificos da base de dados que uma base de dados mocked ou em memória como h2 não consegem revelar.
- Deteta problemas de performance, permitindo ver o tempo de execução de queries e detetar bottlenecks.
- Permite garantir a persistência e integridade dos dados na bd, isto é garantir que os dados são adicionados, removidos ou alterados corretamente.
- É útil para testes de integração, porque valida a interação entre os diferentes componentes.
    

#### Desvantagens:

- Tem um tempo de execução maior.
- Problemas na limpeza dos dados pode gerar erros inesperáveis nos testes.
- Estamos dependentes da disponibilidade da base de dados. Se o servidor estiver em baixo não permite testar.
- É necessário configurar ambientes, isto quen pode ser complexidade desnecessária, em certos casos.
