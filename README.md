# Projeto Eventos VIP

## Descrição

O projeto Eventos VIP é um sistema de gerenciamento de eventos em Java, desenvolvido como um projeto de faculdade. Ele permite o cadastro de convidados (VIP e regulares), garçons e mesas, além de gerenciar pedidos e pagamentos. O sistema também oferece a funcionalidade de gerar relatórios em PDF e persistir os dados dos convidados em um arquivo CSV.

## Funcionalidades

O sistema oferece as seguintes funcionalidades através de um menu interativo no console:

- **Cadastrar Convidado:** Permite cadastrar novos convidados, especificando se são do tipo VIP ou Regular.
- **Designar Convidado à Mesa:** Aloca um convidado a uma mesa específica.
- **Fazer Pedido:** Registra um pedido para uma mesa, associado a um convidado.
- **Fechar Conta da Mesa:** Calcula e exibe o valor total da conta de uma mesa, aplicando descontos para convidados VIP.
- **Relatório no Console:** Exibe um relatório parcial do evento diretamente no console.
- **Gerar PDF do Evento:** Gera um relatório completo do evento em formato PDF.
- **Sair e Salvar:** Encerra o sistema e salva os dados dos convidados em um arquivo `convidados.csv`.

## Como Executar

Para executar o projeto, você precisará ter o [Git](https://git-scm.com/), [GitHub CLI](https://cli.github.com/) e o [Apache Maven](https://maven.apache.org/install.html) instalados e configurados no seu sistema.

1. **Clone o repositório:**
   ```bash
   gh repo clone eventos-vip
   ```
2. **Navegue até o diretório do projeto:**
   ```bash
   cd eventos-vip
   ```
3. **Compile o projeto usando Maven:**
   ```bash
   mvn clean install
   ```
4. **Execute a classe principal:**

   O comando para executar a aplicação é o mesmo para Windows, Linux e macOS. A Máquina Virtual Java (JVM) é capaz de interpretar o caminho do arquivo independentemente do sistema operacional.

   **No Windows, Linux ou macOS:**
   ```bash
   java -cp target/eventos-vip-1.0-SNAPSHOT.jar view.MenuPrincipal
   ```
   Se o comando acima não funcionar no Windows, você pode tentar substituir a barra `/` pela barra invertida `\`:
   ```bash
   java -cp target\eventos-vip-1.0-SNAPSHOT.jar view.MenuPrincipal
   ```

### Dependências

O projeto utiliza as seguintes dependências, que são gerenciadas pelo Maven:

- **iText 7:** Para a geração de relatórios em PDF.
- **SLF4J:** Para logging.

## Alunos

Os seguintes alunos contribuíram para o desenvolvimento do projeto:

- clidenor-jr <clidenor.junior@somosicev.com>
- eduardolimaegs <eduardocblima46@gmail.com>
- gabbms <gabriel.msousz@gmail.com>
- vini7go <vinicius4b5@gmail.com>