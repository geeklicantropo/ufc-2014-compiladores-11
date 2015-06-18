## Apresentação Inicial ##

Esse relatório descreve as duas primeiras partes inicias de todo o processo de um compilador para a linguagem MiniJava. Nesse relatório, procuramos relatar o que conseguimos fazer durante todo o processo de aprendizado nessa disciplina (levamos em consideração as dificuldades encontradas que eu hei de relatar posteriormente) através da descrição da implementadão do trabalho, em termos de estrutura organizacional do projeto, dificuldades encontradas, etc.

## Análise Léxica e Análise Sintática ##

A fase inicial do compilador é a fase que faz a Análise léxica e sintática. Nessa fase inicial, o compilador verifica se o programa contém algum erro léxico “tipo, o uso de uma palavra que não foi identificado como token na gramática” e também algum erro sintático “tipo, partes do programa que não conseguem ser geradas a partir da gramatica definida, no nosso caso em particular, a gramática da linguagem MiniJava. ”

Iniciarmos o projeto com a implementação dos analisadores léxico e sintático, incorporamos o plugin JavaCC na plataforma do desenvolvimento eclipse. Iniciamos a análise léxica pela separação de tokens, onde foram definidos os tokens da gramática no seu devido formato suportado pelo JavaCC, após isso, criamos os métodos de parse. Cada produção da gramática possuía um método correspondente, estes seriam recursivamente chamando por outros métodos, ou seja, outras produções da gramática.


Uma gramática é dita recursiva à esquerda se ela tiver um não-terminal A tal que
> A => A& para alguma cadeia w.

> A nossa gramática MiniJava possuía recursão a esquerda, que não é aceita pelo JavaCC. Na gramática original havia uma recursividade à esquerda no não-terminal "Exp", que representa a expressão. Para contornar, tivemos que efetuar uma alteração na gramática, como segue abaixo:

Gramática original com recursão à esquerda:
Exp → Exp op Exp
→ Exp . length
→ Exp [Exp ](.md)
→ Exp . id ( ExpList )
→ INTEGER LITERAL
→ true
→ false
→ id
→ this
→ new int [Exp ](.md)
→ new id ()
→ ! Exp
→ ( Exp )

Gramática modificada sem recursão à esquerda:
ExpL → op Exp ExpL
→ . length ExpL
→  [Exp ](.md) ExpL
→ . id ( ExpList ) ExpL
Exp→ INTEGER LITERAL ExpL
→ true ExpL
→ false ExpL
→ id ExpL
→ this ExpL
→ new int [Exp ](.md) ExpL
→ new id () ExpL
→ ! Exp ExpL
→ ( Exp ) ExpL



Em algumas produções haviam conflitos, o conflito foi devido a necessidade do uso de lookaheads de determinados tamanhos, dependendo da produção. Nessa parte da construção, tivemos algumas dificuldade. No início declaramos um lookahead específico no options, e na frente de cada produção, colocamos o lookahead que era pra ser utilizado. Mas mesmo assim, estava aparecendo o aviso de erro para as produções que tinham conflito. Então setamos o lookahead no próprio Parser.jj,  nas produções que possuíam conflito, e todos os warnings foram eliminados corretamente.

## Ações Semânticas, Tabela de Símbolos e Checagem de Tipos ##

### Ações Semânticas ###

Na ação semântica, é necessário uma árvore de sintaxe abstrata, para facilitar a construção da Tabela de Símbolos e auxiliar a Checagem de Tipos, pois uma arvore não abstrata complicaria essas duas fases seguintes.
As ações semânticas farão a construção dessa árvore, onde vão usar as derivações da gramática, construindo assim os nós da árvore a partir delas. Fizemos esta parte no arquivo Parser.jj, onde cada produção da gramática foi alterada adicionando as ações semânticas.

**Tabela de Símbolos**

Na tabela de símbolos temos a visão de uma estrutura. A estrutura composto de atributos, métodos, parâmetros, variáveis locais e os nomes das classes do programa. Essa estrutura é essencial para a fase de checagem de tipo, pois permite a detenção de possíveis erros de tipagem do programa.
A estrutura de tabala do símbolo foi feita utilizando relacionamentos entre classes e Hashmaps. Hashmap é uma estrutura do JAVA semelhante a um dicionário, que guarda o par chave/valor. A estrutura da tabela de símbolos foi feita utilizando as seguintes classes:


**TableForClass**

