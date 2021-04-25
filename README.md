# despesas_server
API a ser consumida por aplicação Flutter(mobile) e Angular(web) para controle de despesas pessoais

## Getting started
Para executar o projeto, será necessário instalar os seguintes programas:
- [JDK 11: Necessário para executar o projeto Java](https://www.oracle.com/br/java/technologies/javase-jdk11-downloads.html)
- [IntelliJ IDEA: Necessário para o desenvolvimento do projeto](https://www.jetbrains.com/pt-br/idea/download/#section=windows)
- [MySQL: Banco de dados utilizado na aplicação](https://www.mysql.com/downloads/)
- [MySQL Workbench: Necessário para o gerenciamento do banco de dados](https://www.mysql.com/downloads/)

## Development
Para controle do saldo das contas do plano de contas, controle de saldo das duplicatas e insersão das contas pai ao inserir um registro em plano, é necessária criação das seguintes trigger no banco de dados:
- [atualiza_plano_saldo_insert: Altera o saldo das contas ao inserir lançamento](https://docs.google.com/document/d/1zo0MV1RhUugFpNfjZVhXmEMnq24CI0Rf5bVlnby2O-g/edit?usp=sharing)
- [atualiza_plano_saldo_pai_insert: Altera o saldo das contas pai das contas vinculadas ao lançamento](https://docs.google.com/document/d/11Muy0tmPivLYWtxe0UfcuVxC7E1z8zHi68qF2PB55ac/edit?usp=sharing)
- [atualiza_saldo_duplicata_insert: Altera o saldo da duplicata quando é realizado o pagamento](https://docs.google.com/document/d/1MQLxF-eyb9FAUV08rbbGDgemI0zIGuaHLmOA3mR4uQs/edit?usp=sharing)
- [insert_plano_pai: Relaciona a conta do plano com suas contas pai ao inserir registro](https://docs.google.com/document/d/1Wbj8RmWc_oPWTvQRdjCiDagWSJeJha7lOYOeHuYjyyg/edit?usp=sharing)
