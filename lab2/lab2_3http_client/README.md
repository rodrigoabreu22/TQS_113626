#### e) 
Os testes falham quando a net está desligada e funcionam com a net ligada, logo posso concluir que a implementação está bem feita.


#### f)
Os comandos são todos diferentes e servem para diferentes tipos de testagem embora alguns testes tenham aspetos em comuns com outros.

| Command | Runs Unit Tests? | Runs Integration Tests? | Creates Package? | Installs to Local Repo? |
|---------|---|----|---|-----|
| `mvn test` | ✅ | ❌ | ❌ | ❌ |
| `mvn package` | ✅ | ❌ | ✅ | ❌ |
| `mvn package -DskipTests=true` | ❌ | ❌ | ✅ | ❌ |
| `mvn failsafe:integration-test` | ❌ | ✅ | ❌ | ❌ |
| `mvn install` | ✅ | ❌ | ✅ | ✅ |