> Nessa classe temos uma tabela de atributos que é um objeto do tipo  TableOfAttribute e uma tabela de métodos que é um objeto do tipo  TableOfMethod. O métodos mais importantes são o addMethod que nada mais adiciona  um método com seus respectivos parâmetros e variáveis locais em uma ParamAndLocalOfMethod,  bem como seu tipo.  Temos também a função addAttributesTable que vai criar um tabela de atributos  e também registrar adicionar atributos.


**ParamAndLocalOfMethod**

> Nessa classes temos os parâmetros do método e suas  variáveis locais. Os parâmetros do do método é representado por um objeto do tipo  TableOfParam e as variáveis locais do método é um objeto do tipo TableOfLocal.  Temos os métodos addParams e addLocals que tem a função de adicionar parâmetros e variáveis locais no TableOfParam e no TableOfLocal, respectivamente.


**TableOfAttribute**

> Nessa classe temos uma estrutura responsável por guardar os atributos de uma determinada classe. Temos uma estrutura que guarda um atributo(Symbol) e seu determinado tipo(Type). Temos também métodos de acesso à essa estrutura, bem como um método AddAttribute para adicionar atributos.

**TableOfLocal**

> Nessa classe temos uma estrutura para guardar uma variável local(Symbol) e seu devido tipo(Type). Temos também os métodos de acesso a essa estrutura.

**TableOfMethod**

> Nessa classe, temos dois hashmaps. Um deles, tem o nome do método(Symbol) e seu determinado tipo(Type). Uma outra estrutura tem o nome do método(Symbol) e seu determinado conjunto de variáveis locais e parâmetros que é um objeto do tipo ParamAndLocalOfMethod.

**TableOfParam**

> Nessa classe temos uma estrutura que vai guardar um parâmetro(Symbol) e seu devido tipo(Type). Temos também os métodos de acesso a essa estrutura.

**TableOfSymbol**

> Nessa classe procuramos representar uma tabela com as classes do programa e  suas respectivas TableForClass correspondentes. Os pares chaves/valor do hashmap são as classes(chave), que são objetos Symbol e as devidas tabelas(valor) correspondentes, que são objetos do tipo TableForClass. As classes são adicionadas na symboltable através do método addClass.  Temos também um HashMap para registrar a classe filha(symbol) e seu determinado pai(symbol).


### Herança no MiniJava ###

A linguagem MiniJava permite que se ultilize herança. Dessa forma, tivemos de se preocupar para que ao ser detectado um caso de uma classe herdar de outra, a classe base pudesse receber os atributos e métodos da super-classe. A estrutura usada para representar a herança foi um HashMap de Symbol(chave da classe base) para Symbol(valor da super-classe) que fica na classe TableOfSymbol. Na parte do visit em ClassDeclExtends, temos o processamento da herança. A herança é adicionada na estrutura de dados e é feita a configuração de métodos e variáveis na função de setUpTheInheritance.
Checagem de Tipos

O padrão Visitor é um padrão de projeto no contexto de orientação a objeto e engenharia de software. O esse padrão nos  permite criar uma nova operação sem que se mude a classe dos elementos sobre as quais ela opera. A ideia é separar o algoritmo da estrutura do objeto. Com isso, ganhamos praticidade sobretudo na possibilidade de se adicionar novas funcionalidades a estruturas de um objeto pré-existente sem a necessidade de modificá-las. Esse padrão foi ultilizado no projeto com auxílio do material encontrado no seguinte endereço: http://www.cambridge.org/resources/052182060X/ .

Na classe SymbolTableVisitor que implementa a classe TypeVisitor, onde aquela tem como objetivo fazer o analise de cada uma das classes da árvore sintática. Foi acrescentada também a classe ErrorMsg. Nessa classe teremos um booleano que indica se houve algum erro na checagem de tipos. A etapa onde é feita a checagem de tipos utiliza bastante a classe TableOfSymbol, pois esta armazena estruturadamente as declarações de variáveis e o escopo das mesmas.

### Testes ###

No pacote tests temos a classe Test onde é realizada as operações implementadas sobre os arquivos java disponíveis na pasta sampleMiniJavaPrograms.

### Conclusão & Considerações ###

A equipe formada por apenas dois membros encontrou uma serie de dificuldades com a tecnologia utilizada bem como a complexidade do projeto. Porém, a equipe pode absorver a fundamentação teórica durante a disciplina e isso ajudou a iniciar e continuar o desenvolvimento do projeto. Pudemos nesse projeto, usar conhecimentos adquiridos em outras disciplinas como engenharia de software, porém não pudemos fazer uso de ferramentas devido as limitações de tempo e de pessoal.