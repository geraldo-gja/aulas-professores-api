# Case de Technology (FourD) - 30/06/23

# Perfil Desenvolvedor Backend

## Instruções
O case é composto por duas etapas, conforme descrito abaixo.
O resultado case será avaliado através do fornecimento do repositório Github contendo os códigos
produzidos.

## Case
## Etapa 1
A UniTech procurou a FourD para o desenvolvimento do Backoffice de seus professores. A grande
preocupação da Unitech é que o Backoffce tenha uma experiência normal em situações de alta
demanda de uso.

Você ficou responsável em desenvolver uma API para suportar o Backoffice proposto, resolvendo as
seguintes jornadas:
* Cadastro (deverá ficar pendente, aguardando aprovação)
* Login
* Lista de aulas (listar somente as aulas do professor relacionado ao token de acesso)
* Cadastro de novas aulas (criação e edição)

A aula é composta dos seguintes dados:
* Título
* Descrição
* Data prevista para aula.

Disponibilize em sua API um recurso para aprovar o cadastro de um professor.
Requisitos não funcionais:
• Utilizar conceito de JWT. O payload do JWT deve possuir o código e nome do professor.
• Utilize um banco de dados não relacional.
• Planeje uma solução para resolver a preocupação de situações de alta demanda.

## Etapa 2

Crie também um compose Docker para que o time de Frontend possa facilmente provisionar um
ambiente local para testes com a sua API.

Lembre-se de habilitar o CORS de forma que o desenvolvedor do Frontend consiga informar no
momento de subir o container a Url de origem (ex: http://localhost:3000) através da environment
allow-origin.

Ao final do case você deverá fornecer o caminho do repositório git.
