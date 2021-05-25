# Desafio Mobile da Warren Brasil

Chegou a hora de você conhecer um pouquinho sobre os desafios enfrentamos e as tecnologias que utilizamos aqui na Warren! A ideia desse desafio é desenvolver um aplicativo simples que consiste em uma tela de login, uma tela com os objetivos de um usuário e uma tela com os detalhes do objetivo.
Você terá o prazo de 1 semana para a entrega, que deve ser realizada por etapas, pois parte importante do projeto é a colaboração e resposta aos comentários/sugestões.

## Entrega
Usar esse repositório como um template para a criação de um repositório *privado* no GitHub e adicionar `@robuske` e `@bocato` (para iOS) ou `@pablobaldez` (para Android) como colaborador. 
`https://help.github.com/en/github/creating-cloning-and-archiving-repositories/creating-a-repository-from-a-template`

# Anotações do Desenvolvedor

Olá! Neste projeto, foi implementado todas as solicitações do teste (Login, Lista de Objetivos e Detalhes Do Objetivo), onde as principais tecnologias utilizadas foram:

* **Kotlin**: O app foi feito completamente em Kotlin.
* **Retrofit**: Foi utilizado o Retrofit para gerenciamentos e parseamento de requests.
* **Architecture Components**: Foi utilizado o LiveData junto do ViewModel para a camada de apresentação.
* **Coroutines**: Todo o gerenciamento de estados e tarefas assíncronas foram feitos utilizando Kotlin Coroutines.
* **Injeção de Dependências**: Foi utilizado o Koin como ferramenta de injeção de dependência.
* **Testes**: Foram implementados testes unitários nas principais classes da aplicação, utilizando JUnit4, Google Truth, CoroutinesTest e Mockk.

## Sobre os Pull Requests
Eu segui a metodologia apresentada no GitFlow como gerenciamento do versionamento do Git, e iria abrir os pull requests assim que fosse terminando as funcionalidades.

Porém, até o momento o `@pablobaldez` não aceitou o pedido para colaborar no projeto, assim não tendo ninguém para interagir no fluxo de aprovação de funcionalidades.

Para que eu não ficasse parado aguardando a aceitação do convite e aprovação dos pull requests, segui o fluxo normal do GitFlow, mas nos momentos de pull request, eu simplesmente realizei o merge na branch develop. Abaixo vocês podem ver de maneira visual como foi o gerenciamento das branchs:

![git](https://user-images.githubusercontent.com/29527763/119518636-135f2280-bd4f-11eb-971a-10f69dacc6dc.png)

## Arquitetura
A arquitetura foi inspirada no que a documentação do Android sugere: Uma **camada de visualização (activities, fragments, etc.)** se comunicando com a **camada de apresentação (ViewModels)** que interage com **Repositórios** que por fim, manipulam outras classes que interagem com o ecossistema Android **(Room, Retrofit, etc.)**.

De forma genérica, a arquitetura do aplicativo pode ser definida como no diagrama abaixo:

![arq](https://user-images.githubusercontent.com/29527763/119516902-85cf0300-bd4d-11eb-9b8e-8243f4a08344.png)

## Demo
Abaixo vocês podem ver uma preview de como o aplicativo ficou, antes de precisar compilá-lo.

https://user-images.githubusercontent.com/29527763/119517574-1c9bbf80-bd4e-11eb-864e-307dcec1e916.mp4



